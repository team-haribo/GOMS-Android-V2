package com.goms.datastore.datasource.setting

import kotlinx.coroutines.flow.Flow

interface SettingDataSource {
    fun getThemeValue(): Flow<String>

    suspend fun setThemeValue(themeValue: String)

    fun getAlarmValue(): Flow<String>

    suspend fun setAlarmValue(alarmValue: String)

    fun getQrcodeValue(): Flow<String>

    suspend fun setQrcodeValue(qrcodeValue: String)

    fun getTimeValue(): Flow<String>

    suspend fun setTimeValue(timeValue: String)
}