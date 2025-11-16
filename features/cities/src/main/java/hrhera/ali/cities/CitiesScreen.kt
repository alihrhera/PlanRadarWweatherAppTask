package hrhera.ali.cities

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hrhera.ali.cities.components.AddCityBottomSheet
import hrhera.ali.cities.components.AddCityButton
import hrhera.ali.cities.components.CityItem
import hrhera.ali.cities.components.CityTitle


@Composable
fun CitiesScreen(
    onMoveToHistory: (String) -> Unit
) {
    val viewModel: CitiesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.emitAction(CitiesScreenEvents.LoadData)
    }
    val context = LocalContext.current
    LaunchedEffect(uiState.error) {
        if (uiState.error.isNotBlank()) {
            Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(uiState.moveToCity) {
        uiState.moveToCity?.let {
            viewModel.updateState {
                uiState.copy(
                    moveToCity = null
                )
            }
            onMoveToHistory(it.name)
        }
    }

    AddCityBottomSheet(uiState) { viewModel.emitAction(it) }
    ScreenContent(viewModel, uiState, onMoveToHistory)


}

@Composable
private fun ScreenContent(
    viewModel: CitiesViewModel,
    uiState: CityUiStat,
    onMoveToHistory: (String) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AddCityButton {
                viewModel.emitAction(
                    CitiesScreenEvents.ShowAddCityBottomSheet
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues = innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CityTitle(uiState.isAutoObserved) {
                viewModel.emitAction(
                    CitiesScreenEvents.AutoObserve
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                LazyColumn {
                    items(uiState.cities.size) {
                        CityItem(uiState.cities[it].name) { cityName ->
                            onMoveToHistory(cityName)
                        }
                    }
                }
                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

