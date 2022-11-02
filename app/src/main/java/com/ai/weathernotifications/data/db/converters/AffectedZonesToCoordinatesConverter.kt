package com.ai.weathernotifications.data.db.converters

import androidx.room.TypeConverter
import com.ai.weathernotifications.data.db.CoordinatesList
import com.google.gson.Gson

class AffectedZonesToCoordinatesConverter {

    @TypeConverter
    fun fromListCoordinatesToString(data: CoordinatesList): String =
        Gson().toJson(data)

    @TypeConverter
    fun fromStringToCoordinates(data: String): CoordinatesList =
        Gson().fromJson(data, CoordinatesList::class.java)
}