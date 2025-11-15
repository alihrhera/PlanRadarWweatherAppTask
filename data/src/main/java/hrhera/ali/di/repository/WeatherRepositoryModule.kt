package hrhera.ali.di.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hrhera.ali.domain.repository.WeatherRepository
import hrhera.ali.repository.WeatherRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherRepositoryModule {
    @Binds
    abstract fun bindWeatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository
}
