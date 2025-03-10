package com.goms.domain.council

import com.goms.data.repository.council.CouncilRepository
import java.util.UUID
import javax.inject.Inject

class DeleteOutingUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    operator fun invoke(accountIdx: UUID) = runCatching {
        councilRepository.deleteOuting(accountIdx = accountIdx)
    }
}