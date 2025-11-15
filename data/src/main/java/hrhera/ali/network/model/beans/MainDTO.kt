package hrhera.ali.network.model.beans

import com.google.gson.annotations.SerializedName

data class MainDTO(
    val feelsLike: Double,
    @SerializedName("grndLevel")
    val grandLevel: Int,
    val humidity: Int,
    val pressure: Int,
    val seaLevel: Int,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double
)