package com.goms.data.repository.late

import com.goms.model.response.late.RankResponse
import com.goms.network.datasource.late.LateDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LateRepositoryImpl @Inject constructor(
    private val remoteLateDataSource: LateDataSource
) : LateRepository {
    override suspend fun getLateRankList(): Flow<List<RankResponse>> {
        return remoteLateDataSource.getLateRankList()
    }
}