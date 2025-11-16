package hrhera.ali.domain.usecase

import hrhera.ali.domain.repository.CitiesRepository
import javax.inject.Inject

class RemoveCityUseCase @Inject constructor(private val repository: CitiesRepository) {
    suspend operator fun invoke(city:String) = repository.removeCity(city)
}