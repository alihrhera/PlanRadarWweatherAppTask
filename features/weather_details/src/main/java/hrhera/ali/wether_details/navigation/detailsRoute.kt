package hrhera.ali.wether_details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hrhera.ali.wether_details.WeatherDetailsRoute


const val DETAILS_ROUTE_NAME = "/details"
const val DETAILS_ARGUMENT_ID = "id"
private const val DETAILS_ROUTE = "/details/{$DETAILS_ARGUMENT_ID}"

fun NavGraphBuilder.detailsRoute() {
    composable(
        route = DETAILS_ROUTE,
        arguments = listOf(
            navArgument(DETAILS_ARGUMENT_ID) {
                type = NavType.LongType; defaultValue = -1
            }
        )
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getLong(DETAILS_ARGUMENT_ID) ?: -1
        if (id != -1L) WeatherDetailsRoute(id)

    }
}

