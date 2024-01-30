package com.goms.network.api

import com.goms.model.response.outing.CountResponse
import com.goms.model.response.outing.OutingResponse
import retrofit2.http.GET

interface OutingAPI {
    @GET("/api/v2/outing/")
    suspend fun getOutingList(): List<OutingResponse>

    @GET("/api/v2/outing/count")
    suspend fun getOutingCount(): CountResponse
}