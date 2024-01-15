package com.goms.network.datasource.auth

import com.goms.model.request.auth.LoginRequest
import com.goms.model.response.auth.LoginResponse
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
    override suspend fun login(body: LoginRequest): Flow<LoginResponse> = flow {
        emit(
            GomsApiHandler<LoginResponse>()
                .httpRequest { authAPI.login(body = body) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)
}