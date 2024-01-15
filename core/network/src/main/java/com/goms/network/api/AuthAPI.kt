package com.goms.network.api

import com.goms.model.request.auth.LoginRequest
import com.goms.model.response.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("signin")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse
}