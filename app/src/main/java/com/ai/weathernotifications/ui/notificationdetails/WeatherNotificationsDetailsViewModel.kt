package com.ai.weathernotifications.ui.notificationdetails

import android.app.Application
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ai.weathernotifications.data.db.AffectedZonesAreas
import com.ai.weathernotifications.data.db.WeatherNotification
import com.ai.weathernotifications.data.repo.WeatherNotifRepo
import com.ai.weathernotifications.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class WeatherNotificationsDetailsViewModel
@Inject constructor(
    private val app: Application,
    private val weatherNotifRepo: WeatherNotifRepo
) : AndroidViewModel(app) {

    private var _affectedZones = MutableStateFlow<Set<String?>>(mutableSetOf())
    val affectedZones: StateFlow<Set<String?>> get() = _affectedZones

    suspend fun fetchWeatherNotifInfo(id: String): Flow<WeatherNotification?> =
        weatherNotifRepo.fetchNotifById(id).stateIn(viewModelScope, SharingStarted.Lazily, null)


    fun fetchAffectedZones(fileUrls: List<String>?) {
        viewModelScope.launch {
            if (fileUrls.isNullOrEmpty()) return@launch
            fileUrls.forEach { url ->
                weatherNotifRepo.fetchAffectedZones(url).collect {
                    when (it) {
                        is Resource.Success -> {
                            it.data?.let { zones ->
                                getZonesDetails(zones)
                            }
                        }
                        is Resource.Error -> Log.v("eroare", it.error?.message.orEmpty())
                        is Resource.Loading -> {
                            it.data?.let { zones ->
                                getZonesDetails(zones)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getZonesDetails(zones: AffectedZonesAreas) {
        viewModelScope.launch {
            val locations = mutableListOf<String?>()
            locations.addAll(zones.coordinates.coord.map { address ->
                getCityName(address.lat, address.long)
            })

            _affectedZones.value = _affectedZones.value.plus((locations))
        }
    }

    private suspend fun getCityName(lat: Double, long: Double): String {
        return viewModelScope.async(Dispatchers.IO) {
            var cityName: String? = null
            val geoCoder = Geocoder(app, Locale.getDefault())
            val address = geoCoder.getFromLocation(lat, long, 3)
            if (address.isNotEmpty()) {
                cityName = address[0].locality ?: address[0].adminArea
            }

            return@async cityName.orEmpty()
        }.await()
    }
}