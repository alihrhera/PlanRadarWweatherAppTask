package hrhera.ali.wether_details

import hrhera.ali.core.UiState
import hrhera.ali.domain.models.Weather

data class DetailsUiState(
    val name: String = "",
    val description: String = "---",
    val time: String = "---",
    val date: String = "---",
    val temp: String = "---",
    val icon: String = "---",
    val humidity: String = "---",
    val windSpeed: String = "---",
    val isLoading: Boolean = false,
    val errorEntity: String? = null,
) : UiState {
    fun fromWeather(weather: Weather) = copy(
        name = weather.cityName,
        description = weather.description,
        time = weather.dateTime,
        temp = weather.tempCelsius,
        icon = weather.icon,
        humidity = weather.humidity,
        windSpeed = weather.windSpeed,
        date = weather.date
    )
}