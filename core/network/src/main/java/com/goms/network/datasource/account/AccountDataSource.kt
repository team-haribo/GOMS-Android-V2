package com.goms.network.datasource.account

import com.goms.network.dto.request.account.FindPasswordRequest
import com.goms.network.dto.request.account.RePasswordRequest
import com.goms.network.dto.response.account.ProfileResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AccountDataSource {
    suspend fun getProfile(): Flow<ProfileResponse>

    suspend fun updateProfileImage(file: MultipartBody.Part): Flow<Unit>

    suspend fun setProfileImage(file: MultipartBody.Part): Flow<Unit>

    suspend fun deleteProfileImage(): Flow<Unit>

    suspend fun findPassword(body: FindPasswordRequest): Flow<Unit>

    suspend fun rePassword(body: RePasswordRequest): Flow<Unit>
}