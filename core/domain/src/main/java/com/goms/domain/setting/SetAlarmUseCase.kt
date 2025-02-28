package com.goms.domain.setting

import com.goms.data.repository.setting.SettingRepository
import javax.inject.Inject

class SetAlarmUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(alarm: String) = runCatching {
        settingRepository.setAlarmValue(alarm)
    }
}