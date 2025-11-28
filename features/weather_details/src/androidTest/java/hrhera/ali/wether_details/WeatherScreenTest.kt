package hrhera.ali.wether_details

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import hrhera.ali.wether_details.components.WeatherScreen
import org.junit.Rule
import org.junit.Test

class WeatherScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun weatherScreen_displaysAllUiElements() {
        val testState = DetailsUiState(
            name = "Cairo",
            description = "Sunny",
            time = "12:00 PM",
            date = "2025-11-28",
            temp = "25°C",
            icon = "01d",
            humidity = "50%",
            windSpeed = "10 km/h",
            isLoading = false,
            errorEntity = null
        )
        composeTestRule.setContent {
            MaterialTheme {
                WeatherScreen(uiState = testState)
            }
        }
        composeTestRule.onNodeWithText("Cairo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sunny").assertIsDisplayed()
        composeTestRule.onNodeWithText("25°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("50%").assertIsDisplayed()
        composeTestRule.onNodeWithText("10 km/h").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.weather_icon)
        )
            .assertIsDisplayed()
    }

    @Test
    fun weatherScreen_displaysError() {
        val state = DetailsUiState(
            name = "",
            description = "",
            time = "",
            date = "",
            temp = "",
            icon = "",
            humidity = "",
            windSpeed = "",
            isLoading = false,
            errorEntity = "Network error"
        )

        composeTestRule.setContent {
            MaterialTheme {
                WeatherScreen(uiState = state)
            }
        }
        composeTestRule.onNodeWithText("Network error").assertIsDisplayed()
    }


}
