package hrhera.ali.cities.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hrhera.ali.cities.CitiesScreen


const val CITY_ROUT_NAME = "/cities"
fun NavGraphBuilder.cityScreenRoute(
    onShowHistory: (String) -> Unit,
    onGotoDetails: (String, Long) -> Unit
) {
    composable(CITY_ROUT_NAME) {
        CitiesScreen(
            onMoveToHistory = onShowHistory,
            onMoveToDetails = onGotoDetails
        )
    }
}