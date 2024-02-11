package com.goms.data.repository.council

import com.goms.model.response.council.LateResponse
import com.goms.model.response.council.StudentResponse
import com.goms.network.datasource.council.CouncilDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import java.util.UUID
import javax.inject.Inject

class CouncilRepositoryImpl @Inject constructor(
    private val remoteCouncilDataSource: CouncilDataSource
) : CouncilRepository {
    override suspend fun getStudentList(): Flow<List<StudentResponse>> {
        return remoteCouncilDataSource.getStudentList()
    }

    override suspend fun deleteOuting(accountIdx: UUID): Flow<Unit> {
        return remoteCouncilDataSource.deleteOuting(accountIdx = accountIdx)
    }

    override suspend fun getLateList(date: LocalDate): Flow<List<LateResponse>> {
        return remoteCouncilDataSource.getLateList(date = date)
    }
}