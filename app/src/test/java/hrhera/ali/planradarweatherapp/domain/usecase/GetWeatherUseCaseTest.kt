package hrhera.ali.planradarweatherapp.domain.usecase


import hrhera.ali.planradarweatherapp.core.Result
import hrhera.ali.planradarweatherapp.data.FakeWeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherUseCaseTest {

    private val repository = FakeWeatherRepository()
    private val useCase = GetWeatherUseCase(repository)

    @Test
    fun `getWeather emits loading then success for valid city`() = runTest {
        val emissions = mutableListOf<Result<Weather>>()
        val job = launch {
            useCase("Cairo").collect { emissions.add(it) }
        }

        // تأكّد إنك تسمح بالـ emissions تنفّذ
        advanceUntilIdle()

        job.cancel()

        // التأكّديات
        assert(emissions[0] is Result.Loading)
        val success = emissions[1] as Result.Success
        assertEquals("Sunny", success.data.description)
    }

    @Test
    fun `getWeather emits loading then error for invalid city`() = runTest {
        val emissions = mutableListOf<Result<Weather>>()
        val job = launch {
            useCase("Invalid").collect { emissions.add(it) }
        }

        advanceUntilIdle()

        job.cancel()

        assert(emissions[0] is Result.Loading)
        val error = emissions[1] as Result.Error
        assertEquals("City not found", error.message)
    }

}