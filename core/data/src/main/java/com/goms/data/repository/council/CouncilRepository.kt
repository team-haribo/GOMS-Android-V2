package com.goms.data.repository.council

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CouncilRepository {
    suspend fun deleteOuting(accountIdx: UUID): Flow<Unit>
}