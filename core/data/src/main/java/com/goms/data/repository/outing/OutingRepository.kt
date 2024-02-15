package com.goms.data.repository.outing

import com.goms.model.response.outing.CountResponse
import com.goms.model.response.outing.OutingResponse
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OutingRepository {
    suspend fun outing(outingUUID: UUID): Flow<Unit>

    suspend fun getOutingList(): Flow<List<OutingResponse>>

    suspend fun getOutingCount(): Flow<CountResponse>

    suspend fun outingSearch(name: String): Flow<List<OutingResponse>>
}