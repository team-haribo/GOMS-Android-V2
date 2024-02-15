package com.goms.network.api

import com.goms.model.response.outing.CountResponse
import com.goms.model.response.outing.OutingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface OutingAPI {
    @POST
    suspend fun outing(
        @Path("outingUUID") outingUUID: UUID
    ): Response<Unit>
    @GET("/api/v2/outing/")
    suspend fun getOutingList(): List<OutingResponse>

    @GET("/api/v2/outing/count")
    suspend fun getOutingCount(): CountResponse

    @GET("/api/v2/outing/search")
    suspend fun outingSearch(
        @Query("name") name: String
    ): List<OutingResponse>
}