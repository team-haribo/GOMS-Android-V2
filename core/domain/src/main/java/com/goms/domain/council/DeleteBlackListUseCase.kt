package com.goms.domain.council

import com.goms.data.repository.council.CouncilRepository
import java.util.UUID
import javax.inject.Inject

class DeleteBlackListUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    operator fun invoke(accountIdx: UUID) = kotlin.runCatching {
        councilRepository.deleteBlackList(accountIdx = accountIdx)
    }
}