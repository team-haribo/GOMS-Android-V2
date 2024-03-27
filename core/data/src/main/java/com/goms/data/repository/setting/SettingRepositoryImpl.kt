package com.goms.data.repository.setting

import com.goms.datastore.SettingDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingDataSource: SettingDataSource
) : SettingRepository {
    override fun getThemeValue(): Flow<String> {
        return settingDataSource.getThemeValue()
    }

    override suspend fun setThemeValue(themeValue: String) {
        settingDataSource.setThemeValue(themeValue)
    }

    override fun getAlarmValue(): Flow<String> {
        return settingDataSource.getAlarmValue()
    }

    override suspend fun setAlarmValue(alarmValue: String) {
        settingDataSource.setAlarmValue(alarmValue)
    }

    override fun getQrcodeValue(): Flow<String> {
        return settingDataSource.getQrcodeValue()
    }

    override suspend fun setQrcodeValue(qrcodeValue: String) {
        settingDataSource.setQrcodeValue(qrcodeValue)
    }

}