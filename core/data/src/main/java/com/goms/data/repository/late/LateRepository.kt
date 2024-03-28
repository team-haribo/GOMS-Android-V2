package com.goms.data.repository.late

import com.goms.model.response.late.RankResponseModel
import com.goms.network.dto.response.late.RankResponse
import kotlinx.coroutines.flow.Flow

interface LateRepository {
    suspend fun getLateRankList(): Flow<List<RankResponseModel>>
}