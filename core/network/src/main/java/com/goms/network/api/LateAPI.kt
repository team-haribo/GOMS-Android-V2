package com.goms.network.api

import com.goms.network.di.RequestUrls
import com.goms.network.dto.response.late.RankResponse
import retrofit2.http.GET

interface LateAPI {
    @GET(RequestUrls.LATE.rank)
    fun getLateRankList(): List<RankResponse>
}