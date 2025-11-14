package hrhera.ali.planradarweatherapp.domain.usecase

import hrhera.ali.planradarweatherapp.domain.repository.WeatherRepository

class GetCityWeatherUseCase (private val repository: WeatherRepository){
    suspend operator fun invoke(city: String) = repository.getWeather(city)

}