package com.ai.weathernotifications.data.network

import com.ai.weathernotifications.data.db.AffectedZonesAreas
import com.ai.weathernotifications.data.db.Coordinates
import com.ai.weathernotifications.data.db.CoordinatesList

data class AffectedZonesDto(
    val id: String,
    val geometry: Geometry
) {
    fun convert(): AffectedZonesAreas {
        val coordinatesDto = geometry.coordinates.flatten()
        val coordinates = coordinatesDto.map { coord -> Coordinates(coord[0], coord[1]) }
        return AffectedZonesAreas(
            id = id,
            coordinates = CoordinatesList(coordinates)
        )
    }
}

data class Geometry(
    val coordinates: List<List<List<Double>>>
)

data class CoordinatesDto(
    val data: List<Double>
) {
    fun convert(): Coordinates =
        Coordinates(
            long = data[0],
            lat = data[1]
        )
}
