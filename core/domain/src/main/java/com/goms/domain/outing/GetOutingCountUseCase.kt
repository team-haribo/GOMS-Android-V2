package com.goms.domain.outing

import com.goms.data.repository.outing.OutingRepository
import com.goms.model.response.outing.CountResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOutingCountUseCase @Inject constructor(
    private val outingRepository: OutingRepository
) {
    suspend operator fun invoke(): Flow<CountResponse> =
        outingRepository.getOutingCount()
}