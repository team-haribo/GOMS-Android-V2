package com.goms.domain.auth

import com.goms.data.repository.auth.AuthRepository
import com.goms.model.request.auth.SignUpRequest
import javax.inject.Inject

class SighUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(body: SignUpRequest) = kotlin.runCatching {
        authRepository.signUp(body = body)
    }
}