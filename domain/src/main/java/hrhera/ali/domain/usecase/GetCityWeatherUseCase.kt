package hrhera.ali.domain.usecase

import hrhera.ali.domain.repository.WeatherRepository

class GetCityWeatherUseCase (private val repository: WeatherRepository){
    suspend operator fun invoke(city: String) = repository.getWeather(city)

}