package com.ai.weathernotifications.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherNotifsApi {

    companion object {
        const val BASE_URL = "https://api.weather.gov/"
        const val PHOTO_BASE_URL = "https://picsum.photos/1000?random="
    }

    @GET("alerts/active?status=actual&message_type=alert")
    suspend fun fetchNotifs(): WeatherNotificationsResponse

    @GET("{fullUrl}")
    suspend fun fetchAffectedZones(@Path(value = "fullUrl", encoded = true) filePath: String): AffectedZonesDto
}