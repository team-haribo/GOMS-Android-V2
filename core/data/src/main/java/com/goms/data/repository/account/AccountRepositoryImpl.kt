package com.goms.data.repository.account

import com.goms.model.response.account.ProfileResponse
import com.goms.network.datasource.account.AccountDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val remoteAccountDataSource: AccountDataSource
) : AccountRepository {
    override suspend fun getProfile(): Flow<ProfileResponse> {
        return remoteAccountDataSource.getProfile()
    }
}