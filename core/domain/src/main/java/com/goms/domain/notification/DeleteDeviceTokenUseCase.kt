package com.goms.domain.notification

import com.goms.data.repository.notification.NotificationRepository
import javax.inject.Inject

class DeleteDeviceTokenUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke() = runCatching {
        notificationRepository.deleteDeviceToken()
    }
}