package com.goms.network.datasource.auth

import com.goms.network.dto.request.auth.LoginRequest
import com.goms.network.dto.request.auth.SendNumberRequest
import com.goms.network.dto.request.auth.SignUpRequest
import com.goms.network.dto.response.auth.LoginResponse
import com.goms.network.api.AuthAPI
import com.goms.network.util.GomsApiHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authAPI: AuthAPI
) : AuthDataSource {
    override suspend fun signUp(body: SignUpRequest): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { authAPI.signUp(body = body) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun login(body: LoginRequest): Flow<LoginResponse> = flow {
        emit(
            GomsApiHandler<LoginResponse>()
                .httpRequest { authAPI.login(body = body) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun tokenRefresh(refreshToken: String): Flow<LoginResponse> = flow {
        emit(
            GomsApiHandler<LoginResponse>()
                .httpRequest { authAPI.tokenRefresh(refreshToken = refreshToken) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun sendNumber(body: SendNumberRequest): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { authAPI.sendNumber(body = body) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun verifyNumber(email: String, authCode: String): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { authAPI.verifyNumber(email = email, authCode = authCode) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun logout(): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { authAPI.logout() }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)
}