package com.goms.data.repository.account

import com.goms.model.request.account.RePasswordRequestModel
import com.goms.model.response.account.ProfileResponseModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AccountRepository {
    suspend fun getProfile(): Flow<ProfileResponseModel>

    suspend fun uploadProfileImage(file: MultipartBody.Part): Flow<Unit>

    suspend fun rePassword(body: RePasswordRequestModel): Flow<Unit>
}