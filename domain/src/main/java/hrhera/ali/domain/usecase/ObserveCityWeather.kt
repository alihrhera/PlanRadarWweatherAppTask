package hrhera.ali.domain.usecase

import hrhera.ali.domain.repository.CitiesRepository
import javax.inject.Inject

class ObserveCityWeather @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    operator fun invoke() = citiesRepository.observeCities()
}