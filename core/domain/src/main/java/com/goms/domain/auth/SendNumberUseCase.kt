package com.goms.domain.auth

import com.goms.data.repository.auth.AuthRepository
import com.goms.model.request.auth.SendNumberRequestModel
import javax.inject.Inject

class SendNumberUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(body: SendNumberRequestModel) = kotlin.runCatching {
        authRepository.sendNumber(body = body)
    }
}