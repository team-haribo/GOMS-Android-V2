package com.goms.data.repository.account

import com.goms.model.request.account.FindPasswordRequestModel
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
    override fun getProfile(): Flow<ProfileResponseModel> {
        return remoteAccountDataSource.getProfile().map { it.toModel() }
    }

    override fun updateProfileImage(file: MultipartBody.Part): Flow<Unit> {
        return remoteAccountDataSource.updateProfileImage(file)
    }

    override fun setProfileImage(file: MultipartBody.Part): Flow<Unit> {
        return remoteAccountDataSource.setProfileImage(file)
    }

    override fun deleteProfileImage(): Flow<Unit> {
        return remoteAccountDataSource.deleteProfileImage()
    }

    override fun findPassword(body: FindPasswordRequestModel): Flow<Unit> {
        return remoteAccountDataSource.findPassword(body = body.toDto())
    }

    override fun rePassword(body: RePasswordRequestModel): Flow<Unit> {
        return remoteAccountDataSource.rePassword(body = body.toDto())
    }

    override fun withdraw(password: String): Flow<Unit> {
        return remoteAccountDataSource.withdraw(password = password)
    }
}