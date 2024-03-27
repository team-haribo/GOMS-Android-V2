package com.goms.domain.council

import com.goms.data.repository.council.CouncilRepository
import com.goms.model.response.council.OutingUUIDResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOutingUUIDUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    suspend operator fun invoke(): Flow<OutingUUIDResponseModel> =
        councilRepository.getOutingUUID()
}