package hrhera.ali.local_db.dao


import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import hrhera.ali.local_db.db.WeatherDatabase
import hrhera.ali.local_db.migrations.MIGRATION_1_2
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_DB = "migration-test"

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        WeatherDatabase::class.java
    )

    @Test
    fun migrate1To2_addTimestamp() {
        val db = helper.createDatabase(TEST_DB, 1)
        db.execSQL("INSERT INTO weather_data (id, cityName, icon, description, temp," +
                " windSpeed, humidity) VALUES (1, 'Test', '', '', 20, 3, 50)")
        db.close()

        val migrated = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)
        val cursor =
            migrated.query("SELECT id, cityName, temp, timestamp FROM weather_data WHERE id = 1")
        cursor.use {
            assertTrue(it.moveToFirst())
            assertEquals(1L, it.getLong(it.getColumnIndex("id")))
            assertEquals("Test", it.getString(it.getColumnIndex("cityName")))
            assertEquals(20f, it.getFloat(it.getColumnIndex("temp")))
            assertEquals(0L, it.getLong(it.getColumnIndex("timestamp")))
        }
        migrated.close()
    }

    @Test
    fun migrateAll() {
        helper.createDatabase(TEST_DB, 1).close()
        helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)
            .close()
    }
}
