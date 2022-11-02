package com.ai.weathernotifications.data.db.converters

import androidx.room.TypeConverter
import com.ai.weathernotifications.data.db.AffectedZones
import com.google.gson.Gson

class AffectedZonesToStringConverter {

    @TypeConverter
    fun fromListToString(data: AffectedZones): String = Gson().toJson(data).toString()

    @TypeConverter
    fun fromStringToList(data: String): AffectedZones = Gson().fromJson(data, AffectedZones::class.java)
}