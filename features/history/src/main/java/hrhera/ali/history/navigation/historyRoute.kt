package hrhera.ali.history.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hrhera.ali.history.HistoryScreenRoute

fun NavGraphBuilder.historyRoute(
    navController: NavHostController
) {
    composable(
        route = "/history/{cityName}",
        arguments = listOf(
            navArgument("cityName") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val cityName = backStackEntry.arguments?.getString("cityName")
        HistoryScreenRoute(cityName = cityName) {
        }
    }
}

