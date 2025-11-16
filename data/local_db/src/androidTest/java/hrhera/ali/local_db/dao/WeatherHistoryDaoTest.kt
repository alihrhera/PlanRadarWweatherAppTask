package hrhera.ali.local_db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import hrhera.ali.local_db.db.WeatherDatabase
import hrhera.ali.local_db.entity.WeatherEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


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
    fun deleteWeatherHistory_shouldRemoveRecord() = runTest {
        val weather = WeatherEntity(
            cityName = "Cairo",
            icon = "",
            description = "",
            temperature = 25.0f,
            windSpeed = 1f,
            humidity = 50f
        )
        val id = dao.insertWeather(weather)

        dao.deleteWeatherHistory(id)

        val result = dao.getWeatherHistoryDetails(id)
        assertNull(result)
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

