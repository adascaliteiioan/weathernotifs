package com.ai.weathernotifications

import android.app.Application
import androidx.work.Configuration
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

}
