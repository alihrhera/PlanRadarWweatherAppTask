package hrhera.ali.domain.models

data class Weather(
    val description: String,
    val tempCelsius: String,
    val humidity: String,
    val windSpeed: String,
    val cityName: String,
    val dateTime: String,
    val icon: String,
    val time: String = "",
    val date: String = "",
)