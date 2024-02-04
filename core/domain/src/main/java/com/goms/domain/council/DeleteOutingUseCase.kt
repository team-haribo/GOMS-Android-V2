package com.goms.domain.council

import com.goms.data.repository.council.CouncilRepository
import java.util.UUID
import javax.inject.Inject

class DeleteOutingUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    suspend operator fun invoke(accountIdx: UUID) = kotlin.runCatching {
        councilRepository.deleteOuting(accountIdx = accountIdx)
    }
}