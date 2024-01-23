package com.goms.data.repository.auth

import com.goms.datastore.AuthTokenDataSource
import com.goms.model.request.auth.LoginRequest
import com.goms.model.request.auth.SendNumberRequest
import com.goms.model.request.auth.SignUpRequest
import com.goms.model.response.auth.LoginResponse
import com.goms.network.datasource.auth.AuthDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: AuthDataSource,
    private val localAuthDataSource: AuthTokenDataSource
) : AuthRepository {
    override suspend fun signUp(body: SignUpRequest): Flow<Unit> {
        return remoteAuthDataSource.signUp(body = body)
    }

    override suspend fun login(body: LoginRequest): Flow<LoginResponse> {
        return remoteAuthDataSource.login(body = body)
    }

    override suspend fun saveToken(token: LoginResponse) {
        token.let {
            localAuthDataSource.setAccessToken(it.accessToken)
            localAuthDataSource.setRefreshToken(it.refreshToken)
            localAuthDataSource.setAccessTokenExp(it.accessTokenExp)
            localAuthDataSource.setRefreshTokenExp(it.refreshTokenExp)
            localAuthDataSource.setAuthority(it.authority.name)
        }
    }

    override suspend fun sendNumber(body: SendNumberRequest): Flow<Unit> {
        return remoteAuthDataSource.sendNumber(body = body)
    }

    override suspend fun verifyNumber(email: String, authCode: String): Flow<Unit> {
        return remoteAuthDataSource.verifyNumber(email = email, authCode = authCode)
    }
}