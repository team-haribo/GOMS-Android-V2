package com.goms.domain.account

import com.goms.data.repository.account.AccountRepository
import com.goms.model.request.account.RePasswordRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RePasswordUseCase @Inject constructor(
    private val accountRepository: AccountRepository
)
{
    suspend operator fun invoke(body: RePasswordRequest): Flow<Unit> {
        return accountRepository.rePassword(body = body)
    }
}