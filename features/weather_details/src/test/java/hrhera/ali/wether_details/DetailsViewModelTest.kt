package hrhera.ali.wether_details

import app.cash.turbine.test
import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.Weather
import hrhera.ali.domain.usecase.GetWeatherHistoryDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel
    private val getWeatherUseCase: GetWeatherHistoryDetailsUseCase = mockk()

    private lateinit var testDispatcher : TestDispatcher

    @Before
    fun setup() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailsViewModel(getWeatherUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getWeatherDetails returns Loading then state should be loading`() = runTest {
        coEvery { getWeatherUseCase(1) } returns flow {
            emit(ResultSource.Loading)
        }
        viewModel.getWeatherDetails(1)
        viewModel.uiState.test {
            skipItems(1)
            val item = awaitItem()
            assert(item.isLoading)
        }
    }

    @Test
    fun `when getWeatherDetails returns Success then uiState should update with data`() =
        runTest {
            val fakeWeather = Weather.EMPTY.copy(id = 1, tempCelsius = "20")

            coEvery { getWeatherUseCase(1) } returns flow {
                emit(ResultSource.Loading)
                emit(ResultSource.Success(fakeWeather))
            }

            viewModel.getWeatherDetails(1)

            viewModel.uiState.test {
                skipItems(2)
                val item = awaitItem()
                assert(!item.isLoading)
                assert(item.temp!="---")
                assert(item.temp == "20")
            }
        }

    @Test
    fun `when getWeatherDetails returns Error then uiState contains error`() =
        runTest {
            coEvery { getWeatherUseCase(1) } returns flow {
                emit(ResultSource.Loading)
                emit(ResultSource.Error(message = "Network error"))
            }

            viewModel.getWeatherDetails(1)

            viewModel.uiState.test {
                skipItems(2)
                val item = awaitItem()
                assert(item.errorEntity == "Network error")
            }
        }
}
