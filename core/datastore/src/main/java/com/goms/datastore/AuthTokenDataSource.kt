package com.goms.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthTokenDataSource @Inject constructor(
    private val authToken: DataStore<AuthToken>
) {
    fun getAccessToken(): Flow<String> = authToken.data.map {
        it.accessToken ?: ""
    }

    suspend fun setAccessToken(accessToken: String) {
        authToken.updateData {
            it.toBuilder()
                .setAccessToken(accessToken)
                .build()
        }
    }

    fun getAccessTokenExp(): Flow<String> = authToken.data.map {
        it.accessExp ?: ""
    }

    suspend fun setAccessTokenExp(accessTokenExp: String) {
        authToken.updateData {
            it.toBuilder()
                .setAccessExp(accessTokenExp)
                .build()
        }
    }

    fun getRefreshToken(): Flow<String> = authToken.data.map {
        it.refreshToken ?: ""
    }

    suspend fun setRefreshToken(refreshToken: String) {
        authToken.updateData {
            it.toBuilder()
                .setRefreshToken(refreshToken)
                .build()
        }
    }

    fun getRefreshTokenExp(): Flow<String> = authToken.data.map {
        it.refreshExp ?: ""
    }

    suspend fun setRefreshTokenExp(refreshTokenExp: String) {
        authToken.updateData {
            it.toBuilder()
                .setRefreshExp(refreshTokenExp)
                .build()
        }
    }

    fun getAuthority(): Flow<String> = authToken.data.map {
        it.authority ?: ""
    }

    suspend fun setAuthority(authority: String) {
        authToken.updateData {
            it.toBuilder()
                .setAuthority(authority)
                .build()
        }
    }
}