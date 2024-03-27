package com.goms.data.repository.council

import com.goms.model.request.council.AuthorityRequestModel
import com.goms.model.response.council.LateResponseModel
import com.goms.model.response.council.OutingUUIDResponseModel
import com.goms.model.response.council.StudentResponseModel
import com.goms.network.dto.request.council.AuthorityRequest
import com.goms.network.dto.response.council.LateResponse
import com.goms.network.dto.response.council.OutingUUIDResponse
import com.goms.network.dto.response.council.StudentResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import java.util.UUID

interface CouncilRepository {
    suspend fun getStudentList(): Flow<List<StudentResponseModel>>

    suspend fun changeAuthority(body: AuthorityRequestModel): Flow<Unit>

    suspend fun setBlackList(accountIdx: UUID): Flow<Unit>

    suspend fun deleteBlackList(accountIdx: UUID): Flow<Unit>

    suspend fun studentSearch(
        grade: Int?,
        gender: String?,
        major: String?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<StudentResponseModel>>

    suspend fun getOutingUUID(): Flow<OutingUUIDResponseModel>

    suspend fun deleteOuting(accountIdx: UUID): Flow<Unit>

    suspend fun getLateList(date: LocalDate): Flow<List<LateResponseModel>>
}