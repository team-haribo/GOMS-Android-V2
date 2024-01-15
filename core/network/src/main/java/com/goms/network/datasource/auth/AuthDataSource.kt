package com.goms.network.datasource.auth

import com.goms.model.request.auth.LoginRequest
import com.goms.model.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    suspend fun login(body: LoginRequest): Flow<LoginResponse>
}