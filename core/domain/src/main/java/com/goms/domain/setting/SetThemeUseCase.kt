package com.goms.domain.setting

import com.goms.data.repository.setting.SettingRepository
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(theme: String) = kotlin.runCatching {
        settingRepository.setThemeValue(theme)
    }
}