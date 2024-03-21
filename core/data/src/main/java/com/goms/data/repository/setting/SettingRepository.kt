package com.goms.data.repository.setting

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SettingRepository {
    fun getThemeValue(): Flow<String>

    suspend fun setThemeValue(themeValue: String)

    fun getAlarmValue(): Flow<String>

    suspend fun setAlarmValue(alarmValue: String)

    fun getQrcodeValue(): Flow<String>

    suspend fun setQrcodeValue(qrcodeValue: String)
}