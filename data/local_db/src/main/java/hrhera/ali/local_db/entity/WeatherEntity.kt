package hrhera.ali.local_db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cityName: String,
    val icon: String = "",
    val description: String = "",
    val temperature: Float = 0f,
    val windSpeed: Float = 0f,
    val humidity: Float = 0f,
    val timestamp: Long = System.currentTimeMillis()
)