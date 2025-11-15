package hrhera.ali.domain.repository

import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.City
import hrhera.ali.domain.models.Weather
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    suspend fun getWeather(city: String): Flow<ResultSource<List<Weather>>>
}