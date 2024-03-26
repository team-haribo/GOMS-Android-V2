package com.goms.domain.auth

import com.goms.data.repository.auth.AuthRepository
import com.goms.model.response.auth.LoginResponseModel
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(token: LoginResponseModel) = kotlin.runCatching {
        authRepository.saveToken(token = token)
    }
}