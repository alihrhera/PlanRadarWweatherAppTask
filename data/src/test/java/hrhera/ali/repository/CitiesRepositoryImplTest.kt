package hrhera.ali.repository

import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.City
import hrhera.ali.local_db.dao.WeatherHistoryDao
import hrhera.ali.local_db.entity.WeatherEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.junit.JUnitAsserter.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CitiesRepositoryImplTest {

    private lateinit var weatherLocalDb: WeatherHistoryDao
    private lateinit var repository: CitiesRepositoryImpl

    @Before
    fun setup() {
        weatherLocalDb = mockk()
        repository = CitiesRepositoryImpl(weatherLocalDb)
    }

    @Test
    fun `getCities should return cities wrapped in ResultSource`() = runTest {
        val fakeEntities = listOf(
            WeatherEntity(cityName = "Cairo", temperature = 30.0f)
        )
        coEvery { weatherLocalDb.getCities() } returns fakeEntities
        val flow = repository.getCities()
        val emissions = flow.take(2).toList()
        assertTrue(emissions[0] is ResultSource.Loading)
        coVerify(exactly = 1) { weatherLocalDb.getCities() }
    }

    @Test
    fun `observeCities should emit Loading then Success`() = runTest {
        val fakeEntities = listOf(
            WeatherEntity(cityName = "Cairo", temperature = 30.0f)
        )

        coEvery { weatherLocalDb.observeCities() } returns flowOf(fakeEntities)
        val emissions = mutableListOf<ResultSource<List<City>>>()
        repository.observeCities().collect { emissions.add(it) }
        assertTrue(emissions[0] is ResultSource.Loading)
        println("${emissions[1]} ${fakeEntities.first().timestamp}")
        val success = emissions[1] as ResultSource.Success
        assertEquals("Cairo", success.data.first().name)
        verify(exactly = 1) { weatherLocalDb.observeCities() }
    }

    @Test
    fun `observeCities should emit Error on exception`() = runTest {
        val exception = RuntimeException("DB error")
        coEvery { weatherLocalDb.observeCities() } returns flow { throw exception }

        val emissions = mutableListOf<ResultSource<List<City>>>()
        repository.observeCities().collect { emissions.add(it) }

        assertTrue(emissions[0] is ResultSource.Loading)
        val error = emissions[1] as ResultSource.Error
        assertEquals("DB error", error.message)
    }
}
