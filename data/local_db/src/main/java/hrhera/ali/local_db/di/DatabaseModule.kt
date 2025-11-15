package com.planRadar.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hrhera.ali.core.Constants
import hrhera.ali.local_db.dao.WeatherHistoryDao
import hrhera.ali.local_db.db.WeatherDatabase
import hrhera.ali.local_db.migrations.MIGRATION_1_2
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(
        @ApplicationContext context: Context,
    ): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            Constants.WEATHER_DATABASE
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherHistoryDao(database: WeatherDatabase): WeatherHistoryDao {
        return database.weatherHistoryDao()
    }

}