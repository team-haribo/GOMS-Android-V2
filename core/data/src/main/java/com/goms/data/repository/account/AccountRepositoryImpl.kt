package com.goms.data.repository.account

import com.goms.model.request.account.RePasswordRequest
import com.goms.model.response.account.ProfileResponse
import com.goms.network.datasource.account.AccountDataSource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val remoteAccountDataSource: AccountDataSource
) : AccountRepository {
    override suspend fun getProfile(): Flow<ProfileResponse> {
        return remoteAccountDataSource.getProfile()
    }

    override suspend fun uploadProfileImage(file: MultipartBody.Part): Flow<Unit> {
        return remoteAccountDataSource.uploadProfileImage(file)
    }

    override suspend fun rePassword(body: RePasswordRequest): Flow<Unit> {
        return remoteAccountDataSource.rePassword(body = body)
    }
}