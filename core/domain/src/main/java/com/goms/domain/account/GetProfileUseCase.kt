package com.goms.domain.account

import com.goms.data.repository.account.AccountRepository
import com.goms.model.response.account.ProfileResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Flow<ProfileResponseModel> =
        accountRepository.getProfile()
}