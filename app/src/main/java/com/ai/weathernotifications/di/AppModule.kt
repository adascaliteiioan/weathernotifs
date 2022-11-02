package com.ai.weathernotifications.di

import android.app.Application
import androidx.room.Room
import com.ai.weathernotifications.data.db.WeatherNotificationDb
import com.ai.weathernotifications.data.network.WeatherNotifsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(WeatherNotifsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGitApi(retrofit: Retrofit): WeatherNotifsApi =
        retrofit.create(WeatherNotifsApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): WeatherNotificationDb =
        Room.databaseBuilder(app, WeatherNotificationDb::class.java, "weather_db")
            .fallbackToDestructiveMigration()
            .build()
}