package com.goms.network.datasource.account

import com.goms.network.dto.request.account.RePasswordRequest
import com.goms.network.dto.response.account.ProfileResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AccountDataSource {
    suspend fun getProfile(): Flow<ProfileResponse>

    suspend fun uploadProfileImage(file: MultipartBody.Part): Flow<Unit>

    suspend fun rePassword(body: RePasswordRequest): Flow<Unit>
}