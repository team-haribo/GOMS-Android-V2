package com.goms.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingDataSource @Inject constructor(
    private val settingInfo: DataStore<SettingInfo>
) {
    fun getThemeValue(): Flow<String> = settingInfo.data.map {
        it.theme ?: "Dark"
    }

    suspend fun setThemeValue(themeValue: String) {
        settingInfo.updateData {
            it.toBuilder()
                .setTheme(themeValue)
                .build()
        }
    }

    fun getAlarmValue(): Flow<String> = settingInfo.data.map {
        it.alarm ?: "Off"
    }

    suspend fun setAlarmValue(alarmValue: String) {
        settingInfo.updateData {
            it.toBuilder()
                .setAlarm(alarmValue)
                .build()
        }
    }

    fun getQrcodeValue(): Flow<String> = settingInfo.data.map {
        it.qrcode ?: "Off"
    }

    suspend fun setQrcodeValue(qrcodeValue: String) {
        settingInfo.updateData {
            it.toBuilder()
                .setQrcode(qrcodeValue)
                .build()
        }
    }
}