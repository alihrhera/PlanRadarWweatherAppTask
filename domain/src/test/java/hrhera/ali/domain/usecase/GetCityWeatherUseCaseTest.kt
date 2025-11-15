package hrhera.ali.domain.usecase



import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.Weather
import hrhera.ali.domain.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCityWeatherUseCaseTest {

    private val repository = FakeWeatherRepository()
    private val useCase = GetCityWeatherUseCase(repository)

    @Test
    fun `getWeather emits loading then success for valid city`() = runTest {
        val emissions = mutableListOf<ResultSource<List<Weather>>>()
        val job = launch {
            useCase("Cairo").collect { emissions.add(it) }
        }

        advanceUntilIdle()
        job.cancel()

        assert(emissions.size >= 2)
        assert(emissions[0] is ResultSource.Loading)
        val success = emissions[1] as ResultSource.Success
        assertEquals("Sunny", success.data.first().description)
    }

    @Test
    fun `getWeather emits loading then error for invalid city`() = runTest {
        val emissions = mutableListOf<ResultSource<List<Weather>>>()
        val job = launch {
            useCase("Invalid").collect { emissions.add(it) }
        }

        advanceUntilIdle()
        job.cancel()

        assert(emissions.size >= 2)
        assert(emissions[0] is ResultSource.Loading)
        val error = emissions[1] as ResultSource.Error
        assertEquals("City not found", error.message)
    }

    @Test
    fun `getWeather only loading for city with delay but no further emission`() = runTest {
        val repositoryDelayed = object : WeatherRepository {
            override suspend fun getWeather(city: String) = flow {
                emit(ResultSource.Loading)
            }
        }
        val useCaseDelayed = GetCityWeatherUseCase(repositoryDelayed)

        val emissions = mutableListOf<ResultSource<List<Weather>>>()
        val job = launch {
            useCaseDelayed("AnyCity").collect { emissions.add(it) }
        }
        advanceUntilIdle()
        job.cancel()
        assert(emissions.size == 1)
        assert(emissions[0] is ResultSource.Loading)
    }
}

