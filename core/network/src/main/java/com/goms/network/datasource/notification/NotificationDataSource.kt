package com.goms.network.datasource.notification

import kotlinx.coroutines.flow.Flow

interface NotificationDataSource {
    suspend fun saveDeviceToken(deviceToken: String): Flow<Unit>
}