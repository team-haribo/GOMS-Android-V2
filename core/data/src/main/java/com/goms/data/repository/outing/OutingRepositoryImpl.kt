package com.goms.data.repository.outing

import com.goms.model.response.outing.CountResponseModel
import com.goms.model.response.outing.OutingResponseModel
import com.goms.network.dto.response.outing.CountResponse
import com.goms.network.dto.response.outing.OutingResponse
import com.goms.network.datasource.outing.OutingDataSource
import com.goms.network.mapper.response.outing.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class OutingRepositoryImpl @Inject constructor(
    private val remoteOutingDataSource: OutingDataSource
) : OutingRepository {
    override suspend fun outing(outingUUID: UUID): Flow<Unit> {
        return remoteOutingDataSource.outing(outingUUID)
    }

    override suspend fun getOutingList(): Flow<List<OutingResponseModel>> {
        return remoteOutingDataSource.getOutingList().map { list -> list.map { it.toModel() } }
    }

    override suspend fun getOutingCount(): Flow<CountResponseModel> {
        return remoteOutingDataSource.getOutingCount().map { it.toModel() }
    }

    override suspend fun outingSearch(name: String): Flow<List<OutingResponseModel>> {
        return remoteOutingDataSource.outingSearch(name = name).map { list -> list.map { it.toModel() } }
    }
}