package hrhera.ali.cities.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hrhera.ali.cities.CitiesScreenEvents
import hrhera.ali.cities.CitiesViewModel
import hrhera.ali.cities.CityUiStat


@Composable
fun CitiesScreenContent(
    viewModel: CitiesViewModel,
    uiState: CityUiStat,
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
            CityTitle()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                LazyColumn {
                    items(uiState.cities.size) { index ->
                        val cityName = uiState.cities[index].name
                        CityItem(
                            city = cityName,
                            isLoading = uiState.deleteCityName == cityName,
                            onMoveToHistory = {
                                viewModel.emitAction(
                                    CitiesScreenEvents.GotoCityHistory(cityName)
                                )
                            },
                            onMoveToDetails = {
                                viewModel.emitAction(
                                    CitiesScreenEvents.FetchWeatherAndMoveToDetails(
                                        cityName
                                    )
                                )
                            },
                            onDeleteCity = {
                                viewModel.emitAction(
                                    CitiesScreenEvents.DeleteCity(cityName)
                                )
                            }
                        )
                    }
                }
                if (uiState.cities.isEmpty()) {
                    Text(
                        text = "No cities found, please add one",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                }
            }

        }
    }
}
