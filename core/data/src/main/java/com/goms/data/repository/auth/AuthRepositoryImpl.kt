package com.goms.data.repository.auth

import com.goms.model.request.auth.LoginRequest
import com.goms.model.response.auth.LoginResponse
import com.goms.network.datasource.auth.AuthDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun login(body: LoginRequest): Flow<LoginResponse> {
        return remoteAuthDataSource.login(body = body)
    }
}