package hrhera.ali.cities

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import hrhera.ali.cities.components.AddCityBottomSheet
import hrhera.ali.cities.components.CitiesScreenContent


@Composable
fun CitiesScreen(
    onMoveToHistory: (String) -> Unit,
    onMoveToDetails: (String, Long) -> Unit
) {
    val viewModel: CitiesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    LoadHistory(viewModel)
    ErrorRender(uiState)
    MoveToCityHistory(uiState, viewModel, onMoveToHistory)
    MoveToDetails(uiState, viewModel, onMoveToDetails)
    AddCityBottomSheet(uiState) { viewModel.emitAction(it) }
    CitiesScreenContent(viewModel, uiState)


}

@Composable
private fun MoveToDetails(
    uiState: CityUiStat,
    viewModel: CitiesViewModel,
    onMoveToDetails: (String, Long) -> Unit
) {
    LaunchedEffect(uiState.detailsId) {
        uiState.detailsId?.let {
            val name = uiState.moveToCity?.name ?: ""
            val id = it
            viewModel.updateState {
                CityUiStat(
                    moveToCity = null,
                    detailsId = null
                )
            }
            onMoveToDetails(name, id)
        }
    }
}

@Composable
private fun MoveToCityHistory(
    uiState: CityUiStat,
    viewModel: CitiesViewModel,
    onMoveToHistory: (String) -> Unit
) {
    LaunchedEffect(uiState.moveToCity) {
        uiState.moveToCity?.let {
            val name = it.name
            viewModel.updateState {
                CityUiStat(
                    moveToCity = null,
                    detailsId = null
                )
            }
            onMoveToHistory(name)
        }
    }
}

@Composable
private fun ErrorRender(uiState: CityUiStat) {
    val context = LocalContext.current
    LaunchedEffect(uiState.error) {
        if (uiState.error.isNotBlank()) {
            Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun LoadHistory(viewModel: CitiesViewModel) {
    LaunchedEffect(Unit) {
        viewModel.emitAction(CitiesScreenEvents.LoadData)
    }
}


