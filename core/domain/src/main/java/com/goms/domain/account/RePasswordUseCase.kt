package com.goms.domain.account

import com.goms.data.repository.account.AccountRepository
import com.goms.model.request.account.RePasswordRequest
import javax.inject.Inject

class RePasswordUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(body: RePasswordRequest) = kotlin.runCatching {
        accountRepository.rePassword(body = body)
    }
}