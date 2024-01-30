package com.goms.domain.late

import com.goms.data.repository.late.LateRepository
import com.goms.model.response.late.RankResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLateRankListUseCase @Inject constructor(
    private val lateRepository: LateRepository
) {
    suspend operator fun invoke(): Flow<List<RankResponse>> =
        lateRepository.getLateRankList()
}