package com.goms.domain.auth

import com.goms.data.repository.auth.AuthRepository
import javax.inject.Inject

class VerifyNumberUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, authCode: String) = kotlin.runCatching {
        authRepository.verifyNumber(email = email, authCode = authCode)
    }
}