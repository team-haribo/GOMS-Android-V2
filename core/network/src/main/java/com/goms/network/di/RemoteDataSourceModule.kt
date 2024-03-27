package com.goms.network.di

import com.goms.network.datasource.account.AccountDataSource
import com.goms.network.datasource.account.AccountDataSourceImpl
import com.goms.network.datasource.auth.AuthDataSource
import com.goms.network.datasource.auth.AuthDataSourceImpl
import com.goms.network.datasource.council.CouncilDataSource
import com.goms.network.datasource.council.CouncilDataSourceImpl
import com.goms.network.datasource.late.LateDataSource
import com.goms.network.datasource.late.LateDataSourceImpl
import com.goms.network.datasource.notification.NotificationDataSource
import com.goms.network.datasource.notification.NotificationDataSourceImpl
import com.goms.network.datasource.outing.OutingDataSource
import com.goms.network.datasource.outing.OutingDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    abstract fun bindAccountDataSource(
        accountDataSourceImpl: AccountDataSourceImpl
    ): AccountDataSource

    @Binds
    abstract fun bindLateDataSource(
        lateDataSourceImpl: LateDataSourceImpl
    ): LateDataSource

    @Binds
    abstract fun bindOutingDataSource(
        outingDataSourceImpl: OutingDataSourceImpl
    ): OutingDataSource

    @Binds
    abstract fun bindCouncilDataSource(
        councilDataSourceImpl: CouncilDataSourceImpl
    ): CouncilDataSource

    @Binds
    abstract fun bindNotificationDataSource(
        notificationDataSourceImpl: NotificationDataSourceImpl
    ): NotificationDataSource
}