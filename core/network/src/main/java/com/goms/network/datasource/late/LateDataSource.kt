package com.goms.network.datasource.late

import com.goms.model.response.late.RankResponse
import kotlinx.coroutines.flow.Flow

interface LateDataSource {
    suspend fun getLateRankList(): Flow<List<RankResponse>>
}