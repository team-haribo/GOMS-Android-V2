package com.goms.data.repository.outing

import com.goms.model.response.outing.CountResponse
import com.goms.model.response.outing.OutingResponse
import kotlinx.coroutines.flow.Flow

interface OutingRepository {
    suspend fun getOutingList(): Flow<List<OutingResponse>>

    suspend fun getOutingCount(): Flow<CountResponse>
}