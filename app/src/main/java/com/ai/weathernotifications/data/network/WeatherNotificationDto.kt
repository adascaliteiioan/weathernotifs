package com.ai.weathernotifications.data.network

import com.ai.weathernotifications.data.db.AffectedZones
import com.ai.weathernotifications.data.db.WeatherNotification
import com.ai.weathernotifications.util.toISODateTime
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate

data class WeatherNotificationDto(
    val id: String,
    @SerializedName("event")
    val name: String,
    @SerializedName("effective")
    val startDate: String,
    @SerializedName("expires")
    val endDate: String,
    val headline: String,
    @SerializedName("senderName")
    val source: String,
    val severity: String,
    val certainty: String,
    val urgency: String,
    val description: String,
    val affectedZones: List<String>,
    val instruction: String?
) {
    fun convert(imageUrl: String) = WeatherNotification(
        id = id,
        name = name,
        startDate = startDate.toISODateTime(),
        endDate = endDate.toISODateTime(),
        source = source,
        imageUrl = imageUrl,
        headline = headline,
        severity = severity,
        urgency = urgency,
        certainty = certainty,
        description = description,
        affectedZones = AffectedZones(affectedZones),
        instruction = instruction
    )
}

data class WeatherNotificationsResponse(
    @SerializedName("features")
    val features: List<Properties>
)

data class Properties(
    @SerializedName("properties")
    val notifications: WeatherNotificationDto
)


