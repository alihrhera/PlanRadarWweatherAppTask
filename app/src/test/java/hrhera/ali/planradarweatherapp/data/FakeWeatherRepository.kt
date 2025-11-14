package hrhera.ali.planradarweatherapp.data

import hrhera.ali.planradarweatherapp.domain.models.Weather
import hrhera.ali.planradarweatherapp.core.Result
import hrhera.ali.planradarweatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

class FakeWeatherRepository : WeatherRepository {
    override suspend fun getWeather(city: String): Flow<Result<Weather>> = flow {
        emit(Result.Loading)
        delay(500)
        if (city == "Invalid") {
            emit(Result.Error("City not found"))
        } else {
            emit(
                Result.Success(
                    Weather(
                        description = "Sunny",
                        tempCelsius = 25.0,
                        humidity = 50,
                        windSpeed = 5.0
                    )
                )
            )
        }
    }
}
