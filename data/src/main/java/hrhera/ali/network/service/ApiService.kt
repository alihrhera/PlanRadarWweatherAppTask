package hrhera.ali.network.service

import hrhera.ali.network.model.resposne.WeatherResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/weather")
    suspend fun fetchWeather(
        @Query("q") cityName: String,
    ): WeatherResponseData

}