package com.goms.network.datasource.notification

import com.goms.network.api.NotificationAPI
import com.goms.network.util.GomsApiHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val notificationAPI: NotificationAPI
) : NotificationDataSource {
    override suspend fun saveDeviceToken(deviceToken: String): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { notificationAPI.saveDeviceToken(deviceToken = deviceToken) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)
}