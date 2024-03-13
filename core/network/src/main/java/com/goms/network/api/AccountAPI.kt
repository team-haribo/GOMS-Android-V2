package com.goms.network.api

import com.goms.model.response.account.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AccountAPI {
    @GET("/api/v2/account/profile")
    suspend fun getProfile(): ProfileResponse

    @Multipart
    @POST("/api/v2/account/image")
    suspend fun uploadProfileImage(
        @Part file: MultipartBody.Part
    )
}