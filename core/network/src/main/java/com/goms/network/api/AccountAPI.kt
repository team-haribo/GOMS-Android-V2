package com.goms.network.api

import com.goms.model.response.account.ProfileResponse
import retrofit2.http.GET

interface AccountAPI {
    @GET("/api/v2/account/profile")
    suspend fun getProfile(): ProfileResponse
}