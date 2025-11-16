package hrhera.ali.planradarweatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import hrhera.ali.cities.navigation.CITY_ROUT_NAME
import hrhera.ali.cities.navigation.cityScreenRoute
import hrhera.ali.history.navigation.HISTORY_ROUTE_NAME
import hrhera.ali.history.navigation.historyRoute
import hrhera.ali.wether_details.navigation.DETAILS_ROUTE_NAME
import hrhera.ali.wether_details.navigation.detailsRoute

@Composable
fun WeatherNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = CITY_ROUT_NAME
    ) {
        cityScreenRoute(
            onShowHistory = { name ->
                navController.navigate("$HISTORY_ROUTE_NAME/$name")
            }
        ) { name, id ->
            navController.navigate("$DETAILS_ROUTE_NAME/$id") {
                popUpTo("$HISTORY_ROUTE_NAME/$name") {
                    inclusive = true
                }
            }
        }
        historyRoute { detailsId ->
            navController.navigate("$DETAILS_ROUTE_NAME/$detailsId")
        }
        detailsRoute()
    }
}