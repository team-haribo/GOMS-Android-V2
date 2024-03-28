package com.goms.data.repository.notification

import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun saveDeviceToken(deviceToken: String): Flow<Unit>

    suspend fun deleteDeviceToken(): Flow<Unit>
}