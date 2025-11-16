package hrhera.ali.planradarweatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import hrhera.ali.cities.navigation.CITY_ROUT_NAME
import hrhera.ali.cities.navigation.cityScreenRoute
import hrhera.ali.history.navigation.HISTORY_ROUTE_NAME
import hrhera.ali.history.navigation.historyRoute

@Composable
fun WeatherNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = CITY_ROUT_NAME
    ) {
        cityScreenRoute { name, detailsId ->
            navController.navigate("$HISTORY_ROUTE_NAME/$name/$detailsId")
        }
        historyRoute { detailsId ->
            navController.navigate("/details/$detailsId")
        }
    }
}