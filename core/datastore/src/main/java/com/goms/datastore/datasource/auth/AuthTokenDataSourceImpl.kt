package com.goms.datastore.datasource.auth

import androidx.datastore.core.DataStore
import com.goms.datastore.AuthToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthTokenDataSourceImpl @Inject constructor(
    private val authToken: DataStore<AuthToken>
) : AuthTokenDataSource {
    override fun getAccessToken(): Flow<String> = authToken.data.map {
        it.accessToken ?: ""
    }

    override suspend fun setAccessToken(accessToken: String) {
        authToken.updateData {
            it.toBuilder()
                .setAccessToken(accessToken)
                .build()
        }
    }

    override suspend fun removeAccessToken() {
        authToken.updateData {
            it.toBuilder()
                .clearAccessToken()
                .build()
        }
    }

    override fun getAccessTokenExp(): Flow<String> = authToken.data.map {
        it.accessExp ?: ""
    }

    override suspend fun setAccessTokenExp(accessTokenExp: String) {
        authToken.updateData {
            it.toBuilder()
                .setAccessExp(accessTokenExp)
                .build()
        }
    }

    override suspend fun removeAccessTokenExp() {
        authToken.updateData {
            it.toBuilder()
                .clearAccessExp()
                .build()
        }
    }

    override fun getRefreshToken(): Flow<String> = authToken.data.map {
        it.refreshToken ?: ""
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        authToken.updateData {
            it.toBuilder()
                .setRefreshToken(refreshToken)
                .build()
        }
    }

    override suspend fun removeRefreshToken() {
        authToken.updateData {
            it.toBuilder()
                .clearRefreshToken()
                .build()
        }
    }

    override fun getRefreshTokenExp(): Flow<String> = authToken.data.map {
        it.refreshExp ?: ""
    }

    override suspend fun setRefreshTokenExp(refreshTokenExp: String) {
        authToken.updateData {
            it.toBuilder()
                .setRefreshExp(refreshTokenExp)
                .build()
        }
    }

    override suspend fun removeRefreshTokenExp() {
        authToken.updateData {
            it.toBuilder()
                .clearRefreshExp()
                .build()
        }
    }

    override fun getAuthority(): Flow<String> = authToken.data.map {
        it.authority ?: ""
    }

    override suspend fun setAuthority(authority: String) {
        authToken.updateData {
            it.toBuilder()
                .setAuthority(authority)
                .build()
        }
    }

    override suspend fun removeAuthority() {
        authToken.updateData {
            it.toBuilder()
                .clearAuthority()
                .build()
        }
    }
}