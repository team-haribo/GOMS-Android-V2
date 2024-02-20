package com.goms.network.di

import android.util.Log
import com.goms.network.BuildConfig
import com.goms.network.api.AccountAPI
import com.goms.network.api.AuthAPI
import com.goms.network.api.CouncilAPI
import com.goms.network.api.LateAPI
import com.goms.network.api.NotificationAPI
import com.goms.network.api.OutingAPI
import com.goms.network.util.AuthInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.v("HTTP", message) }
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(CookieJar.NO_COOKIES)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshiInstance(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthAPI(retrofit: Retrofit): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountAPI(retrofit: Retrofit): AccountAPI {
        return retrofit.create(AccountAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideLateAPI(retrofit: Retrofit): LateAPI {
        return retrofit.create(LateAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideOutingAPI(retrofit: Retrofit): OutingAPI {
        return retrofit.create(OutingAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCouncilAPI(retrofit: Retrofit): CouncilAPI {
        return retrofit.create(CouncilAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationAPI(retrofit: Retrofit): NotificationAPI {
        return retrofit.create(NotificationAPI::class.java)
    }
}