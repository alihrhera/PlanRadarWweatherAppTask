package hrhera.ali.repository

import hrhera.ali.core.ResultSource
import hrhera.ali.core.buildTask
import hrhera.ali.domain.models.Weather
import hrhera.ali.domain.repository.WeatherRepository
import hrhera.ali.local_db.dao.WeatherHistoryDao
import hrhera.ali.mapper.toEntity
import hrhera.ali.mapper.toWeatherModel
import hrhera.ali.network.service.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val weatherHistoryDao: WeatherHistoryDao,
    private val apiService: ApiService
) : WeatherRepository {
    override suspend fun getWeather(city: String): Flow<ResultSource<List<Weather>>> =
        buildTask {
            fetchOnlineWeatherData(city)
            weatherHistoryDao.getWeatherHistory(cityName = city)
                .map { it.toWeatherModel() }
        }

    private suspend fun fetchOnlineWeatherData(city: String) {
        val weatherData = apiService.fetchWeather(city)
        weatherHistoryDao.insertWeather(
            weatherData.toEntity(city)
        )
    }

    override suspend fun getLocalWeather(city: String): Flow<ResultSource<List<Weather>>> =
        buildTask {
            weatherHistoryDao.getWeatherHistory(cityName = city)
                .map { it.toWeatherModel() }
        }

    override suspend fun getWeatherHistoryDetails(id: Long):
            Flow<ResultSource<Weather>> = buildTask {
        weatherHistoryDao.getWeatherHistoryDetails(id)?.toWeatherModel()
            ?: throw Exception("Item Not found")
    }
}