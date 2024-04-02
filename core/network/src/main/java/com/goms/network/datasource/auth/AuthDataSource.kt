package com.goms.network.datasource.auth

import com.goms.network.dto.request.auth.LoginRequest
import com.goms.network.dto.request.auth.SendNumberRequest
import com.goms.network.dto.request.auth.SignUpRequest
import com.goms.network.dto.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    suspend fun signUp(body: SignUpRequest): Flow<Unit>

    suspend fun login(body: LoginRequest): Flow<LoginResponse>

    suspend fun tokenRefresh(): Flow<LoginResponse>

    suspend fun sendNumber(body: SendNumberRequest): Flow<Unit>

    suspend fun verifyNumber(email: String, authCode: String): Flow<Unit>

    suspend fun logout(): Flow<Unit>
}