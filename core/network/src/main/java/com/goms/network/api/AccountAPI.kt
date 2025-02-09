package com.goms.network.api

import com.goms.network.di.RequestUrls
import com.goms.network.dto.request.account.FindPasswordRequest
import com.goms.network.dto.request.account.RePasswordRequest
import com.goms.network.dto.response.account.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AccountAPI {
    @GET(RequestUrls.ACCOUNT.profile)
    fun getProfile(): ProfileResponse

    @Multipart
    @PATCH(RequestUrls.ACCOUNT.image)
    fun updateProfileImage(
        @Part file: MultipartBody.Part
    )

    @Multipart
    @POST(RequestUrls.ACCOUNT.image)
    fun setProfileImage(
        @Part file: MultipartBody.Part
    )

    @DELETE(RequestUrls.ACCOUNT.account)
    fun deleteProfileImage()

    @PATCH(RequestUrls.ACCOUNT.newPassword)
    fun findPassword(
        @Body body: FindPasswordRequest
    )

    @PATCH(RequestUrls.ACCOUNT.changePassword)
    fun rePassword(
        @Body body: RePasswordRequest
    )

    @DELETE(RequestUrls.ACCOUNT.withdraw)
    fun withdraw(
        @Path("password") password: String
    )
}