package com.goms.data.repository.outing

import com.goms.model.response.outing.CountResponse
import com.goms.model.response.outing.OutingResponse
import com.goms.network.datasource.outing.OutingDataSource
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class OutingRepositoryImpl @Inject constructor(
    private val remoteOutingDataSource: OutingDataSource
) : OutingRepository {
    override suspend fun outing(outingUUID: UUID): Flow<Unit> {
        return remoteOutingDataSource.outing(outingUUID)
    }

    override suspend fun getOutingList(): Flow<List<OutingResponse>> {
        return remoteOutingDataSource.getOutingList()
    }

    override suspend fun getOutingCount(): Flow<CountResponse> {
        return remoteOutingDataSource.getOutingCount()
    }

    override suspend fun outingSearch(name: String): Flow<List<OutingResponse>> {
        return remoteOutingDataSource.outingSearch(name = name)
    }
}