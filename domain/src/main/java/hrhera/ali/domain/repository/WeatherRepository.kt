package hrhera.ali.domain.repository

import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.Weather
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    suspend fun getWeather(city: String): Flow<ResultSource<List<Weather>>>
    suspend fun getLocalWeather(city: String): Flow<ResultSource<List<Weather>>>

    suspend fun getWeatherHistoryDetails(id: Long): Flow<ResultSource<Weather>>
    suspend fun removeWeatherHistoryDetails(id: Long,city: String): Flow<ResultSource<List<Weather>>>
}