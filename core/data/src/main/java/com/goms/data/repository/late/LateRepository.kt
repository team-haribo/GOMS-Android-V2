package com.goms.data.repository.late

import com.goms.model.response.late.RankResponse
import kotlinx.coroutines.flow.Flow

interface LateRepository {
    suspend fun getLateRankList(): Flow<List<RankResponse>>
}