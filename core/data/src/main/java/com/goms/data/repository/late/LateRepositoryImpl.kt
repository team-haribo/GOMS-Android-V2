package com.goms.data.repository.late

import com.goms.model.response.late.RankResponseModel
import com.goms.network.dto.response.late.RankResponse
import com.goms.network.datasource.late.LateDataSource
import com.goms.network.mapper.response.late.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LateRepositoryImpl @Inject constructor(
    private val remoteLateDataSource: LateDataSource
) : LateRepository {
    override suspend fun getLateRankList(): Flow<List<RankResponseModel>> {
        return remoteLateDataSource.getLateRankList().map { list -> list.map { it.toModel() } }
    }
}