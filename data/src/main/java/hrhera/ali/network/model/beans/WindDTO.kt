package hrhera.ali.network.model.beans

import com.google.gson.annotations.SerializedName

data class WindDTO(
    val deg: Int,
    val gust: Double,
    @SerializedName("speed")
    val windSpeed: Double
)