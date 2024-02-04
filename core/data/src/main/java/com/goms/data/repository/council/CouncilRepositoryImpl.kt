package com.goms.data.repository.council

import com.goms.network.datasource.council.CouncilDataSource
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class CouncilRepositoryImpl @Inject constructor(
    private val remoteCouncilDataSource: CouncilDataSource
) : CouncilRepository {
    override suspend fun deleteOuting(accountIdx: UUID): Flow<Unit> {
        return remoteCouncilDataSource.deleteOuting(accountIdx = accountIdx)
    }
}