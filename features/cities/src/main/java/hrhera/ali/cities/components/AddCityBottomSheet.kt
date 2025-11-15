package hrhera.ali.cities.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import hrhera.ali.cities.CitiesScreenEvents
import hrhera.ali.cities.CityUiStat

@Composable
@OptIn(ExperimentalMaterial3Api::class)
 fun AddCityBottomSheet(
    uiState: CityUiStat,
    emitAction: (CitiesScreenEvents) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()

    if (uiState.showAddCityBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                emitAction(
                    CitiesScreenEvents.DismissAddCityBottomSheet
                )
            },
            sheetState = bottomSheetState
        ) {
            AddCityBottomSheetContent(
                isLoaderVisible = uiState.searchLoading,
                name = uiState.searchQuery,
                onAddClick = {
                    emitAction(
                        CitiesScreenEvents.SearchForCity(it)
                    )
                },
                onCancelClick = {
                    emitAction(
                        CitiesScreenEvents.DismissAddCityBottomSheet
                    )
                }
            )
        }
    }
}
