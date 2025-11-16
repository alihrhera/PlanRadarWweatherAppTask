package hrhera.ali.cities.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hrhera.ali.cities.CitiesScreen
import hrhera.ali.domain.models.City


const val CITY_ROUT_NAME="/cities"
fun NavGraphBuilder.cityScreenRoute(
    onShowHistory: (String, Long?) -> Unit
) {
    composable(CITY_ROUT_NAME) {
        CitiesScreen {name,id->
            onShowHistory(name,id)
        }
    }
}