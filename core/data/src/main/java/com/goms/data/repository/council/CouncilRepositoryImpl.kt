package com.goms.data.repository.council

import com.goms.model.request.council.AuthorityRequest
import com.goms.model.response.council.LateResponse
import com.goms.model.response.council.OutingUUIDResponse
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

    override suspend fun changeAuthority(body: AuthorityRequest): Flow<Unit> {
        return remoteCouncilDataSource.changeAuthority(body = body)
    }

    override suspend fun setBlackList(accountIdx: UUID): Flow<Unit> {
        return remoteCouncilDataSource.setBlackList(accountIdx = accountIdx)
    }

    override suspend fun deleteBlackList(accountIdx: UUID): Flow<Unit> {
        return remoteCouncilDataSource.deleteBlackList(accountIdx = accountIdx)
    }

    override suspend fun studentSearch(
        grade: Int?,
        gender: String?,
        major: String?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<StudentResponse>> {
        return remoteCouncilDataSource.studentSearch(
            grade = grade,
            gender = gender,
            major = major,
            name = name,
            isBlackList = isBlackList,
            authority = authority
        )
    }

    override suspend fun getOutingUUID(): Flow<OutingUUIDResponse> {
        return remoteCouncilDataSource.getOutingUUID()
    }

    override suspend fun deleteOuting(accountIdx: UUID): Flow<Unit> {
        return remoteCouncilDataSource.deleteOuting(accountIdx = accountIdx)
    }

    override suspend fun getLateList(date: LocalDate): Flow<List<LateResponse>> {
        return remoteCouncilDataSource.getLateList(date = date)
    }
}