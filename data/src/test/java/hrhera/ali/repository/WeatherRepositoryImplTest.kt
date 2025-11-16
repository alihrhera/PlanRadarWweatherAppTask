package hrhera.ali.repository


import hrhera.ali.core.ResultSource
import hrhera.ali.local_db.dao.WeatherHistoryDao
import hrhera.ali.local_db.entity.WeatherEntity
import hrhera.ali.mapper.toEntity
import hrhera.ali.network.model.resposne.WeatherResponseData
import hrhera.ali.network.service.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

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
        val city = "cairo"
        val apiWeather = WeatherResponseData.EMPTY.copy(id = 1, name = city)
        coEvery { apiService.fetchWeather(city) } returns apiWeather

        val entityList = apiWeather.toEntity(city)
        coEvery { weatherHistoryDao.getWeatherHistory(city) } returns listOf(entityList)
        coEvery { weatherHistoryDao.insertWeather(any()) } returns 1

        val resultFlow = repository.getWeather(city)

        resultFlow.collect { result ->
            when (result) {
                is ResultSource.Loading -> {
                    println("Loading...")
                }

                is ResultSource.Success -> {
                    val weatherList = result.data.first()
                    assertEquals(apiWeather.name, weatherList.cityName)
                }

                is ResultSource.Error -> {
                    fail("Expected Success, but got Error: ${result.message}")
                }
            }
        }

        coVerify(exactly = 1) { weatherHistoryDao.insertWeather(any()) }
        coVerify(exactly = 1) { weatherHistoryDao.getWeatherHistory(city) }
    }


    @Test
    fun `getLocalWeather should fetch from DB`() = runTest {
        val city = "cairo"
        val entityList = listOf(
            WeatherEntity(
                cityName = city,
                temperature = 25f,
                windSpeed = 5f,
                humidity = 60f
            )
        )
        coEvery { weatherHistoryDao.getWeatherHistory(city) } returns entityList

        val resultFlow = repository.getLocalWeather(city)
        resultFlow.collect { result ->
            if (result is ResultSource.Success) {
                val weatherList = result.data.first()
                assertEquals(city, weatherList.cityName)
            }

        }
        coVerify(exactly = 1) { weatherHistoryDao.getWeatherHistory(city) }
    }

    @Test
    fun `getWeatherHistoryDetails should throw Exception when item not found`() = runTest {
        val id = 1L
        coEvery { weatherHistoryDao.getWeatherHistoryDetails(id) } returns null
        repository.getWeatherHistoryDetails(id).collect {
            if (it is ResultSource.Error) {
                assertEquals("Item Not found", it.message)
            }
        }
        coVerify(exactly = 1) { weatherHistoryDao.getWeatherHistoryDetails(id) }
    }

    @Test
    fun `removeWeatherHistoryDetails should remove item and return updated list`() = runTest {
        val city = "cairo"
        val id = 1L

        val entityList =
            listOf(WeatherEntity(cityName = city, temperature = 25f, windSpeed = 5f, humidity = 60f))
        coEvery { weatherHistoryDao.getWeatherHistory(city) } returns entityList
        coEvery { weatherHistoryDao.deleteWeatherHistory(id) } returns Unit

        val resultFlow = repository.removeWeatherHistoryDetails(id, city)
        resultFlow.collect { result ->
            if (result is ResultSource.Success) {
                val updatedWeatherList = result.data
                assertTrue(updatedWeatherList.isNotEmpty())
            }
        }
        coVerify(exactly = 1) { weatherHistoryDao.deleteWeatherHistory(id) }
        coVerify(exactly = 1) { weatherHistoryDao.getWeatherHistory(city) }
    }


}
