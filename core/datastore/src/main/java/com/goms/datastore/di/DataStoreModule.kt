package com.goms.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.goms.datastore.AuthToken
import com.goms.datastore.SettingInfo
import com.goms.datastore.serializer.AuthTokenSerializer
import com.goms.datastore.serializer.SettingSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideAuthTokenDataStore(
        @ApplicationContext context: Context,
        authTokenSerializer: AuthTokenSerializer
    ): DataStore<AuthToken> =
        DataStoreFactory.create(
            serializer = authTokenSerializer,
        ) {
            context.dataStoreFile("authToken.pb")
        }

    @Provides
    @Singleton
    fun provideSettingInfoDataStore(
        @ApplicationContext context: Context,
        settingSerializer: SettingSerializer
    ): DataStore<SettingInfo> =
        DataStoreFactory.create(
            serializer = settingSerializer,
        ) {
            context.dataStoreFile("settingInfo.pb")
        }
}