package com.goms.network.util

import com.goms.datastore.AuthTokenDataSource
import com.goms.model.util.ResourceKeys
import com.goms.network.di.RequestUrls
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
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
        val path = request.url.encodedPath
        val method = request.method

        val accessToken = runBlocking { dataSource.getAccessToken().first() }
        val refreshToken = runBlocking { dataSource.getRefreshToken().first() }

        val newRequest = when {
            ignorePaths.any { path.contains(it) } && method in listOf(POST, GET) -> {
                request
            }
            path.endsWith(RequestUrls.AUTH.auth) && method in listOf(DELETE, PATCH) -> {
                request.newBuilder().addHeader("refreshToken", "${ResourceKeys.BEARER} $refreshToken").build()
            }
            else -> {
                request.newBuilder().addHeader("Authorization", "${ResourceKeys.BEARER} $accessToken").build()
            }
        }

        val response = chain.proceed(newRequest)

        return when (response.code) {
            204, 205 -> response.newBuilder().code(200).build()
            else -> response
        }
    }
}