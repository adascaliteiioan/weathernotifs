package com.ai.weathernotifications.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ai.weathernotifications.data.db.WeatherNotification
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherNotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifs(notifications: List<WeatherNotification>)

    @Query("SELECT * FROM weather_notification")
    fun fetchNotifs(): Flow<List<WeatherNotification>>

    @Query("DELETE FROM weather_notification WHERE endDate < date('now')")
    fun deleteExpiredNotifications()

    @Query("SELECT * FROM weather_notification WHERE id = :id")
    suspend fun fetchNotifById(id: String): WeatherNotification
}