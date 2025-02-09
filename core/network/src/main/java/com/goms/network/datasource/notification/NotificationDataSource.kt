package com.goms.network.datasource.notification

import kotlinx.coroutines.flow.Flow

interface NotificationDataSource {
    fun saveDeviceToken(deviceToken: String): Flow<Unit>

    fun deleteDeviceToken(): Flow<Unit>
}