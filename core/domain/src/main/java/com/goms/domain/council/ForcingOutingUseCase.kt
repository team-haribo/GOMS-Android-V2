package com.goms.domain.council

import com.goms.data.repository.council.CouncilRepository
import java.util.UUID
import javax.inject.Inject

class ForcingOutingUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    operator fun invoke(outingIdx: UUID) = runCatching {
        councilRepository.forcingOuting(outingIdx = outingIdx)
    }
}