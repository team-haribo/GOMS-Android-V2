package com.goms.domain.setting

import com.goms.data.repository.setting.SettingRepository
import javax.inject.Inject

class SetQrcodeUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(qrcode: String) = runCatching {
        settingRepository.setQrcodeValue(qrcode)
    }
}