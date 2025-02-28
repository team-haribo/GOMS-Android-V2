package com.goms.network.datasource.council

import com.goms.network.api.CouncilAPI
import com.goms.network.dto.request.council.AuthorityRequest
import com.goms.network.dto.response.council.LateResponse
import com.goms.network.dto.response.council.OutingUUIDResponse
import com.goms.network.dto.response.council.StudentResponse
import com.goms.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import java.util.UUID
import javax.inject.Inject

class CouncilDataSourceImpl @Inject constructor(
    private val councilAPI: CouncilAPI
) : CouncilDataSource {

    override fun forcingOuting(outingIdx: UUID): Flow<Unit> =
        performApiRequest { councilAPI.forcingOuting(outingIdx = outingIdx) }

    override fun getStudentList(): Flow<List<StudentResponse>> =
        performApiRequest { councilAPI.getStudentList() }

    override fun changeAuthority(body: AuthorityRequest): Flow<Unit> =
        performApiRequest { councilAPI.changeAuthority(body = body) }

    override fun setBlackList(accountIdx: UUID): Flow<Unit> =
        performApiRequest { councilAPI.setBlackList(accountIdx = accountIdx) }

    override fun deleteBlackList(accountIdx: UUID): Flow<Unit> =
        performApiRequest { councilAPI.deleteBlackList(accountIdx = accountIdx) }

    override fun studentSearch(
        grade: Int?,
        gender: String?,
        major: String?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<StudentResponse>> = performApiRequest {
        councilAPI.studentSearch(
            grade = grade,
            gender = gender,
            major = major,
            name = name,
            isBlackList = isBlackList,
            authority = authority
        )
    }

    override fun getOutingUUID(): Flow<OutingUUIDResponse> =
        performApiRequest { councilAPI.getOutingUUID() }

    override fun deleteOuting(accountIdx: UUID): Flow<Unit> =
        performApiRequest { councilAPI.deleteOuting(accountIdx = accountIdx) }

    override fun getLateList(date: LocalDate): Flow<List<LateResponse>> =
        performApiRequest { councilAPI.getLateList(date = date) }
}