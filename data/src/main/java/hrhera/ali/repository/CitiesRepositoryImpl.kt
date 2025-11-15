package hrhera.ali.repository

import hrhera.ali.core.ResultSource
import hrhera.ali.core.buildTask
import hrhera.ali.core.defaultDispatcher
import hrhera.ali.core.errorHandling
import hrhera.ali.domain.models.City
import hrhera.ali.domain.repository.CitiesRepository
import hrhera.ali.local_db.dao.WeatherHistoryDao
import hrhera.ali.local_db.entity.WeatherEntity
import hrhera.ali.mapper.toCities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class CitiesRepositoryImpl @Inject constructor(
    private val weatherLocalDb: WeatherHistoryDao
) : CitiesRepository {
    override suspend fun getCities(): Flow<ResultSource<List<City>>> = buildTask {
        weatherLocalDb.getCities().toCities()
    }

    override fun observeCities(): Flow<ResultSource<List<City>>> {
        return weatherLocalDb.observeCities()
            .map<List<WeatherEntity>, ResultSource<List<City>>> { entities ->
                ResultSource.Success(entities.toCities())
            }
            .onStart {
                emit(ResultSource.Loading)
            }
            .catch { e ->
                emit(ResultSource.Error(message = errorHandling(e)))
            }
            .flowOn(defaultDispatcher)
    }


}