package com.goms.data.repository.auth

import com.goms.datastore.datasource.auth.AuthTokenDataSource
import com.goms.model.request.auth.LoginRequestModel
import com.goms.model.request.auth.SendNumberRequestModel
import com.goms.model.request.auth.SignUpRequestModel
import com.goms.model.response.auth.LoginResponseModel
import com.goms.network.datasource.auth.AuthDataSource
import com.goms.network.mapper.request.auth.toDto
import com.goms.network.mapper.response.auth.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: AuthDataSource,
    private val localAuthDataSource: AuthTokenDataSource
) : AuthRepository {
    override suspend fun signUp(body: SignUpRequestModel): Flow<Unit> {
        return remoteAuthDataSource.signUp(body = body.toDto())
    }

    override suspend fun login(body: LoginRequestModel): Flow<LoginResponseModel> {
        return remoteAuthDataSource.login(body = body.toDto()).map { it.toModel() }
    }

    override suspend fun tokenRefresh(refreshToken: String): Flow<LoginResponseModel> {
        return remoteAuthDataSource.tokenRefresh(refreshToken = refreshToken).map { it.toModel() }
    }

    override suspend fun saveToken(token: LoginResponseModel) {
        token.let {
            localAuthDataSource.setAccessToken(it.accessToken)
            localAuthDataSource.setRefreshToken(it.refreshToken)
            localAuthDataSource.setAccessTokenExp(it.accessTokenExp)
            localAuthDataSource.setRefreshTokenExp(it.refreshTokenExp)
            localAuthDataSource.setAuthority(it.authority.name)
        }
    }

    override fun getRefreshToken(): Flow<String> {
        return localAuthDataSource.getRefreshToken()
    }

    override fun getRole(): Flow<String> {
        return localAuthDataSource.getAuthority()
    }

    override suspend fun setRole(role: String) {
        role.let { localAuthDataSource.setAuthority(it) }
    }

    override suspend fun deleteToken() {
        localAuthDataSource.removeAccessToken()
        localAuthDataSource.removeRefreshToken()
        localAuthDataSource.removeAccessTokenExp()
        localAuthDataSource.removeRefreshTokenExp()
        localAuthDataSource.removeAuthority()
    }

    override suspend fun sendNumber(body: SendNumberRequestModel): Flow<Unit> {
        return remoteAuthDataSource.sendNumber(body = body.toDto())
    }

    override suspend fun verifyNumber(email: String, authCode: String): Flow<Unit> {
        return remoteAuthDataSource.verifyNumber(email = email, authCode = authCode)
    }

    override suspend fun logout(): Flow<Unit> {
        return remoteAuthDataSource.logout()
    }
}