package hrhera.ali.domain.usecase

import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.City
import hrhera.ali.domain.models.Weather
import hrhera.ali.domain.repository.WeatherRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class FakeWeatherRepository : WeatherRepository {
    override suspend fun getWeather(city: String): Flow<ResultSource<List<Weather>>> =
        flow {
            emit(ResultSource.Loading)
            delay(500)
            if (city == "Invalid") {
                emit(ResultSource.Error("City not found"))
            } else {
                emit(
                    ResultSource.Success(
                        listOf(
                            Weather(
                                description = "Sunny",
                                tempCelsius = "25.0",
                                humidity = "50",
                                windSpeed = "5.0",
                                cityName = "London",
                                dateTime = "2023-11-24 12:00:00",
                                icon = ""
                            )
                        )
                    )
                )
            }
        }

    private val _weatherHistoryFlow = MutableStateFlow<ResultSource<List<Weather>>>(
        ResultSource.Loading
    )

    override suspend fun observeWeatherHistory(): Flow<ResultSource<List<Weather>>> {
        return _weatherHistoryFlow
    }
    suspend fun updateWeather(newWeather: List<Weather>) {
        _weatherHistoryFlow.emit(ResultSource.Success(newWeather))
    }
}