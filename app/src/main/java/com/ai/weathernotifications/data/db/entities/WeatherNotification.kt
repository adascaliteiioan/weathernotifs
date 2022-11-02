package com.ai.weathernotifications.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "weather_notification")
data class WeatherNotification(
    @PrimaryKey
    val id: String,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val source: String,
    val headline: String,
    val imageUrl: String,
    val severity: String,
    val certainty: String,
    val urgency: String,
    val description: String,
    val affectedZones: AffectedZones,
    val instruction: String?
)
data class AffectedZones(val zones: List<String>)
