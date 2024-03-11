package com.goms.network.api

import com.goms.model.request.auth.LoginRequest
import com.goms.model.request.auth.SendNumberRequest
import com.goms.model.request.auth.SignUpRequest
import com.goms.model.response.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {
    @POST("/api/v2/auth/signup")
    suspend fun signUp(
        @Body body: SignUpRequest
    )

    @POST("/api/v2/auth/signin")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

    @POST("/api/v2/auth/email/send")
    suspend fun sendNumber(
        @Body body: SendNumberRequest
    )

    @GET("/api/v2/auth/email/verify")
    suspend fun verifyNumber(
        @Query("email") email: String,
        @Query("authCode") authCode: String
    )

    @DELETE("/api/v2/auth")
    suspend fun logout()
}