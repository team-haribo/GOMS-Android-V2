package com.goms.network.datasource.account

import com.goms.model.response.account.ProfileResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.http.Multipart

interface AccountDataSource {
    suspend fun getProfile(): Flow<ProfileResponse>

    suspend fun uploadProfileImage(file: MultipartBody.Part): Flow<Unit>
}