package com.goms.domain.council

import com.goms.data.repository.council.CouncilRepository
import com.goms.model.response.council.LateResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class GetLateListUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    suspend operator fun invoke(date: LocalDate): Flow<List<LateResponse>> =
        councilRepository.getLateList(date = date)
}