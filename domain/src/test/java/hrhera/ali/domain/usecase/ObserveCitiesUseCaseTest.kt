package hrhera.ali.domain.usecase

import app.cash.turbine.test
import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.City
import hrhera.ali.domain.repository.CitiesRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveCitiesUseCaseTest {

    private lateinit var repository: CitiesRepository
    private lateinit var useCase: ObserveCitiesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = ObserveCitiesUseCase(repository)
    }

    @Test
    fun `invoke emits loading then success`() = runTest {
        val citiesList = listOf(City("Cairo",emptyList()), City("Alexandria",emptyList()))
        every { repository.observeCities() } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Success(citiesList))
        }

        useCase().test {
            assert(awaitItem() is ResultSource.Loading)
            val success = awaitItem() as ResultSource.Success
            assertEquals(2, success.data.size)
            assertEquals("Cairo", success.data[0].name)
            assertEquals("Alexandria", success.data[1].name)
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits loading then error`() = runTest {
        every { repository.observeCities() } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Error("Network error"))
        }

        useCase().test {
            assert(awaitItem() is ResultSource.Loading)
            val error = awaitItem() as ResultSource.Error
            assertEquals("Network error", error.message)
            awaitComplete()
        }
    }

    @Test
    fun `invoke only loading with no further emission`() = runTest {
        every { repository.observeCities() } returns flow {
            emit(ResultSource.Loading)
            kotlinx.coroutines.delay(Long.MAX_VALUE)
        }

        useCase().test {
            assert(awaitItem() is ResultSource.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
