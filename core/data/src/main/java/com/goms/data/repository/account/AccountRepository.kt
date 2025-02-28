package com.goms.data.repository.account

import com.goms.model.request.account.FindPasswordRequestModel
import com.goms.model.request.account.RePasswordRequestModel
import com.goms.model.response.account.ProfileResponseModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AccountRepository {
    fun getProfile(): Flow<ProfileResponseModel>

    fun updateProfileImage(file: MultipartBody.Part): Flow<Unit>

    fun setProfileImage(file: MultipartBody.Part): Flow<Unit>

    fun deleteProfileImage(): Flow<Unit>

    fun findPassword(body: FindPasswordRequestModel): Flow<Unit>

    fun rePassword(body: RePasswordRequestModel): Flow<Unit>

    fun withdraw(password: String): Flow<Unit>
}