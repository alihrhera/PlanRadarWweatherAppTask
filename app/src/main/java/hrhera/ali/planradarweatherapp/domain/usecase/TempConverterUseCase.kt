package hrhera.ali.planradarweatherapp.domain.usecase

class TempConverterUseCase {

    fun toCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }

    fun toKelvin(celsius: Double): Double {
        return celsius + 273.15
    }

}