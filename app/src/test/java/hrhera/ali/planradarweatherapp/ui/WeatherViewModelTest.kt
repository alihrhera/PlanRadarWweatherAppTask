//package hrhera.ali.planradarweatherapp.ui
//
//import hrhera.ali.planradarweatherapp.data.FakeWeatherRepository
//import hrhera.ali.planradarweatherapp.domain.usecase.GetCityWeatherUseCase
//import hrhera.ali.planradarweatherapp.ui.cities.CityUiStat
//import hrhera.ali.planradarweatherapp.ui.cities.CityWeatherViewModel
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.assertTrue
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.test.*
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import kotlin.test.assertEquals
//import kotlin.test.assertFalse
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class WeatherViewModelTest {
//
//    private val testDispatcher = StandardTestDispatcher()
//    private lateinit var viewModel: CityWeatherViewModel
//    private val fakeRepository = FakeWeatherRepository()
//    private val useCase = GetCityWeatherUseCase(fakeRepository)
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(testDispatcher)
//        viewModel = CityWeatherViewModel(useCase)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `fetch sets loading then success state for valid city`() = runTest {
//        val states = mutableListOf<CityUiStat>()
//        val job = launch { viewModel.state.collect { states.add(it) } }
//
//        viewModel.fetch("Cairo")
//        advanceUntilIdle()
//
//        job.cancel()
//        assertFalse(states[0].isLoading)
//        assertTrue(states[1].isLoading)
//        val successState = states[2]
//        assertEquals(false, successState.isLoading)
//        assertEquals("Sunny", successState.weather.description)
//        assertEquals("", successState.error)
//    }
//
//    @Test
//    fun `fetch sets loading then error state for invalid city`() = runTest {
//        val states = mutableListOf<CityUiStat>()
//        val job = launch { viewModel.state.collect { states.add(it) } }
//        viewModel.fetch("Invalid")
//        advanceUntilIdle()
//        job.cancel()
//        assertFalse(states[0].isLoading)
//        assertTrue(states[1].isLoading)
//        val errorState = states[2]
//        assertEquals(false, errorState.isLoading)
//        assertEquals("City not found", errorState.error)
//    }
//
//    @Test
//    fun `initial state is default CityUiStat`() = runTest {
//        val initial = viewModel.state.value
//        assertEquals(false, initial.isLoading)
//        assertEquals("", initial.error)
//    }
//}
//
//
