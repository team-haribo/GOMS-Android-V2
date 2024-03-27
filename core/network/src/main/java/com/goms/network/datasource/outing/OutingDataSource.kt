package com.goms.network.datasource.outing

import com.goms.network.dto.response.outing.CountResponse
import com.goms.network.dto.response.outing.OutingResponse
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OutingDataSource {
    suspend fun outing(outingUUID: UUID): Flow<Unit>

    suspend fun getOutingList(): Flow<List<OutingResponse>>

    suspend fun getOutingCount(): Flow<CountResponse>

    suspend fun outingSearch(name: String): Flow<List<OutingResponse>>
}