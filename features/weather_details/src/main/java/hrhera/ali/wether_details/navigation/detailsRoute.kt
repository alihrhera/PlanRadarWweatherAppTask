package hrhera.ali.wether_details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hrhera.ali.wether_details.WeatherDetailsRoute


private const val DETAILS_ROUTE = "/details/{id}"
const val DETAILS_ROUTE_NAME = "/details"
fun NavGraphBuilder.detailsRoute() {
    composable(
        route = DETAILS_ROUTE,
        arguments = listOf(
            navArgument("id") {
                type = NavType.LongType; defaultValue = -1
            }
        )
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getLong("id") ?: -1
        if (id != -1L) WeatherDetailsRoute(id)

    }
}

