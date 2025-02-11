package com.goms.network.datasource.account

import com.goms.network.dto.request.account.FindPasswordRequest
import com.goms.network.dto.request.account.RePasswordRequest
import com.goms.network.dto.response.account.ProfileResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AccountDataSource {
    fun getProfile(): Flow<ProfileResponse>

    fun updateProfileImage(file: MultipartBody.Part): Flow<Unit>

    fun setProfileImage(file: MultipartBody.Part): Flow<Unit>

    fun deleteProfileImage(): Flow<Unit>

    fun findPassword(body: FindPasswordRequest): Flow<Unit>

    fun rePassword(body: RePasswordRequest): Flow<Unit>

    fun withdraw(password: String): Flow<Unit>
}