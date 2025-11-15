package hrhera.ali.domain.usecase

import hrhera.ali.domain.repository.CitiesRepository
import javax.inject.Inject

class ObserveCitiesUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    operator fun invoke() = citiesRepository.observeCities()
}