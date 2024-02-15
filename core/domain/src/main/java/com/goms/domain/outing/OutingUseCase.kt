package com.goms.domain.outing

import com.goms.data.repository.outing.OutingRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class OutingUseCase @Inject constructor(
    private val outingRepository: OutingRepository
) {
<<<<<<< HEAD
    suspend operator fun invoke(outingUUID: UUID) = kotlin.runCatching {
        outingRepository.outing(outingUUID)
    }
=======
    suspend operator fun invoke(outingUUID: UUID): Flow<Unit> =
        outingRepository.outing(outingUUID)
>>>>>>> f9910a9 (:memo: :: Add OutingUseCase.kt)
}