package com.goms.domain.council

import com.goms.data.repository.council.CouncilRepository
import java.util.UUID
import javax.inject.Inject

class ForcingOutingUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    suspend operator fun invoke(outingIdx: UUID) = kotlin.runCatching {
        councilRepository.forcingOuting(outingIdx = outingIdx)
    }
}