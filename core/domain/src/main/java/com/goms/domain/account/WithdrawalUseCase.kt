package com.goms.domain.account

import com.goms.data.repository.account.AccountRepository
import javax.inject.Inject

class WithdrawalUseCase@Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(password: String) = kotlin.runCatching {
        accountRepository.withdraw(password = password)
    }
}