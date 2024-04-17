package com.goms.network.util

import com.goms.common.exception.TokenExpirationException
import com.goms.datastore.AuthTokenDataSource
import com.goms.model.util.ResourceKeys
import com.goms.network.dto.response.auth.LoginResponse
import com.goms.network.BuildConfig
import com.goms.network.di.RequestUrls
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataSource: AuthTokenDataSource,
): Interceptor {
    private val ignorePaths by lazy {
        listOf(
            "/auth",
            "/new-password"
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val currentTime = System.currentTimeMillis().toGomsTimeDate()
        val ignoreMethodPOST = "POST"
        val ignoreMethodGET = "GET"
        val ignoreMethodDELETE = "DELETE"
        val ignoreMethodPATCH = "PATCH"
        val path = request.url.encodedPath
        val method = request.method

        if (ignorePaths.any { path.contains(it) } && method in listOf(ignoreMethodPOST, ignoreMethodGET)) {
            val response = chain.proceed(request)
            return if (response.code == 204) {
                response.newBuilder().code(200).build()
            } else {
                response
            }
        }

        runBlocking {
            val refreshTime = dataSource.getRefreshTokenExp().first().replace("\"", "")
            val accessTime = dataSource.getAccessTokenExp().first().replace("\"", "")

            if (refreshTime == "") {
                return@runBlocking
            }

            if (currentTime.after(refreshTime.toDate())) {
                throw TokenExpirationException()
            }

            // access token 재발급
            if (currentTime.after(accessTime.toDate())) {
                val client = OkHttpClient()
                val refreshToken = dataSource.getRefreshToken().first().replace("\"", "")
                val refreshTokenWithBearer = "${ResourceKeys.BEARER} $refreshToken"

                val moshi = Moshi.Builder().build()

                val refreshTokenAdapter: JsonAdapter<LoginResponse> =
                    moshi.adapter(LoginResponse::class.java)

                val refreshRequest = Request.Builder()
                    .url(BuildConfig.BASE_URL + RequestUrls.AUTH.auth)
                    .patch(chain.request().body ?: RequestBody.create(null, byteArrayOf()))
                    .addHeader("refreshToken", refreshTokenWithBearer)
                    .build()

                val response = client.newCall(refreshRequest).execute()
                if (response.isSuccessful) {
                    val token = refreshTokenAdapter.fromJson(response.body!!.string())
                        ?: throw TokenExpirationException()
                    dataSource.setAccessToken(token.accessToken)
                    dataSource.setRefreshToken(token.refreshToken)
                    dataSource.setAccessTokenExp(token.accessTokenExp)
                    dataSource.setRefreshTokenExp(token.refreshTokenExp)
                    dataSource.setAuthority(token.authority.name)
                } else throw TokenExpirationException()
            }
            val accessToken = dataSource.getAccessToken().first().replace("\"", "")
            builder.addHeader("Authorization", "${ResourceKeys.BEARER} $accessToken")

            val isAuthEndpoint = path.endsWith("/auth")

            if (isAuthEndpoint && method == ignoreMethodDELETE || method == ignoreMethodPATCH) {
                val refreshToken = dataSource.getRefreshToken().first().replace("\"", "")
                val refreshTokenWithBearer = "${ResourceKeys.BEARER} $refreshToken"
                builder.addHeader("refreshToken", refreshTokenWithBearer)
            }
        }
        val response = chain.proceed(builder.build())

        return when (response.code) {
            204, 205 -> response.newBuilder().code(200).build()
            else -> response
        }
    }
}