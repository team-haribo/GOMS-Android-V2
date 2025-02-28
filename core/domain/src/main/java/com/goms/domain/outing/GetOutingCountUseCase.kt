package com.goms.domain.outing

import com.goms.data.repository.outing.OutingRepository
import com.goms.model.response.outing.CountResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOutingCountUseCase @Inject constructor(
    private val outingRepository: OutingRepository
) {
    operator fun invoke(): Flow<CountResponseModel> =
        outingRepository.getOutingCount()
}