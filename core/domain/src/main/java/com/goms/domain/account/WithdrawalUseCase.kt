package com.goms.domain.account

import com.goms.data.repository.account.AccountRepository
import com.goms.model.request.account.WithdrawRequestModel
import javax.inject.Inject

class WithdrawalUseCase@Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(body: WithdrawRequestModel) = kotlin.runCatching {
        accountRepository.withdraw(body = body)
    }
}