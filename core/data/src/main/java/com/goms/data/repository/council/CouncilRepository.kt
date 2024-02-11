package com.goms.data.repository.council

import com.goms.model.request.council.AuthorityRequest
import com.goms.model.response.council.LateResponse
import com.goms.model.response.council.StudentResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import java.util.UUID

interface CouncilRepository {
    suspend fun getStudentList(): Flow<List<StudentResponse>>

    suspend fun changeAuthority(body: AuthorityRequest): Flow<Unit>

    suspend fun deleteOuting(accountIdx: UUID): Flow<Unit>

    suspend fun getLateList(date: LocalDate): Flow<List<LateResponse>>
}