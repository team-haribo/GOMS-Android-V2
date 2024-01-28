package com.goms.network.datasource.outing

import com.goms.model.response.outing.CountResponse
import com.goms.model.response.outing.OutingResponse
import kotlinx.coroutines.flow.Flow

interface OutingDataSource {
    suspend fun getOutingList(): Flow<List<OutingResponse>>

    suspend fun getOutingCount(): Flow<CountResponse>
}