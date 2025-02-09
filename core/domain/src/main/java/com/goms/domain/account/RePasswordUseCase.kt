package com.goms.domain.account

import com.goms.data.repository.account.AccountRepository
import com.goms.model.request.account.RePasswordRequestModel
import javax.inject.Inject

class RePasswordUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(body: RePasswordRequestModel) = kotlin.runCatching {
        accountRepository.rePassword(body = body)
    }
}