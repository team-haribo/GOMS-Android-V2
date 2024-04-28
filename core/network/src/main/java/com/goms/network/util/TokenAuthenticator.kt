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
    private var isException: Boolean = false
    private lateinit var accessToken: String
    private lateinit var refreshToken: String

    override fun authenticate(route: Route?, response: Response): Request? {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val authApi = retrofit.create(AuthAPI::class.java)

        runBlocking {
            accessToken = dataSource.getAccessToken().first()
            refreshToken = dataSource.getRefreshToken().first()

            runCatching {
                authApi.tokenRefresh("${ResourceKeys.BEARER} $refreshToken")
            }.onSuccess {
                with(dataSource) {
                    setAccessToken(it.accessToken)
                    setRefreshToken(it.refreshToken)
                    setAccessTokenExp(it.accessTokenExp)
                    setRefreshTokenExp(it.refreshTokenExp)
                    setAuthority(it.authority.name)
                }
                accessToken = it.accessToken
            }.onFailure {
                isException = true
            }
        }

        return buildRequest(
            accessToken = accessToken,
            response = response
        )
    }

    private fun buildRequest(accessToken: String, response: Response): Request? {
        return if (isException) null
        else response.request.newBuilder()
            .addHeader("Authorization", "${ResourceKeys.BEARER} $accessToken")
            .build()
    }
}