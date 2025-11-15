package hrhera.ali.mapper

import hrhera.ali.core.utils.formatDateTime
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
    )
}

fun WeatherResponseData.toEntity(cityName: String) = WeatherEntity(
    cityName = cityName,
    icon = weather.firstOrNull()?.icon ?: "",
    description = weather.firstOrNull()?.description ?: "",
    windSpeed = wind.speed.toFloat(),
    temperature = main.temp.toFloat(),
    humidity = main.humidity.toFloat(),
    timestamp = System.currentTimeMillis()
)
