package com.goms.data.repository.account

import com.goms.model.response.account.ProfileResponse
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getProfile(): Flow<ProfileResponse>
}