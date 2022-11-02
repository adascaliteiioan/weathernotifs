package com.ai.weathernotifications.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RequestTimeChecker @Inject constructor(context: Application) {

    private val prefsName = "SharedPreferenceDemo"
    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    }

    fun saveNotificationRequestTime(time: Long) {
        preferences[NOTIFICATIONS_REQUEST_TIME] = time
    }

    fun canMakeNotificationsRequest(): Boolean {
        val lastRequestTime: Long = preferences[NOTIFICATIONS_REQUEST_TIME] ?: 0
        val currentTime = System.currentTimeMillis()
        return currentTime - lastRequestTime > NOTIFICATION_PAUSE_TIME
    }

    fun saveAffectedZonesRequestTime(time: Long) {
        preferences[AFFECTED_ZONES_REQUEST_TIME] = time
    }

    fun canMakeAffectedZonesRequest(): Boolean {
        val lastRequestTime: Long = preferences[AFFECTED_ZONES_REQUEST_TIME] ?: 0
        val currentTime = System.currentTimeMillis()
        return currentTime - lastRequestTime > AFFECTED_ZONES_PAUSE_TIME
    }

    companion object {
        private const val NOTIFICATIONS_REQUEST_TIME = "NOTIFICATIONS_REQUEST_TIME"
        private val NOTIFICATION_PAUSE_TIME = TimeUnit.MINUTES.toMillis(15)

        private const val AFFECTED_ZONES_REQUEST_TIME = "AFFECTED_ZONES_REQUEST_TIME"
        private val AFFECTED_ZONES_PAUSE_TIME = TimeUnit.MINUTES.toMillis(15)
    }
}