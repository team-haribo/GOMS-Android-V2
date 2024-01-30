package com.goms.data.repository.outing

import com.goms.model.response.outing.CountResponse
import com.goms.model.response.outing.OutingResponse
import com.goms.network.datasource.outing.OutingDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OutingRepositoryImpl @Inject constructor(
    private val remoteOutingDataSource: OutingDataSource
) : OutingRepository {
    override suspend fun getOutingList(): Flow<List<OutingResponse>> {
        return remoteOutingDataSource.getOutingList()
    }

    override suspend fun getOutingCount(): Flow<CountResponse> {
        return remoteOutingDataSource.getOutingCount()
    }
}