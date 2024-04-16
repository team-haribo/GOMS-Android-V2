package com.goms.network.datasource.account

import com.goms.network.dto.request.account.FindPasswordRequest
import com.goms.network.dto.response.account.ProfileResponse
import com.goms.network.api.AccountAPI
import com.goms.network.util.GomsApiHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class AccountDataSourceImpl @Inject constructor(
    private val accountAPI: AccountAPI
) : AccountDataSource {
    override suspend fun getProfile(): Flow<ProfileResponse> = flow {
        emit(
            GomsApiHandler<ProfileResponse>()
                .httpRequest { accountAPI.getProfile() }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun updateProfileImage(file: MultipartBody.Part): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { accountAPI.updateProfileImage(file) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun setProfileImage(file: MultipartBody.Part): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { accountAPI.setProfileImage(file) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteProfileImage(): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { accountAPI.deleteProfileImage() }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun findPassword(body: FindPasswordRequest): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { accountAPI.rePassword(body = body) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)
}