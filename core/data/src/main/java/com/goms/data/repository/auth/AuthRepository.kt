package com.goms.data.repository.auth

import com.goms.model.request.auth.LoginRequestModel
import com.goms.model.request.auth.SendNumberRequestModel
import com.goms.model.request.auth.SignUpRequestModel
import com.goms.model.response.auth.LoginResponseModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(body: SignUpRequestModel): Flow<Unit>

    suspend fun login(body: LoginRequestModel): Flow<LoginResponseModel>

    suspend fun tokenRefresh(): Flow<LoginResponseModel>

    suspend fun saveToken(token: LoginResponseModel)

    fun getRole(): Flow<String>

    suspend fun setRole(role: String)

    suspend fun deleteToken()

    suspend fun sendNumber(body: SendNumberRequestModel): Flow<Unit>

    suspend fun verifyNumber(email: String, authCode: String): Flow<Unit>

    suspend fun logout(): Flow<Unit>
}