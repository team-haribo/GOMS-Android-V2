package com.goms.network.datasource.outing

import com.goms.model.response.outing.CountResponse
import com.goms.model.response.outing.OutingResponse
import com.goms.network.api.OutingAPI
import com.goms.network.util.GomsApiHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class OutingDataSourceImpl @Inject constructor(
    private val outingAPI: OutingAPI
) : OutingDataSource {
    override suspend fun outing(outingUUID: UUID): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { outingAPI.outing(outingUUID) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getOutingList(): Flow<List<OutingResponse>> = flow {
        emit(
            GomsApiHandler<List<OutingResponse>>()
                .httpRequest { outingAPI.getOutingList() }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getOutingCount(): Flow<CountResponse> = flow {
        emit(
            GomsApiHandler<CountResponse>()
                .httpRequest { outingAPI.getOutingCount() }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun outingSearch(name: String): Flow<List<OutingResponse>> = flow {
        emit(
            GomsApiHandler<List<OutingResponse>>()
                .httpRequest { outingAPI.outingSearch(name = name) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)
}