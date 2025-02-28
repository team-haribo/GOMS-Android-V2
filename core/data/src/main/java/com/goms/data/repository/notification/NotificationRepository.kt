package com.goms.data.repository.notification

import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun saveDeviceToken(deviceToken: String): Flow<Unit>

    fun deleteDeviceToken(): Flow<Unit>
}