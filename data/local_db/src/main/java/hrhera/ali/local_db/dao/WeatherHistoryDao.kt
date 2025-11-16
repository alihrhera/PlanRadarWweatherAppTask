package hrhera.ali.local_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hrhera.ali.local_db.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherHistoryDao {

    @Query("SELECT * FROM weather_data WHERE cityName = :cityName ORDER BY timestamp DESC")
    suspend fun getWeatherHistory(cityName: String): List<WeatherEntity>

    @Query("SELECT * FROM weather_data WHERE id = :id Limit 1")
    suspend fun getWeatherHistoryDetails(id: Long): WeatherEntity?


    @Query("SELECT * FROM weather_data ORDER BY timestamp DESC")
    suspend fun getCities(): List<WeatherEntity>

    @Insert
    suspend fun insertWeather(record: WeatherEntity): Long

    @Query("DELETE FROM weather_data WHERE cityName = :cityName")
    suspend fun deleteWeatherHistoryForCity(cityName: String)

    @Query("DELETE FROM weather_data WHERE id = :id")
    suspend fun deleteWeatherHistory(id: Long)

    @Query(
        "SELECT * FROM weather_data WHERE cityName = :cityName ORDER BY timestamp DESC " +
                "LIMIT 1"
    )
    suspend fun getLatestWeatherForCity(cityName: String): WeatherEntity?

}