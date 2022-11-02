package com.ai.weathernotifications.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ai.weathernotifications.data.db.WeatherNotificationDb
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class DbCleanWorker  @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters,
    @Assisted private val db: WeatherNotificationDb
) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.v("treaba","sunt in worker")
        db.weatherNotifsDao().deleteExpiredNotifications()
        return Result.success()
    }
}