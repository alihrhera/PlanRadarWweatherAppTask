package hrhera.ali.wether_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import hrhera.ali.wether_details.components.WeatherScreen


@Composable
fun WeatherDetailsRoute(id: Long) {
    val viewModel: DetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(id) {
        viewModel.emitAction(DetailsAction.OnGetWeatherDetails(id))
    }
    WeatherScreen(
        uiState = uiState,
    )
}



