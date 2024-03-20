package com.goms.data.repository.auth

import com.goms.model.request.auth.LoginRequest
import com.goms.model.request.auth.SendNumberRequest
import com.goms.model.request.auth.SignUpRequest
import com.goms.model.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(body: SignUpRequest): Flow<Unit>

    suspend fun login(body: LoginRequest): Flow<LoginResponse>

    suspend fun saveToken(token: LoginResponse)

    fun getRole(): Flow<String>

    suspend fun setRole(role: String)

    suspend fun deleteToken()

    suspend fun sendNumber(body: SendNumberRequest): Flow<Unit>

    suspend fun verifyNumber(email: String, authCode: String): Flow<Unit>

    suspend fun logout(): Flow<Unit>
}