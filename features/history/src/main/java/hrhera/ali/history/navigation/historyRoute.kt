package hrhera.ali.history.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hrhera.ali.history.HistoryScreenRoute


private const val HISTORY_ROUTE = "/history/{cityName}"
const val HISTORY_ROUTE_NAME = "/history"
fun NavGraphBuilder.historyRoute(
    onMoveToDetails: (Long) -> Unit
) {
    composable(
        route = HISTORY_ROUTE,
        arguments = listOf(
            navArgument("cityName") { type = NavType.StringType },
            navArgument("id") {
                type = NavType.LongType; defaultValue = -1
            }
        )
    ) { backStackEntry ->
        val cityName = backStackEntry.arguments?.getString("cityName")
        HistoryScreenRoute(cityName = cityName) { id ->
            onMoveToDetails(id)
        }
    }
}

