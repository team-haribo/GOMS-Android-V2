package com.goms.data.repository.account

import com.goms.model.request.account.FindPasswordRequestModel
import com.goms.model.response.account.ProfileResponseModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AccountRepository {
    suspend fun getProfile(): Flow<ProfileResponseModel>

    suspend fun updateProfileImage(file: MultipartBody.Part): Flow<Unit>

    suspend fun setProfileImage(file: MultipartBody.Part): Flow<Unit>

    suspend fun deleteProfileImage(): Flow<Unit>

    suspend fun findPassword(body: FindPasswordRequestModel): Flow<Unit>
}