package com.goms.data.repository.late

import com.goms.model.response.late.RankResponseModel
import kotlinx.coroutines.flow.Flow

interface LateRepository {
    fun getLateRankList(): Flow<List<RankResponseModel>>
}