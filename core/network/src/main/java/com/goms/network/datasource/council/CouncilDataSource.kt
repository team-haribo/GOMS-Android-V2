package com.goms.network.datasource.council

import com.goms.model.request.council.AuthorityRequest
import com.goms.model.response.council.LateResponse
import com.goms.model.response.council.OutingUUIDResponse
import com.goms.model.response.council.StudentResponse
import com.goms.model.response.outing.OutingResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import java.util.UUID

interface CouncilDataSource {
    suspend fun getStudentList(): Flow<List<StudentResponse>>

    suspend fun changeAuthority(body: AuthorityRequest): Flow<Unit>

    suspend fun setBlackList(accountIdx: UUID): Flow<Unit>

    suspend fun deleteBlackList(accountIdx: UUID): Flow<Unit>

    suspend fun studentSearch(
        grade: Int?,
        gender: String?,
        major: String?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<StudentResponse>>

    suspend fun getOutingUUID(): Flow<OutingUUIDResponse>

    suspend fun deleteOuting(accountIdx: UUID): Flow<Unit>

    suspend fun getLateList(date: LocalDate): Flow<List<LateResponse>>
}