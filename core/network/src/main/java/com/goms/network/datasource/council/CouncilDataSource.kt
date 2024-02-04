package com.goms.network.datasource.council

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CouncilDataSource {
    suspend fun deleteOuting(accountIdx: UUID): Flow<Unit>
}