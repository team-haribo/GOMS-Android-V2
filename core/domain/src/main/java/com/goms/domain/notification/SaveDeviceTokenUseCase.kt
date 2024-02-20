package com.goms.domain.notification

import com.goms.data.repository.notification.NotificationRepository
import javax.inject.Inject

class SaveDeviceTokenUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(deviceToken: String) = kotlin.runCatching {
        notificationRepository.saveDeviceToken(deviceToken = deviceToken)
    }
}