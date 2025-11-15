package hrhera.ali.repository


import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.Weather
import hrhera.ali.local_db.dao.WeatherHistoryDao
import hrhera.ali.mapper.toEntity
import hrhera.ali.network.model.resposne.WeatherResponseData
import hrhera.ali.network.service.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {

    private lateinit var apiService: ApiService
    private lateinit var weatherHistoryDao: WeatherHistoryDao
    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setup() {
        apiService = mockk()
        weatherHistoryDao = mockk()
        repository = WeatherRepositoryImpl(weatherHistoryDao, apiService)
    }

    @Test
    fun `getWeather should fetch from API and save to DB`() = runTest {
        val city = "Cairo"

        val apiWeather =
            WeatherResponseData(
                id = 1,
                weather = emptyList(),
                base = "",
                clouds = mockk(),
                cod = 0,
                cooRd = mockk(),
                dt = 0,
                main = mockk(),
                name = city,
                sys = mockk(),
                timezone = 0,
                visibility = 0,
                wind = mockk()
            )
        coEvery { apiService.fetchWeather(city) } returns apiWeather

        val entityList = apiWeather.toEntity(city)
        coEvery { weatherHistoryDao.getWeatherHistory(city) } returns listOf(entityList)
        coEvery { weatherHistoryDao.insertWeather(any()) } returns 1

        val resultFlow = repository.getWeather(city)
        resultFlow.collect { result ->
            assertTrue(result is ResultSource.Success)
            val weatherList =( result as ResultSource.Success<List<Weather>>).data.first()
            assertEquals(apiWeather.name, weatherList.cityName)
        }
        coVerify(exactly = 1) { weatherHistoryDao.insertWeather(any()) }
        coVerify(exactly = 1) { weatherHistoryDao.getWeatherHistory(city) }
    }
}
