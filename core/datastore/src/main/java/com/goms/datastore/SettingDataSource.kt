package com.goms.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingDataSource @Inject constructor(
    private val settingInfo: DataStore<SettingInfo>
) {
    fun getThemeValue(): Flow<String> = settingInfo.data.map {
        if (it.theme.isNullOrEmpty()) "Dark" else it.theme
    }

    suspend fun setThemeValue(themeValue: String) {
        settingInfo.updateData {
            it.toBuilder()
                .setTheme(themeValue)
                .build()
        }
    }

    fun getAlarmValue(): Flow<String> = settingInfo.data.map {
        if (it.alarm.isNullOrEmpty()) "On" else it.alarm
    }

    suspend fun setAlarmValue(alarmValue: String) {
        settingInfo.updateData {
            it.toBuilder()
                .setAlarm(alarmValue)
                .build()
        }
    }

    fun getQrcodeValue(): Flow<String> = settingInfo.data.map {
        if (it.qrcode.isNullOrEmpty()) "Off" else it.qrcode
    }

    suspend fun setQrcodeValue(qrcodeValue: String) {
        settingInfo.updateData {
            it.toBuilder()
                .setQrcode(qrcodeValue)
                .build()
        }
    }
}