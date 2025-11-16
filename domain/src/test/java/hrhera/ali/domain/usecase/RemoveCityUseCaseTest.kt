package hrhera.ali.domain.usecase

import app.cash.turbine.test
import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.City
import hrhera.ali.domain.repository.CitiesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class RemoveCityUseCaseTest {

    private lateinit var repository: CitiesRepository
    private lateinit var useCase: RemoveCityUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = RemoveCityUseCase(repository)
    }

    @Test
    fun `invoke emits loading then success when repository succeeds`() = runTest {
        val cityName = "Cairo"
        val city = City(name = "Alex",emptyList())
        val updatedCities = listOf(city)

        coEvery { repository.removeCity(cityName) } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Success(updatedCities))
        }

        useCase(cityName).test {
            assert(awaitItem() is ResultSource.Loading)
            val success = awaitItem() as ResultSource.Success
            assertEquals("Alex", success.data.first().name)

            awaitComplete()
        }
    }

    @Test
    fun `invoke emits loading then error when repository fails`() = runTest {
        val cityName = "Cairo"

        coEvery { repository.removeCity(cityName) } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Error("Could not remove city"))
        }

        useCase(cityName).test {
            assert(awaitItem() is ResultSource.Loading)

            val error = awaitItem() as ResultSource.Error
            assertEquals("Could not remove city", error.message)

            awaitComplete()
        }
    }

    @Test
    fun `invoke emits only loading when repository delays indefinitely`() = runTest {
        val cityName = "Cairo"

        coEvery { repository.removeCity(cityName) } returns flow {
            emit(ResultSource.Loading)
            delay(Long.MAX_VALUE)
        }
        useCase(cityName).test {
            assert(awaitItem() is ResultSource.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
