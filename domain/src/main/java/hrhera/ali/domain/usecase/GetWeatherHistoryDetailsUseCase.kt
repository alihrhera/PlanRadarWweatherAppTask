package hrhera.ali.domain.usecase

import hrhera.ali.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherHistoryDetailsUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(id: Long) = repository.getWeatherHistoryDetails(id)
}