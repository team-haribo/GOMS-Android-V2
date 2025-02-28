package com.goms.network.datasource.late

import com.goms.network.api.LateAPI
import com.goms.network.dto.response.late.RankResponse
import com.goms.network.util.GomsApiHandler
import com.goms.network.util.performApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LateDataSourceImpl @Inject constructor(
    private val lateAPI: LateAPI
) : LateDataSource {
    override fun getLateRankList(): Flow<List<RankResponse>> =
        performApiRequest { lateAPI.getLateRankList() }
}