package com.goms.domain.late

import com.goms.data.repository.late.LateRepository
import com.goms.model.response.late.RankResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLateRankListUseCase @Inject constructor(
    private val lateRepository: LateRepository
) {
    operator fun invoke(): Flow<List<RankResponseModel>> =
        lateRepository.getLateRankList()
}