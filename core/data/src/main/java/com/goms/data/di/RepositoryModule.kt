package com.goms.data.di

import com.goms.data.repository.account.AccountRepository
import com.goms.data.repository.account.AccountRepositoryImpl
import com.goms.data.repository.auth.AuthRepository
import com.goms.data.repository.auth.AuthRepositoryImpl
import com.goms.data.repository.council.CouncilRepository
import com.goms.data.repository.council.CouncilRepositoryImpl
import com.goms.data.repository.late.LateRepository
import com.goms.data.repository.late.LateRepositoryImpl
import com.goms.data.repository.notification.NotificationRepository
import com.goms.data.repository.notification.NotificationRepositoryImpl
import com.goms.data.repository.outing.OutingRepository
import com.goms.data.repository.outing.OutingRepositoryImpl
import com.goms.data.repository.setting.SettingRepository
import com.goms.data.repository.setting.SettingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    abstract fun bindLateRepository(
        lateRepositoryImpl: LateRepositoryImpl
    ): LateRepository

    @Binds
    abstract fun bindOutingRepository(
        outingRepositoryImpl: OutingRepositoryImpl
    ): OutingRepository

    @Binds
    abstract fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository

    @Binds
    abstract fun bindCouncilRepository(
        councilRepositoryImpl: CouncilRepositoryImpl
    ): CouncilRepository

    @Binds
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository
}