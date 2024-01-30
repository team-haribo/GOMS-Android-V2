package com.goms.network.datasource.account

import com.goms.model.response.account.ProfileResponse
import com.goms.network.api.AccountAPI
import com.goms.network.util.GomsApiHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
}