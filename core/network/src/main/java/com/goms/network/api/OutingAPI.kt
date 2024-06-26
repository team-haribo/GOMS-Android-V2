package com.goms.network.api

import com.goms.network.di.RequestUrls
import com.goms.network.dto.response.outing.CountResponse
import com.goms.network.dto.response.outing.OutingResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface OutingAPI {
    @POST(RequestUrls.OUTING.outingUUID)
    suspend fun outing(
        @Path("outingUUID") outingUUID: UUID
    )

    @GET(RequestUrls.OUTING.outing)
    suspend fun getOutingList(): List<OutingResponse>

    @GET(RequestUrls.OUTING.count)
    suspend fun getOutingCount(): CountResponse

    @GET(RequestUrls.OUTING.search)
    suspend fun outingSearch(
        @Query("name") name: String
    ): List<OutingResponse>
}