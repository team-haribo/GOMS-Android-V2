package com.goms.network.datasource.outing

import com.goms.network.api.OutingAPI
import com.goms.network.dto.response.outing.CountResponse
import com.goms.network.dto.response.outing.OutingResponse
import com.goms.network.util.GomsApiHandler
import com.goms.network.util.performApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class OutingDataSourceImpl @Inject constructor(
    private val outingAPI: OutingAPI
) : OutingDataSource {
    override fun outing(outingUUID: UUID): Flow<Unit> =
        performApiRequest { outingAPI.outing(outingUUID = outingUUID) }

    override fun getOutingList(): Flow<List<OutingResponse>> =
        performApiRequest { outingAPI.getOutingList() }

    override fun getOutingCount(): Flow<CountResponse> =
        performApiRequest { outingAPI.getOutingCount() }

    override fun outingSearch(name: String): Flow<List<OutingResponse>> =
        performApiRequest { outingAPI.outingSearch(name = name) } }
