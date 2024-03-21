package com.goms.network.datasource.account

import com.goms.model.request.account.RePasswordRequest
import com.goms.model.response.account.ProfileResponse
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

    override suspend fun uploadProfileImage(file: MultipartBody.Part): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { accountAPI.uploadProfileImage(file) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun rePassword(body: RePasswordRequest): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { accountAPI.rePassword(body = body) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)
}