package hrhera.ali.repository

import hrhera.ali.core.ResultSource
import hrhera.ali.core.buildTask
import hrhera.ali.domain.models.City
import hrhera.ali.domain.repository.CitiesRepository
import hrhera.ali.local_db.dao.WeatherHistoryDao
import hrhera.ali.mapper.toCities
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CitiesRepositoryImpl @Inject constructor(
    private val weatherLocalDb: WeatherHistoryDao
) : CitiesRepository {
    override suspend fun getCities(): Flow<ResultSource<List<City>>> = buildTask {
        weatherLocalDb.getCities().toCities()
    }
    override suspend fun removeCity(name: String): Flow<ResultSource<List<City>>> = buildTask {
        weatherLocalDb.deleteWeatherHistoryForCity(name)
        weatherLocalDb.getCities().toCities()
    }

}