package com.goms.network.api

import com.goms.network.di.RequestUrls
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationAPI {
    @POST(RequestUrls.NOTIFICATION.saveToken)
    suspend fun saveDeviceToken(
        @Path("deviceToken") deviceToken: String,
    )

    @DELETE(RequestUrls.NOTIFICATION.deleteToken)
    suspend fun deleteDeviceToken()
}