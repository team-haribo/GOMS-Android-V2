package com.goms.domain.account

import com.goms.data.repository.account.AccountRepository
import com.goms.model.request.account.FindPasswordRequestModel
import javax.inject.Inject

class FindPasswordUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(body: FindPasswordRequestModel) = runCatching {
        accountRepository.findPassword(body = body)
    }
}