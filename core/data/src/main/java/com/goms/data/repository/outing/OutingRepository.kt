package com.goms.data.repository.outing

import com.goms.model.response.outing.CountResponseModel
import com.goms.model.response.outing.OutingResponseModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OutingRepository {
    fun outing(outingUUID: UUID): Flow<Unit>

    fun getOutingList(): Flow<List<OutingResponseModel>>

    fun getOutingCount(): Flow<CountResponseModel>

    fun outingSearch(name: String): Flow<List<OutingResponseModel>>
}