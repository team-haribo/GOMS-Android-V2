package com.goms.domain.setting

import com.goms.data.repository.setting.SettingRepository
import javax.inject.Inject

class SetTimeUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(time: String) = runCatching {
        settingRepository.setTimeValue(time)
    }
}