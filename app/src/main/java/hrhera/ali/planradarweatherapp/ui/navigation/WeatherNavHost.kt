package hrhera.ali.planradarweatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import hrhera.ali.cities.navigation.cityScreenRoute
import hrhera.ali.history.navigation.historyRoute

@Composable
fun WeatherNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "/cities"
    ) {
        cityScreenRoute {
            navController.navigate("/history/$it")
        }
        historyRoute(navController)
    }
}