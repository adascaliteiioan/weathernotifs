package com.ai.weathernotifications.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "affected_zones")
data class AffectedZonesAreas(
    @PrimaryKey
    val id: String,
    val coordinates: CoordinatesList
)

data class CoordinatesList(
    val coord: List<Coordinates>
)

data class Coordinates(
    val long: Double,
    val lat: Double
)
