package com.goms.domain.notification

import com.goms.data.repository.notification.NotificationRepository
import javax.inject.Inject

class DeleteDeviceTokenUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke() = kotlin.runCatching {
        notificationRepository.deleteDeviceToken()
    }
}