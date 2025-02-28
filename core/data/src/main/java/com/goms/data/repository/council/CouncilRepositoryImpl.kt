package com.goms.data.repository.council

import com.goms.model.request.council.AuthorityRequestModel
import com.goms.model.response.council.LateResponseModel
import com.goms.model.response.council.OutingUUIDResponseModel
import com.goms.model.response.council.StudentResponseModel
import com.goms.network.datasource.council.CouncilDataSource
import com.goms.network.mapper.request.council.toDto
import com.goms.network.mapper.response.council.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import java.util.UUID
import javax.inject.Inject

class CouncilRepositoryImpl @Inject constructor(
    private val remoteCouncilDataSource: CouncilDataSource
) : CouncilRepository {

    override fun forcingOuting(outingIdx: UUID): Flow<Unit> {
        return remoteCouncilDataSource.forcingOuting(outingIdx = outingIdx)
    }

    override fun getStudentList(): Flow<List<StudentResponseModel>> {
        return remoteCouncilDataSource.getStudentList().map { list -> list.map { it.toModel() } }
    }

    override fun changeAuthority(body: AuthorityRequestModel): Flow<Unit> {
        return remoteCouncilDataSource.changeAuthority(body = body.toDto())
    }

    override fun setBlackList(accountIdx: UUID): Flow<Unit> {
        return remoteCouncilDataSource.setBlackList(accountIdx = accountIdx)
    }

    override fun deleteBlackList(accountIdx: UUID): Flow<Unit> {
        return remoteCouncilDataSource.deleteBlackList(accountIdx = accountIdx)
    }

    override fun studentSearch(
        grade: Int?,
        gender: String?,
        major: String?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<StudentResponseModel>> {
        return remoteCouncilDataSource.studentSearch(
            grade = grade,
            gender = gender,
            major = major,
            name = name,
            isBlackList = isBlackList,
            authority = authority
        ).map { list -> list.map { it.toModel() } }
    }

    override fun getOutingUUID(): Flow<OutingUUIDResponseModel> {
        return remoteCouncilDataSource.getOutingUUID().map { it.toModel() }
    }

    override fun deleteOuting(accountIdx: UUID): Flow<Unit> {
        return remoteCouncilDataSource.deleteOuting(accountIdx = accountIdx)
    }

    override fun getLateList(date: LocalDate): Flow<List<LateResponseModel>> {
        return remoteCouncilDataSource.getLateList(date = date).map { list -> list.map { it.toModel() } }
    }
}