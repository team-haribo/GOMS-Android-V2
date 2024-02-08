package com.goms.network.datasource.council

import com.goms.model.response.council.LateResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import java.util.UUID

interface CouncilDataSource {
    suspend fun deleteOuting(accountIdx: UUID): Flow<Unit>

    suspend fun getLateList(date: LocalDate): Flow<List<LateResponse>>
}