package com.goms.network.util

import com.goms.common.exception.TokenExpirationException
import com.goms.datastore.AuthTokenDataSource
import com.goms.model.util.ResourceKeys
import com.goms.network.dto.response.auth.LoginResponse
import com.goms.network.BuildConfig
import com.goms.network.di.RequestUrls
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
            RequestUrls.AUTH.auth,
            RequestUrls.ACCOUNT.newPassword
        )
    }

    private companion object {
        const val POST = "POST"
        const val GET = "GET"
        const val DELETE = "DELETE"
        const val PATCH = "PATCH"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val currentTime = System.currentTimeMillis().toGomsTimeDate()
        val path = request.url.encodedPath
        val method = request.method

        if (ignorePaths.any { path.contains(it) } && method in listOf(POST, GET)) {
            val response = chain.proceed(request)
            return if (response.code == 204) {
                response.newBuilder().code(200).build()
            } else {
                response
            }
        }

        runBlocking {
            val accessToken = dataSource.getAccessToken().first().replace("\"", "")
            val refreshToken = dataSource.getRefreshToken().first().replace("\"", "")
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
                val moshi = Moshi.Builder().build()
                val refreshRequest = Request.Builder()
                    .url(BuildConfig.BASE_URL + RequestUrls.AUTH.auth)
                    .patch(chain.request().body ?: RequestBody.create(null, byteArrayOf()))
                    .addHeader("refreshToken", "${ResourceKeys.BEARER} $refreshToken")
                    .build()
                val response = OkHttpClient().newCall(refreshRequest).execute()

                if (response.isSuccessful) {
                    val token = moshi.adapter(LoginResponse::class.java).fromJson(response.body!!.string())
                        ?: throw TokenExpirationException()
                    with(dataSource) {
                        setAccessToken(token.accessToken)
                        setRefreshToken(token.refreshToken)
                        setAccessTokenExp(token.accessTokenExp)
                        setRefreshTokenExp(token.refreshTokenExp)
                        setAuthority(token.authority.name)
                    }
                } else throw TokenExpirationException()
            }

            val isAuthEndpoint = path.endsWith(RequestUrls.AUTH.auth)

            if (isAuthEndpoint && method in listOf(DELETE, PATCH)) {
                builder.addHeader("refreshToken", "${ResourceKeys.BEARER} $refreshToken")
            } else {
                builder.addHeader("Authorization", "${ResourceKeys.BEARER} $accessToken")
            }
        }
        val response = chain.proceed(builder.build())

        return when (response.code) {
            204, 205 -> response.newBuilder().code(200).build()
            else -> response
        }
    }
}