package hrhera.ali.core

import hrhera.ali.core.utils.humidityFormat
import hrhera.ali.core.utils.toCelsiusFormat
import hrhera.ali.core.utils.windSpeedFormat
import kotlin.test.Test
import kotlin.test.assertEquals

class WeatherFormatExtensionsTest {

    @Test
    fun `toCelsiusFormat should convert Kelvin to Celsius and format correctly`() {
        val kelvin = 300.0
        val result = kelvin.toCelsiusFormat()
        assertEquals("26.9°C", result)
    }

    @Test
    fun `toCelsiusFormat should handle 0 Kelvin correctly`() {
        val kelvin = 0.0
        val result = kelvin.toCelsiusFormat()
        assertEquals("-273.2°C", result)
    }

    @Test
    fun `humidityFormat should format float with one decimal and percent sign`() {
        val humidity = 55.678f
        val result = humidity.humidityFormat()
        assertEquals("55.7%", result)
    }

    @Test
    fun `humidityFormat should format whole number humidity correctly`() {
        val humidity = 80f
        val result = humidity.humidityFormat()
        assertEquals("80.0%", result)
    }

    @Test
    fun `windSpeedFormat should format wind speed with kmh`() {
        val windSpeed = 15.234
        val result = windSpeed.windSpeedFormat()
        assertEquals("15.2 km/h", result)
    }

    @Test
    fun `windSpeedFormat should format whole number wind speed correctly`() {
        val windSpeed = 10.0
        val result = windSpeed.windSpeedFormat()
        assertEquals("10.0 km/h", result)
    }

}
