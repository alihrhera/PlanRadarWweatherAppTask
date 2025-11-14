package hrhera.ali.planradarweatherapp.domain.models

data class Weather(
    val description: String,
    val tempCelsius: Double,
    val humidity: Int,
    val windSpeed: Double
)
