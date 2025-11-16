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
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherHistoryDetailsUseCaseTest {

    private lateinit var repository: WeatherRepository
    private lateinit var useCase: GetWeatherHistoryDetailsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetWeatherHistoryDetailsUseCase(repository)
    }

    @Test
    fun `invoke emits loading then success for valid id`() = runTest {
        val weather = Weather.EMPTY.copy(description = "Sunny")
        coEvery { repository.getWeatherHistoryDetails(1L) } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Success(weather))
        }

        useCase(1L).test {
            assert(awaitItem() is ResultSource.Loading)
            val success = awaitItem() as ResultSource.Success
            assertEquals("Sunny", success.data.description)
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits loading then error for invalid id`() = runTest {
        coEvery { repository.getWeatherHistoryDetails(999L) } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Error("Weather record not found"))
        }

        useCase(999L).test {
            assert(awaitItem() is ResultSource.Loading)
            val error = awaitItem() as ResultSource.Error
            assertEquals("Weather record not found", error.message)
            awaitComplete()
        }
    }

    @Test
    fun `invoke only loading with long delay`() = runTest {
        coEvery { repository.getWeatherHistoryDetails(42L) } returns flow {
            emit(ResultSource.Loading)
            kotlinx.coroutines.delay(Long.MAX_VALUE)
        }

        useCase(42L).test {
            assert(awaitItem() is ResultSource.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
