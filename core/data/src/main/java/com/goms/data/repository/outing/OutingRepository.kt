package com.goms.data.repository.outing

import com.goms.model.response.outing.CountResponseModel
import com.goms.model.response.outing.OutingResponseModel
import com.goms.network.dto.response.outing.CountResponse
import com.goms.network.dto.response.outing.OutingResponse
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OutingRepository {
    suspend fun outing(outingUUID: UUID): Flow<Unit>

    suspend fun getOutingList(): Flow<List<OutingResponseModel>>

    suspend fun getOutingCount(): Flow<CountResponseModel>

    suspend fun outingSearch(name: String): Flow<List<OutingResponseModel>>
}