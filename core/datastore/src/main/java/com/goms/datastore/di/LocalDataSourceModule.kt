package com.goms.datastore.di

import com.goms.datastore.datasource.auth.AuthTokenDataSource
import com.goms.datastore.datasource.auth.AuthTokenDataSourceImpl
import com.goms.datastore.datasource.setting.SettingDataSource
import com.goms.datastore.datasource.setting.SettingDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {
    @Binds
    abstract fun bindAuthTokenDataSource(
        authTokenDataSourceImpl: AuthTokenDataSourceImpl
    ): AuthTokenDataSource

    @Binds
    abstract fun bindSettingDataSource(
        settingDataSourceImpl: SettingDataSourceImpl
    ): SettingDataSource
}