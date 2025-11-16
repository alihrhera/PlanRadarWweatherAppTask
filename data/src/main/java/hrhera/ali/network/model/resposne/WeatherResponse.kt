package hrhera.ali.network.model.resposne

import androidx.room.util.copy
import com.google.gson.annotations.SerializedName
import hrhera.ali.network.model.beans.CloudsDTO
import hrhera.ali.network.model.beans.CoordDTO
import hrhera.ali.network.model.beans.MainDTO
import hrhera.ali.network.model.beans.SysDTO
import hrhera.ali.network.model.beans.WeatherDTO
import hrhera.ali.network.model.beans.WindDTO

data class WeatherResponseData(
    @SerializedName("id") val id: Int,
    @SerializedName("weather") val weather: List<WeatherDTO>?,
    @SerializedName("name") val name: String?,
    @SerializedName("base") val base: String?,
    @SerializedName("clouds") val clouds: CloudsDTO?,
    @SerializedName("cod") val cod: Int?,
    @SerializedName("message") val message: String? = null,
    @SerializedName("coord") val cooRd: CoordDTO?,
    @SerializedName("dt") val dt: Int?,
    @SerializedName("main") val main: MainDTO?,
    @SerializedName("sys") val sys: SysDTO?,
    @SerializedName("timezone") val timezone: Int?,
    @SerializedName("visibility") val visibility: Int?,
    @SerializedName("wind") val wind: WindDTO?
)
{
    companion object{
        val EMPTY= WeatherResponseData(
            id = 0,
            weather = emptyList(),
            name = "",
            base = "",
            clouds = null,
            cod = 0,
            cooRd = null,
            dt = null,
            main = null,
            sys = null,
            timezone = null,
            visibility = null,
            wind = null
        )
    }
}