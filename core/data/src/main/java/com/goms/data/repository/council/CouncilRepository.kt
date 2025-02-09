package com.goms.data.repository.council

import com.goms.model.request.council.AuthorityRequestModel
import com.goms.model.response.council.LateResponseModel
import com.goms.model.response.council.OutingUUIDResponseModel
import com.goms.model.response.council.StudentResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import java.util.UUID

interface CouncilRepository {
    fun forcingOuting(outingIdx: UUID): Flow<Unit>

    fun getStudentList(): Flow<List<StudentResponseModel>>

    fun changeAuthority(body: AuthorityRequestModel): Flow<Unit>

    fun setBlackList(accountIdx: UUID): Flow<Unit>

    fun deleteBlackList(accountIdx: UUID): Flow<Unit>

    fun studentSearch(
        grade: Int?,
        gender: String?,
        major: String?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<StudentResponseModel>>

    fun getOutingUUID(): Flow<OutingUUIDResponseModel>

    fun deleteOuting(accountIdx: UUID): Flow<Unit>

    fun getLateList(date: LocalDate): Flow<List<LateResponseModel>>
}