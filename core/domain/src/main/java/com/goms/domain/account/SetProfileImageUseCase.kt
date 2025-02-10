package com.goms.domain.account

import com.goms.data.repository.account.AccountRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class SetProfileImageUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(file: MultipartBody.Part) = runCatching {
        accountRepository.setProfileImage(file)
    }
}