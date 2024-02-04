package com.goms.network.datasource.council

import com.goms.network.api.CouncilAPI
import com.goms.network.util.GomsApiHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class CouncilDataSourceImpl @Inject constructor(
    private val councilAPI: CouncilAPI
) : CouncilDataSource {
    override suspend fun deleteOuting(accountIdx: UUID): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { councilAPI.deleteOuting(accountIdx = accountIdx) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)
}