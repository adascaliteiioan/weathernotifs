package com.ai.weathernotifications.ui.notifications

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ai.weathernotifications.data.repo.WeatherNotifRepo
import com.ai.weathernotifications.data.worker.DbCleanWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class WeatherNotificationsViewModel @Inject constructor(
    private val context: Application,
    private val weatherRepo: WeatherNotifRepo
): AndroidViewModel(context) {

    init {
        runCleanDbWorker()
    }

    val weatherData = weatherRepo.fetchWeatherNotfs().
            stateIn(viewModelScope, SharingStarted.Lazily, null)

    private fun runCleanDbWorker() {
        val myWork = PeriodicWorkRequestBuilder<DbCleanWorker>(
            10, TimeUnit.SECONDS
        )
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "CleanDbWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            myWork
        )
    }
}