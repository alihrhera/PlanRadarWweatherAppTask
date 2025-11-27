package hrhera.ali.history.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hrhera.ali.history.HistoryScreenRoute


const val HISTORY_ROUTE_NAME = "/history"
const val HISTORY_ARGUMENT_CITY_NAME = "cityName"
private const val HISTORY_ROUTE = "/history/{$HISTORY_ARGUMENT_CITY_NAME}"

fun NavGraphBuilder.historyRoute(
    onMoveToDetails: (Long) -> Unit
) {
    composable(
        route = HISTORY_ROUTE,
        arguments = listOf(
            navArgument(HISTORY_ARGUMENT_CITY_NAME) { type = NavType.StringType },
        )
    ) { backStackEntry ->
        val cityName = backStackEntry.arguments?.getString(HISTORY_ARGUMENT_CITY_NAME)
        HistoryScreenRoute(cityName = cityName) { id ->
            onMoveToDetails(id)
        }
    }
}

