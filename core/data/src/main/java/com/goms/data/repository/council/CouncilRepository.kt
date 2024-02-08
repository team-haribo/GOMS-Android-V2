package com.goms.data.repository.council

import com.goms.model.response.council.LateResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import java.util.UUID

interface CouncilRepository {
    suspend fun deleteOuting(accountIdx: UUID): Flow<Unit>

    suspend fun getLateList(date: LocalDate): Flow<List<LateResponse>>
}