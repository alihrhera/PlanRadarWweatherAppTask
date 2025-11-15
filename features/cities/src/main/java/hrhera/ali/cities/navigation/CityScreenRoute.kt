package hrhera.ali.cities.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hrhera.ali.cities.CitiesScreen

fun NavGraphBuilder.cityScreenRoute(
    onShowHistory: (String) -> Unit
) {
    composable("/cities") {
        CitiesScreen {
            onShowHistory(it)
        }
    }
}