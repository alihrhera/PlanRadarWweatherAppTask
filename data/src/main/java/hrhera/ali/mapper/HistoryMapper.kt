package hrhera.ali.mapper

import hrhera.ali.core.utils.formatDate
import hrhera.ali.core.utils.formatDateTime
import hrhera.ali.core.utils.formatTime
import hrhera.ali.core.utils.humidityFormat
import hrhera.ali.core.utils.windSpeedFormat
import hrhera.ali.core.utils.toCelsiusFormat
import hrhera.ali.domain.models.Weather
import hrhera.ali.local_db.entity.WeatherEntity
import hrhera.ali.network.model.resposne.WeatherResponseData


fun WeatherEntity.toWeatherModel(): Weather {
    return Weather(
        cityName = cityName,
        icon = icon,
        description = description,
        windSpeed = windSpeed.windSpeedFormat(),
        tempCelsius = temperature.toCelsiusFormat(),
        humidity = humidity.humidityFormat(),
        dateTime = timestamp.formatDateTime(),
        date = timestamp.formatDate(),
        time = timestamp.formatTime(),
        id = id
    )
}

fun WeatherResponseData.toEntity(cityName: String) = WeatherEntity(
    cityName = cityName.lowercase(),
    icon = weather?.firstOrNull()?.icon ?: "",
    description = weather?.firstOrNull()?.description ?: "",
    windSpeed = wind?.windSpeed?.toFloat()?:0f,
    temperature = main?.temp?.toFloat()?:0f,
    humidity = main?.humidity?.toFloat()?:0f,
    timestamp = System.currentTimeMillis()
)
