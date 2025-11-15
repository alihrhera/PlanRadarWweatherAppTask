package hrhera.ali.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hrhera.ali.network.service.ApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideWeatherApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}