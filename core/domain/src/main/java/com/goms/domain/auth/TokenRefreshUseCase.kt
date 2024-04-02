package com.goms.domain.auth

import com.goms.data.repository.auth.AuthRepository
import com.goms.model.response.auth.LoginResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenRefreshUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() : Flow<LoginResponseModel> =
        authRepository.tokenRefresh()
}