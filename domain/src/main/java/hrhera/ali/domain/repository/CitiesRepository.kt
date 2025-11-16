package hrhera.ali.domain.repository

import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.City
import hrhera.ali.domain.models.Weather
import kotlinx.coroutines.flow.Flow


interface CitiesRepository {
    suspend fun getCities(): Flow<ResultSource<List<City>>>

    suspend fun removeCity(name: String): Flow<ResultSource<List<City>>>


}


