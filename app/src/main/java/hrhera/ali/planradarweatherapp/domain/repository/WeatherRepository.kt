package hrhera.ali.planradarweatherapp.domain.repository

import hrhera.ali.planradarweatherapp.core.Result
import hrhera.ali.planradarweatherapp.domain.models.Weather
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    suspend fun getWeather(city: String): Flow<Result<Weather>>
}