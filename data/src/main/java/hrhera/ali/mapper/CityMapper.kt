package hrhera.ali.mapper

import hrhera.ali.domain.models.City
import hrhera.ali.local_db.entity.WeatherEntity


fun List<WeatherEntity>.toCities(): List<City> {
    return this
        .groupBy { it.cityName }
        .map { (cityName, weatherList) ->
            City(
                name = cityName,
                history = weatherList.map { it.toWeatherModel() }
            )
        }
}