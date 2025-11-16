package hrhera.ali.domain.usecase

import hrhera.ali.domain.repository.WeatherRepository
import javax.inject.Inject

class RemoveWeatherHistoryDetailsUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(id: Long,city:String) = repository.removeWeatherHistoryDetails(id,city)
}