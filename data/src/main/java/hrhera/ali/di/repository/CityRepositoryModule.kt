package hrhera.ali.di.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hrhera.ali.domain.repository.CitiesRepository
import hrhera.ali.repository.CitiesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class CityRepositoryModule {
    @Binds
    abstract fun bindCitiesRepository(
        impl: CitiesRepositoryImpl
    ): CitiesRepository
}