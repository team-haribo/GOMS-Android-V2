package com.goms.network.datasource.account

import com.goms.model.response.account.ProfileResponse
import kotlinx.coroutines.flow.Flow

interface AccountDataSource {
    suspend fun getProfile(): Flow<ProfileResponse>
}