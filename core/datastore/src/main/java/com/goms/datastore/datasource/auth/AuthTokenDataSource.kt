package com.goms.datastore.datasource.auth

import kotlinx.coroutines.flow.Flow

interface AuthTokenDataSource {
    fun getAccessToken(): Flow<String>

    suspend fun setAccessToken(accessToken: String)

    suspend fun removeAccessToken()

    fun getAccessTokenExp(): Flow<String>

    suspend fun setAccessTokenExp(accessTokenExp: String)

    suspend fun removeAccessTokenExp()

    fun getRefreshToken(): Flow<String>

    suspend fun setRefreshToken(refreshToken: String)

    suspend fun removeRefreshToken()

    fun getRefreshTokenExp(): Flow<String>

    suspend fun setRefreshTokenExp(refreshTokenExp: String)

    suspend fun removeRefreshTokenExp()

    fun getAuthority(): Flow<String>

    suspend fun setAuthority(authority: String)

    suspend fun removeAuthority()
}