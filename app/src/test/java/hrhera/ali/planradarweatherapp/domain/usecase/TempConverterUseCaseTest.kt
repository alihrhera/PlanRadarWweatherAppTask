package hrhera.ali.planradarweatherapp.domain.usecase

import org.junit.Test
import org.junit.Assert.assertEquals

class TempConverterUseCaseTest {
    private val useCase = TempConverterUseCase()


    @Test
    fun `toCelsius nominal conversion`() {
        val result = useCase.toCelsius(300.0)
        assertEquals(26.85, result, 0.01)
    }

    @Test
    fun `toKelvin nominal conversion`() {
        val result = useCase.toKelvin(20.0)
        assertEquals(293.15, result, 0.01)
    }


}