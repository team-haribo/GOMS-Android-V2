package com.goms.network.datasource.notification

import com.goms.network.api.NotificationAPI
import com.goms.network.util.GomsApiHandler
import com.goms.network.util.performApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val notificationAPI: NotificationAPI
) : NotificationDataSource {
    override fun saveDeviceToken(deviceToken: String): Flow<Unit> =
        performApiRequest { notificationAPI.saveDeviceToken(deviceToken) }

    override fun deleteDeviceToken(): Flow<Unit> =
        performApiRequest { notificationAPI.deleteDeviceToken() }

}