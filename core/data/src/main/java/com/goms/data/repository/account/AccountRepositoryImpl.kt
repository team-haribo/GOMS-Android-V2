package com.goms.data.repository.account

import com.goms.model.request.account.RePasswordRequestModel
import com.goms.model.response.account.ProfileResponseModel
import com.goms.network.datasource.account.AccountDataSource
import com.goms.network.mapper.request.account.toDto
import com.goms.network.mapper.response.account.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val remoteAccountDataSource: AccountDataSource
) : AccountRepository {
    override suspend fun getProfile(): Flow<ProfileResponseModel> {
        return remoteAccountDataSource.getProfile().map { it.toModel() }
    }

    override suspend fun uploadProfileImage(file: MultipartBody.Part): Flow<Unit> {
        return remoteAccountDataSource.uploadProfileImage(file)
    }

    override suspend fun rePassword(body: RePasswordRequestModel): Flow<Unit> {
        return remoteAccountDataSource.rePassword(body = body.toDto())
    }
}