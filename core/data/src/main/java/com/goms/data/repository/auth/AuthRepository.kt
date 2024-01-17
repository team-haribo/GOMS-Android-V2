package com.goms.data.repository.auth

import com.goms.model.request.auth.LoginRequest
import com.goms.model.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(body: LoginRequest): Flow<LoginResponse>

    suspend fun saveToken(token: LoginResponse)
}