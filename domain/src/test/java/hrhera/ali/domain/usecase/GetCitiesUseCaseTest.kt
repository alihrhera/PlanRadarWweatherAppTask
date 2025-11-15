package hrhera.ali.domain.usecase


import app.cash.turbine.test
import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.City
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetCitiesUseCaseTest {

    private val fakeRepository = FakeCitiesRepository()
    private val getCitiesUseCase = GetCitiesUseCase(fakeRepository)

    @Test
    fun `invoke returns loading then success with correct cities`() = runTest {
        val city1 = City(name = "Cairo")
        val city2 = City(name = "Alex")
        fakeRepository.addCity(city1)
        fakeRepository.addCity(city2)
        val flow = getCitiesUseCase()
        flow.test {
            assertEquals(ResultSource.Loading, awaitItem())
            val result = awaitItem()
            assert(result is ResultSource.Success)
            val list = (result as ResultSource.Success).data
            assertEquals(2, list.size)
            assertEquals(city1, list[0])
            assertEquals(city2, list[1])
            awaitComplete()
        }
    }
}
