package hrhera.ali.history

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import org.junit.Rule
import org.junit.Test
import io.mockk.mockk
import io.mockk.coEvery
import kotlinx.coroutines.flow.flow
import hrhera.ali.core.ResultSource
import hrhera.ali.core.utils.capitalizeWords
import hrhera.ali.domain.models.Weather
import hrhera.ali.domain.usecase.GetCityWeatherUseCase
import hrhera.ali.domain.usecase.GetLocalCityWeatherUseCase
import hrhera.ali.domain.usecase.RemoveWeatherHistoryDetailsUseCase

class HistoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeGetLocal = mockk<GetLocalCityWeatherUseCase>()
    private val fakeGetCity = mockk<GetCityWeatherUseCase>()
    private val fakeRemove = mockk<RemoveWeatherHistoryDetailsUseCase>()

    @Test
    fun historyScreen_showsLoading_and_thenHistoryItems_and_navigatesOnItemClick() {
        val city = "cairo".capitalizeWords()
        val fakeHistory = listOf(Weather.EMPTY.copy(cityName = city, id = 123L))

        coEvery { fakeGetLocal(city) } returns flow {
            emit(ResultSource.Loading)
            emit(ResultSource.Success(fakeHistory))
        }

        val fakeViewModel = HistoryViewModel(
            getCityWeatherUseCase = fakeGetCity,
            fetchLocalCityWeatherUseCase = fakeGetLocal,
            removeWeatherHistoryDetailsUseCase = fakeRemove
        )

        var navTargetId: Long? = null

        composeTestRule.setContent {
            MaterialTheme {
                HistoryScreenRoute(
                    viewModel = fakeViewModel,
                    cityName = city,
                    onNavToWeatherDetails = { id ->
                        navTargetId = id
                    }
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onRoot().printToLog("tree")
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText(city, substring = false)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText(city).assertExists()
        composeTestRule.onNodeWithText(city).assertExists()
        composeTestRule.onNodeWithText(city).performClick()
        composeTestRule.waitForIdle()
        assert(navTargetId == 123L) { "Expected navTargetId to be 123L, but it was $navTargetId" }
    }
}
