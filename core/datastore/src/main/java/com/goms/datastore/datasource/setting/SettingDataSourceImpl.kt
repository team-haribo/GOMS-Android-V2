package com.goms.datastore.datasource.setting

import androidx.datastore.core.DataStore
import com.goms.datastore.SettingInfo
import com.goms.model.enum.Switch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingDataSourceImpl @Inject constructor(
    private val settingInfo: DataStore<SettingInfo>
) : SettingDataSource {
    override fun getThemeValue(): Flow<String> = settingInfo.data.map {
        if (it.theme.isNullOrEmpty()) "Dark" else it.theme
    }

    override suspend fun setThemeValue(themeValue: String) {
        settingInfo.updateData {
            it.toBuilder()
                .setTheme(themeValue)
                .build()
        }
    }

    override fun getAlarmValue(): Flow<String> = settingInfo.data.map {
        if (it.alarm.isNullOrEmpty()) Switch.OFF.value else it.alarm
    }

    override suspend fun setAlarmValue(alarmValue: String) {
        settingInfo.updateData {
            it.toBuilder()
                .setAlarm(alarmValue)
                .build()
        }
    }

    override fun getQrcodeValue(): Flow<String> = settingInfo.data.map {
        if (it.qrcode.isNullOrEmpty()) Switch.OFF.value else it.qrcode
    }

    override suspend fun setQrcodeValue(qrcodeValue: String) {
        settingInfo.updateData {
            it.toBuilder()
                .setQrcode(qrcodeValue)
                .build()
        }
    }

    override fun getTimeValue(): Flow<String> = settingInfo.data.map {
        if (it.time.isNullOrEmpty()) Switch.OFF.value else it.time
    }

    override suspend fun setTimeValue(timeValue: String) {
        settingInfo.updateData {
            it.toBuilder()
                .setTime(timeValue)
                .build()
        }
    }
}