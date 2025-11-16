package hrhera.ali.repository

import hrhera.ali.core.ResultSource
import hrhera.ali.local_db.dao.WeatherHistoryDao
import hrhera.ali.local_db.entity.WeatherEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

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



}
