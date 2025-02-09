package com.goms.network.datasource.outing

import com.goms.network.dto.response.outing.CountResponse
import com.goms.network.dto.response.outing.OutingResponse
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OutingDataSource {
    fun outing(outingUUID: UUID): Flow<Unit>

    fun getOutingList(): Flow<List<OutingResponse>>

    fun getOutingCount(): Flow<CountResponse>

    fun outingSearch(name: String): Flow<List<OutingResponse>>
}