package hrhera.ali.history.navigation

import android.telephony.ims.SipDetails
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hrhera.ali.history.HistoryScreenRoute


const val HISTORY_ROUTE = "/history/{cityName}/{id}"
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
        val id = backStackEntry.arguments?.getLong("id")
        HistoryScreenRoute(cityName = cityName, detailsId = id) {
            onMoveToDetails(it)
        }
    }
}

