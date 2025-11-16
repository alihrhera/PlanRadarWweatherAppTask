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
class RemoveCityDetailsUseCaseTest {

    private lateinit var repository: WeatherRepository
    private lateinit var useCase: RemoveWeatherHistoryDetailsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = RemoveWeatherHistoryDetailsUseCase(repository)
    }


    @Test
    fun `invoke emits success result when repository succeeds`() = runTest {
        val id = 42L
        val name = "cairo"
        val weather = Weather.EMPTY.copy(description = "Sunny")

        coEvery { repository.removeWeatherHistoryDetails(id,name) } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Success(listOf(weather)))
        }

        useCase(id,name).test {
            assert(awaitItem() is ResultSource.Loading)
            val success = awaitItem() as ResultSource.Success
            assertEquals("Sunny", success.data.first().description)
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits error result when repository fails`() = runTest {
        val id = 42L
        val name = "cairo"
        coEvery { repository.removeWeatherHistoryDetails(id,name) } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Error("Could not remove history"))
        }

        useCase(id,name).test {
            assert(awaitItem() is ResultSource.Loading)
            val error = awaitItem() as ResultSource.Error
            assertEquals("Could not remove history", error.message)
            awaitComplete()
        }
    }

    @Test
    fun `invoke only emits loading when repository delays indefinitely`() = runTest {
        val id = 42L
        val name = "cairo"

        coEvery { repository.removeWeatherHistoryDetails(id,name) } returns flow {
            emit(ResultSource.Loading)
            kotlinx.coroutines.delay(Long.MAX_VALUE)
        }

        useCase(id,name).test {
            assert(awaitItem() is ResultSource.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }
}



