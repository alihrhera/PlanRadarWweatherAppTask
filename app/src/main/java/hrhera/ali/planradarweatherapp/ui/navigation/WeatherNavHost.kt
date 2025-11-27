package hrhera.ali.planradarweatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import hrhera.ali.cities.navigation.CITY_ROUT_NAME
import hrhera.ali.cities.navigation.cityScreenRoute
import hrhera.ali.core.DeviceIntegrityChecker
import hrhera.ali.history.navigation.HISTORY_ROUTE_NAME
import hrhera.ali.history.navigation.historyRoute
import hrhera.ali.malware_or_debug.navigation.DEBUG_ROUTE
import hrhera.ali.malware_or_debug.navigation.MALWARE_ROUTE
import hrhera.ali.malware_or_debug.navigation.malwareOrDebugRoute
import hrhera.ali.planradarweatherapp.BuildConfig
import hrhera.ali.wether_details.navigation.DETAILS_ROUTE_NAME
import hrhera.ali.wether_details.navigation.detailsRoute

@Composable
fun WeatherNavHost(
    navController: NavHostController,
) {
    val context = LocalContext.current
    var checkResultEmulator by remember { mutableStateOf(false) }
    var checkResultRoot by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        DeviceIntegrityChecker.check(
            context = context,
            onEmulator = { checkResultEmulator = it },
            onRooted = { checkResultRoot = it }
        )
    }

    HandleSecurityRedirects(checkResultEmulator, checkResultRoot, navController)

    NavHost(
        navController = navController,
        startDestination = CITY_ROUT_NAME
    ) {
        mainRoutes(navController)
        if (BuildConfig.DEBUG) {
            malwareOrDebugRoute(navController)
        }
    }
}

@Composable
private fun HandleSecurityRedirects(
    checkResultEmulator: Boolean,
    checkResultRoot: Boolean,
    navController: NavHostController
) {
    LaunchedEffect(checkResultEmulator, checkResultRoot) {
        if (checkResultEmulator) {
            navController.navigate(DEBUG_ROUTE) {
                launchSingleTop = true
            }
        } else if (checkResultRoot) {
            navController.navigate(MALWARE_ROUTE) {
                launchSingleTop = true
            }
        }
    }
}

private fun NavGraphBuilder.mainRoutes(navController: NavHostController) {
    cityScreenRoute(
        onShowHistory = { name ->
            navController.navigate("$HISTORY_ROUTE_NAME/$name")
        }) { name, id ->
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

