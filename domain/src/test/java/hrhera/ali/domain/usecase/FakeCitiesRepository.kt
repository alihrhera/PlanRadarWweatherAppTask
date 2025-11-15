package hrhera.ali.domain.usecase

import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.City
import hrhera.ali.domain.repository.CitiesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCitiesRepository : CitiesRepository {
    override suspend fun getCities() = flow {
        emit(ResultSource.Loading)
        delay(500)
        emit(
            ResultSource.Success(
                cities.toList()
            )
        )
    }

    override suspend fun observeCities(): Flow<ResultSource<List<City>>> {

    }

    private val cities = mutableSetOf<City>()
    fun addCity(city: City) {
        cities.add(city)
    }
}