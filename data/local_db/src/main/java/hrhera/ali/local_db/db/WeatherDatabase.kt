package hrhera.ali.local_db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import hrhera.ali.local_db.dao.WeatherHistoryDao
import hrhera.ali.local_db.entity.WeatherEntity



@Database(
    entities = [ WeatherEntity::class],
    version = 2,
    exportSchema = true
)


abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherHistoryDao(): WeatherHistoryDao
}