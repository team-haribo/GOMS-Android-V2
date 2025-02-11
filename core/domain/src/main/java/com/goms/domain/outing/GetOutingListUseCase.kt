package com.goms.domain.outing

import com.goms.data.repository.outing.OutingRepository
import com.goms.model.response.outing.OutingResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOutingListUseCase @Inject constructor(
    private val outingRepository: OutingRepository
) {
    operator fun invoke(): Flow<List<OutingResponseModel>> =
        outingRepository.getOutingList()
}