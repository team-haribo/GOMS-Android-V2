package com.goms.network.util

import com.goms.common.exception.TokenExpirationException
import com.goms.datastore.AuthTokenDataSource
import com.goms.model.response.auth.LoginResponse
import com.goms.network.BuildConfig
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataSource: AuthTokenDataSource,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val builder = request.newBuilder()
        val currentTime = System.currentTimeMillis().toGomsTimeDate()
        val ignorePath = listOf("/auth")
        val ignoreMethod = listOf("POST")
        val path = request.url.encodedPath
        val method = request.method

        ignorePath.forEachIndexed { index, s ->
            if (s == path && ignoreMethod[index] == method) {
                return chain.proceed(request)
            }
        }

        if (response.code == 204) {
            return response.newBuilder().code(200).build()
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
                val refreshTokenWithBearer = "Bearer $refreshToken"

                val moshi = Moshi.Builder().build()

                val refreshTokenAdapter: JsonAdapter<LoginResponse> =
                    moshi.adapter(LoginResponse::class.java)

                val refreshRequest = Request.Builder()
                    .url(BuildConfig.BASE_URL + "/api/v2/auth")
                    .patch(chain.request().body ?: RequestBody.create(null, byteArrayOf()))
                    .addHeader("Refresh-Token", refreshTokenWithBearer)
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
            builder.addHeader("Authorization", "Bearer $accessToken")
        }
        return chain.proceed(builder.build())
    }
}