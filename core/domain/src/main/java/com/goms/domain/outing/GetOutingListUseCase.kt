package com.goms.domain.outing

import com.goms.data.repository.outing.OutingRepository
import com.goms.model.response.outing.OutingResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOutingListUseCase @Inject constructor(
    private val outingRepository: OutingRepository
) {
    suspend operator fun invoke(): Flow<List<OutingResponse>> =
        outingRepository.getOutingList()
}