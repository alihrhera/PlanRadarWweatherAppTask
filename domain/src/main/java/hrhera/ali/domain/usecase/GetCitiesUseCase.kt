package hrhera.ali.domain.usecase

import hrhera.ali.domain.repository.CitiesRepository

class GetCitiesUseCase(
    private val citiesRepository: CitiesRepository
) {

    suspend operator fun invoke() = citiesRepository.getCities()

}