package com.goms.network.util

import com.goms.datastore.AuthTokenDataSource
import com.goms.model.util.ResourceKeys
import com.goms.network.BuildConfig
import com.goms.network.api.AuthAPI
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val dataSource: AuthTokenDataSource
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking { dataSource.getRefreshToken().first() }

        val newAccessToken = refreshAccessToken(refreshToken)

        return if (newAccessToken.isNullOrEmpty()) {
            null
        } else {
            response.request.newBuilder()
                .header("Authorization", "${ResourceKeys.BEARER} $newAccessToken")
                .build()
        }
    }

    private fun refreshAccessToken(refreshToken: String): String? {
        return try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val authApi = retrofit.create(AuthAPI::class.java)
            val response = runBlocking { authApi.tokenRefresh("${ResourceKeys.BEARER} $refreshToken") }

            runBlocking {
                with(dataSource) {
                    setAccessToken(response.accessToken)
                    setRefreshToken(response.refreshToken)
                    setAccessTokenExp(response.accessTokenExp)
                    setRefreshTokenExp(response.refreshTokenExp)
                    setAuthority(response.authority.name)
                }
            }

            response.accessToken
        } catch (e: Exception) {
            null
        }
    }
}
