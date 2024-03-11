package com.goms.network.datasource.auth

import com.goms.model.request.auth.LoginRequest
import com.goms.model.request.auth.SendNumberRequest
import com.goms.model.request.auth.SignUpRequest
import com.goms.model.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    suspend fun signUp(body: SignUpRequest): Flow<Unit>

    suspend fun login(body: LoginRequest): Flow<LoginResponse>

    suspend fun sendNumber(body: SendNumberRequest): Flow<Unit>

    suspend fun verifyNumber(email: String, authCode: String): Flow<Unit>

    suspend fun logout(): Flow<Unit>
}