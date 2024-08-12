package com.goms.network.datasource.late

import com.goms.network.api.LateAPI
import com.goms.network.dto.response.late.RankResponse
import com.goms.network.util.GomsApiHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LateDataSourceImpl @Inject constructor(
    private val lateAPI: LateAPI
) : LateDataSource {
    override suspend fun getLateRankList(): Flow<List<RankResponse>> = flow {
        emit(
            GomsApiHandler<List<RankResponse>>()
                .httpRequest { lateAPI.getLateRankList() }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)
}