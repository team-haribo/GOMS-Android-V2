package com.goms.data.repository.notification

import com.goms.network.datasource.notification.NotificationDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {
    override fun saveDeviceToken(deviceToken: String): Flow<Unit> {
        return notificationDataSource.saveDeviceToken(deviceToken = deviceToken)
    }

    override fun deleteDeviceToken(): Flow<Unit> {
        return notificationDataSource.deleteDeviceToken()
    }
}