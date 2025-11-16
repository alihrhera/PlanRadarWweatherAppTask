package hrhera.ali.local_db.dao
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import hrhera.ali.local_db.db.WeatherDatabase
import hrhera.ali.local_db.entity.WeatherEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class WeatherHistoryDaoTest {

    private lateinit var db: WeatherDatabase
    private lateinit var dao: WeatherHistoryDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = db.weatherHistoryDao()
    }

    @After
    fun shutDown() {
        db.close()
    }

    @Test
    fun insertRecord_shouldSaveCorrectly() = runTest {
        val item = WeatherEntity(
            cityName = "Cairo",
            icon = "",
            description = "",
            temperature = 25.1f,
            windSpeed = 0f,
            humidity =0f,
        )

        dao.insertWeather(item)

        val result = dao.getWeatherHistory("Cairo")

        assertEquals(1, result.size)
        assertEquals(25.1f, result.first().temperature)
    }

    @Test
    fun deleteWeatherHistoryForCity_shouldRemoveAllRecords() = runTest {
        dao.insertWeather(
            WeatherEntity(
                cityName = "Cairo",
                icon = "",
                description = "",
                temperature = 25.3f,
                windSpeed = 0f,
                humidity =0f,
            )
        )
        dao.insertWeather(
            WeatherEntity(
                cityName = "Cairo",
                icon = "",
                description = "",
                temperature = 25.2f,
                windSpeed = 0f,
                humidity =0f,
            )
        )

        dao.deleteWeatherHistoryForCity("Cairo")

        val result = dao.getWeatherHistory("Cairo")
        assertTrue(result.isEmpty())
    }

    @Test
    fun getLatestRecord_shouldReturnNewestOne() = runTest {
        dao.insertWeather(
            WeatherEntity(
                cityName = "Cairo",
                icon = "",
                description = "",
                temperature = 20.1f,
                windSpeed = 0f,
                humidity =0f,
            )
        )
        dao.insertWeather(
            WeatherEntity(
                cityName = "Cairo",
                icon = "",
                description = "",
                temperature = 22.1f,
                windSpeed = 0f,
                humidity =0f,
            )
        )

        val latest = dao.getLatestWeatherForCity("Cairo")
        assertNotNull(latest)
        assertEquals(22.1f, latest!!.temperature)
    }

    @Test
    fun getCities_shouldReturnAllCitiesOrderedByTimestampDesc() = runTest {
        val cairo = WeatherEntity(
            cityName = "Cairo",
            icon = "",
            description = "",
            temperature = 25.0f,
            windSpeed = 1f,
            humidity = 50f,
        )
        Thread.sleep(10)
        val alex = WeatherEntity(
            cityName = "Alex",
            icon = "",
            description = "",
            temperature = 20.0f,
            windSpeed = 2f,
            humidity = 60f,
        )

        dao.insertWeather(cairo)
        dao.insertWeather(alex)
        val result = dao.getCities()
        assertEquals(2, result.size)
        assertEquals("Alex", result[0].cityName)
        assertEquals("Cairo", result[1].cityName)
    }



    @Test
    fun observeCities_shouldEmitAllCitiesOrderedByTimestampDesc() = runTest {
        val cairo = WeatherEntity(
            cityName = "Cairo",
            icon = "",
            description = "",
            temperature = 25.0f,
            windSpeed = 1f,
            humidity = 50f,
        )
        Thread.sleep(10)
        val alex = WeatherEntity(
            cityName = "Alex",
            icon = "",
            description = "",
            temperature = 20.0f,
            windSpeed = 2f,
            humidity = 60f,
        )

        dao.insertWeather(cairo)
        dao.insertWeather(alex)
        val result = dao.observeCities().first()
        assertEquals(2, result.size)
        assertEquals("Alex", result[0].cityName)
        assertEquals("Cairo", result[1].cityName)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun observeCities_shouldEmitUpdatesLive() = runTest {
        val emissions = mutableListOf<List<WeatherEntity>>()

        val job = launch {
            dao.observeCities().collect { emissions.add(it) }
        }

        emissions.firstOrNull() ?: advanceUntilIdle()
        assertEquals(0, emissions.lastOrNull()?.size ?: 0)

        val cairo = WeatherEntity(
            cityName = "Cairo",
            icon = "",
            description = "",
            temperature = 25.0f,
            windSpeed = 1f,
            humidity = 50f,
        )
        dao.insertWeather(cairo)

        val firstEmit = dao.observeCities().first { it.size == 1 }
        assertEquals(1, firstEmit.size)
        assertEquals("Cairo", firstEmit[0].cityName)

        val alex = WeatherEntity(
            cityName = "Alex",
            icon = "",
            description = "",
            temperature = 20.0f,
            windSpeed = 2f,
            humidity = 60f,
        )
        dao.insertWeather(alex)
        val secondEmit = dao.observeCities().first { it.size == 2 }
        assertEquals(2, secondEmit.size)
        assertEquals("Alex", secondEmit[0].cityName)
        assertEquals("Cairo", secondEmit[1].cityName)

        job.cancel()
    }

    @Test
    fun getWeatherHistoryDetails_returnsCorrectEntity() = runTest {
        val weather =WeatherEntity(
            id = 1L,
            cityName = "Alex",
            icon = "",
            description = "",
            temperature = 20.0f,
            windSpeed = 2f,
            humidity = 60f,
        )

        dao.insertWeather(weather)
        val result = dao.getWeatherHistoryDetails(1L)
        assertNotNull(result)
        assertEquals(weather.id, result?.id)
    }

    @Test
    fun getWeatherHistoryDetails_returnsNullWhenNoEntity() = runTest {
        val result = dao.getWeatherHistoryDetails(999L)
        assertNull(result)
    }


}

