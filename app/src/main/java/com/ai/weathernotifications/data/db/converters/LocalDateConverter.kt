package com.ai.weathernotifications.data.db.converters

import androidx.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId

class LocalDateConverter {

    @TypeConverter
    fun toLocalDate(date: Long): LocalDate {
        return Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long {
        return date.toEpochDay()
    }
}