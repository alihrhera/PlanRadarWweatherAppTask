package hrhera.ali.domain.usecase


import app.cash.turbine.test
import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.Weather
import hrhera.ali.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCityWeatherUseCaseTest {

    private lateinit var repository: WeatherRepository
    private lateinit var useCase: GetCityWeatherUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetCityWeatherUseCase(repository)
    }

    @Test
    fun `getWeather emits loading then success for valid city`() = runTest {
        val weatherList = listOf(Weather.EMPTY.copy(description = "Sunny"))
        coEvery { repository.getWeather("Cairo") } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Success(weatherList))
        }

        useCase("Cairo").test {
            assert(awaitItem() is ResultSource.Loading)
            val success = awaitItem() as ResultSource.Success
            assertEquals("Sunny", success.data.first().description)
            awaitComplete()
        }
    }


    @Test
    fun `getWeather emits loading then error for invalid city`() = runTest {
        coEvery { repository.getWeather("Invalid") } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Error("City not found"))
        }

        useCase("Invalid").test {
            assert(awaitItem() is ResultSource.Loading)
            val error = awaitItem() as ResultSource.Error
            assertEquals("City not found", error.message)
            awaitComplete()
        }
    }

    @Test
    fun `getWeather only loading for city with delay but no further emission`() = runTest {
        coEvery { repository.getWeather("AnyCity") } returns flow {
            emit(ResultSource.Loading)
            kotlinx.coroutines.delay(Long.MAX_VALUE)
        }

        useCase("AnyCity").test {
            assert(awaitItem() is ResultSource.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

}

