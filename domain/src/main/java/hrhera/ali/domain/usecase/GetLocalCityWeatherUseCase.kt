package hrhera.ali.domain.usecase

import hrhera.ali.domain.repository.WeatherRepository
import javax.inject.Inject

class GetLocalCityWeatherUseCase @Inject constructor(private val repository: WeatherRepository){
    suspend operator fun invoke(city: String) = repository.getLocalWeather(city)
}