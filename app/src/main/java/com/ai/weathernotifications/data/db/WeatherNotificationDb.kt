package com.ai.weathernotifications.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ai.weathernotifications.data.db.converters.AffectedZonesToCoordinatesConverter
import com.ai.weathernotifications.data.db.converters.AffectedZonesToStringConverter
import com.ai.weathernotifications.data.db.converters.LocalDateConverter
import com.ai.weathernotifications.data.db.dao.AffectedZonesDao
import com.ai.weathernotifications.data.db.dao.WeatherNotificationDao

@Database(
    entities = [WeatherNotification::class, AffectedZonesAreas::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    LocalDateConverter::class,
    AffectedZonesToStringConverter::class,
    AffectedZonesToCoordinatesConverter::class
)
abstract class WeatherNotificationDb : RoomDatabase() {

    abstract fun weatherNotifsDao(): WeatherNotificationDao

    abstract fun affectedZonesDao(): AffectedZonesDao
}