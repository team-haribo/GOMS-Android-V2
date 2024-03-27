package com.goms.network.datasource.late

import com.goms.network.dto.response.late.RankResponse
import kotlinx.coroutines.flow.Flow

interface LateDataSource {
    suspend fun getLateRankList(): Flow<List<RankResponse>>
}