package com.goms.network.api

import com.goms.network.di.RequestUrls
import com.goms.network.dto.request.auth.LoginRequest
import com.goms.network.dto.request.auth.SendNumberRequest
import com.goms.network.dto.request.auth.SignUpRequest
import com.goms.network.dto.response.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {
    @POST(RequestUrls.AUTH.signUp)
    suspend fun signUp(
        @Body body: SignUpRequest
    )

    @POST(RequestUrls.AUTH.signIn)
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

    @PATCH(RequestUrls.AUTH.auth)
    suspend fun tokenRefresh(): LoginResponse

    @POST(RequestUrls.AUTH.send)
    suspend fun sendNumber(
        @Body body: SendNumberRequest
    )

    @GET(RequestUrls.AUTH.verify)
    suspend fun verifyNumber(
        @Query("email") email: String,
        @Query("authCode") authCode: String
    )

    @DELETE(RequestUrls.AUTH.auth)
    suspend fun logout()
}