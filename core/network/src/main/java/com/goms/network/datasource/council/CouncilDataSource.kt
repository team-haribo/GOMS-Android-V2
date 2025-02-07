package com.goms.network.datasource.council

import com.goms.network.dto.request.council.AuthorityRequest
import com.goms.network.dto.response.council.LateResponse
import com.goms.network.dto.response.council.OutingUUIDResponse
import com.goms.network.dto.response.council.StudentResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import java.util.UUID

interface CouncilDataSource {
    fun getStudentList(): Flow<List<StudentResponse>>

    fun changeAuthority(body: AuthorityRequest): Flow<Unit>

    fun setBlackList(accountIdx: UUID): Flow<Unit>

    fun deleteBlackList(accountIdx: UUID): Flow<Unit>

    fun forcingOuting(outingIdx: UUID): Flow<Unit>

    fun studentSearch(
        grade: Int?,
        gender: String?,
        major: String?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<StudentResponse>>

    fun getOutingUUID(): Flow<OutingUUIDResponse>

    fun deleteOuting(accountIdx: UUID): Flow<Unit>

    fun getLateList(date: LocalDate): Flow<List<LateResponse>>
}