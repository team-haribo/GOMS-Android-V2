package com.goms.network.api

import com.goms.model.response.late.RankResponse
import retrofit2.http.GET

interface LateAPI {
    @GET("/api/v2/late/rank")
    suspend fun getLateRankList(): List<RankResponse>
}