package com.goms.network.datasource.account

import com.goms.network.api.AccountAPI
import com.goms.network.dto.request.account.FindPasswordRequest
import com.goms.network.dto.request.account.RePasswordRequest
import com.goms.network.dto.response.account.ProfileResponse
import com.goms.network.util.GomsApiHandler
import com.goms.network.util.performApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class AccountDataSourceImpl @Inject constructor(
    private val accountAPI: AccountAPI
) : AccountDataSource {
    override fun getProfile(): Flow<ProfileResponse> =
        performApiRequest { accountAPI.getProfile() }

    override fun updateProfileImage(file: MultipartBody.Part): Flow<Unit> =
        performApiRequest { accountAPI.updateProfileImage(file = file)}

    override fun setProfileImage(file: MultipartBody.Part): Flow<Unit> =
        performApiRequest {accountAPI.setProfileImage(file = file) }
    override fun deleteProfileImage(): Flow<Unit> =
        performApiRequest { accountAPI.deleteProfileImage() }

    override fun findPassword(body: FindPasswordRequest): Flow<Unit> =
        performApiRequest { accountAPI.findPassword(body = body)}

    override  fun rePassword(body: RePasswordRequest): Flow<Unit> =
        performApiRequest { accountAPI.rePassword(body = body) }

    override  fun withdraw(password: String): Flow<Unit> =
        performApiRequest { accountAPI.withdraw(password = password) }
}