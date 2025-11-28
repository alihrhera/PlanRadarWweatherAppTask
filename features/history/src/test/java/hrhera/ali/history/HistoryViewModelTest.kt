package hrhera.ali.history

import app.cash.turbine.test
import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.Weather
import hrhera.ali.domain.usecase.GetCityWeatherUseCase
import hrhera.ali.domain.usecase.GetLocalCityWeatherUseCase
import hrhera.ali.domain.usecase.RemoveWeatherHistoryDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    private lateinit var viewModel: HistoryViewModel

    private val getCityWeatherUseCase: GetCityWeatherUseCase = mockk()
    private val getLocalCityWeatherUseCase: GetLocalCityWeatherUseCase = mockk()
    private val removeWeatherHistoryDetailsUseCase: RemoveWeatherHistoryDetailsUseCase = mockk()

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        viewModel = HistoryViewModel(
            getCityWeatherUseCase,
            getLocalCityWeatherUseCase,
            removeWeatherHistoryDetailsUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `OnFetchWeather updates state with history data`() = runTest(dispatcher) {

        val weatherList = listOf(Weather.EMPTY.copy(cityName = "cairo"))
        coEvery { getLocalCityWeatherUseCase("cairo") } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Success(weatherList))
        }
        viewModel.emitAction(HistoryActions.OnFetchWeather("cairo"))
        dispatcher.scheduler.advanceUntilIdle()
        viewModel.uiState.test {
            awaitItem()
            val isLoading = awaitItem()
            assertEquals(true, isLoading.isLoading)
            val latest = awaitItem()
            assertEquals(weatherList, latest.history)
        }
    }

    @Test
    fun `OnDeleteItem updates state after removal`() = runTest(dispatcher) {

        val updatedHistory = listOf(Weather.EMPTY.copy(cityName = "cairo"))

        coEvery { removeWeatherHistoryDetailsUseCase(5, any())  } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Success(updatedHistory))
        }
        viewModel.emitAction(HistoryActions.OnDeleteItem(5))
        dispatcher.scheduler.advanceUntilIdle()
        viewModel.uiState.test {
            awaitItem()
            val latest = awaitItem()
            assertEquals(updatedHistory, latest.history)
        }
    }

    @Test
    fun `OnMoveToDetails changes detailsId in state`() = runTest(dispatcher) {
        viewModel.emitAction(HistoryActions.OnMoveToDetails(99))
        viewModel.uiState.test {
            val newState = awaitItem()
            assertEquals(99, newState.detailsId)
        }
    }
}
