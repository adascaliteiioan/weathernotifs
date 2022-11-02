package com.ai.weathernotifications.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ai.weathernotifications.data.db.AffectedZonesAreas
import com.ai.weathernotifications.data.db.WeatherNotification
import kotlinx.coroutines.flow.Flow

@Dao
interface AffectedZonesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZones(affectedZone: AffectedZonesAreas)

    @Query("SELECT * FROM affected_zones")
    fun fetchZones(): Flow<List<AffectedZonesAreas>>

    @Query("SELECT * FROM affected_zones WHERE id = :id")
    fun fetchZonesById(id: String): Flow<AffectedZonesAreas>
}