package com.goms.domain.account

import com.goms.data.repository.account.AccountRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class DeleteProfileImageUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() = kotlin.runCatching {
        accountRepository.deleteProfileImage()
    }
}