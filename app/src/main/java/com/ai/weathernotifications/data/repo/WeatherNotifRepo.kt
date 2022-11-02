package com.ai.weathernotifications.data.repo

import com.ai.weathernotifications.data.db.AffectedZonesAreas
import com.ai.weathernotifications.data.db.WeatherNotification
import com.ai.weathernotifications.data.db.WeatherNotificationDb
import com.ai.weathernotifications.data.network.WeatherNotifsApi
import com.ai.weathernotifications.util.RequestTimeChecker
import com.ai.weathernotifications.util.Resource
import com.ai.weathernotifications.util.networkBoundResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherNotifRepo @Inject constructor(
    private val api: WeatherNotifsApi,
    weatherDb: WeatherNotificationDb,
    private val requestTimeChecker: RequestTimeChecker
) {
    private val weatherNotifDao = weatherDb.weatherNotifsDao()
    private val affectedZonesDao = weatherDb.affectedZonesDao()

    fun fetchWeatherNotfs(): Flow<Resource<List<WeatherNotification>>> =
        networkBoundResult(
            query = { weatherNotifDao.fetchNotifs() },
            fetch = {
                val result = api.fetchNotifs()
                requestTimeChecker.saveNotificationRequestTime(System.currentTimeMillis())
                result.features
            },
            saveFetchResult = { serverResult ->

                val notifs = serverResult.map { properties ->
                    properties.notifications
                        .convert("${WeatherNotifsApi.PHOTO_BASE_URL}${properties.notifications.id}")
                }
                weatherNotifDao.insertNotifs(notifs)
            },
            shouldFetch = { requestTimeChecker.canMakeNotificationsRequest() }
        )

    suspend fun fetchNotifById(id: String) = flow {
        emit(weatherNotifDao.fetchNotifById(id))
    }

    fun fetchAffectedZones(fileUrl: String): Flow<Resource<AffectedZonesAreas>> =
        networkBoundResult(
            query = { affectedZonesDao.fetchZonesById(fileUrl) },
            fetch = {
                val result = api.fetchAffectedZones(fileUrl)
                requestTimeChecker.saveAffectedZonesRequestTime(System.currentTimeMillis())
                result
            },
            saveFetchResult = {
                affectedZonesDao.insertZones(it.convert())
            },
            shouldFetch = { requestTimeChecker.canMakeAffectedZonesRequest() }
        )
}
