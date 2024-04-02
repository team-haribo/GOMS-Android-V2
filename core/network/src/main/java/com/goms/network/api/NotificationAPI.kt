package com.goms.network.api

import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationAPI {
    @POST("/api/v2/notification/token/{deviceToken}")
    suspend fun saveDeviceToken(
        @Path("deviceToken") deviceToken: String
    )

    @DELETE("/api/v2/notification/token")
    suspend fun deleteDeviceToken()
}