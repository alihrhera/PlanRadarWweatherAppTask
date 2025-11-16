package hrhera.ali.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hrhera.ali.history.HistoryUiState

@Composable
fun HistoriesList(
    uiState: HistoryUiState,
    onNavToWeather: (Long) -> Unit,
    onDeleteItem: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            count = uiState.history.size,
            key = { index ->
                index
            }
        ) { index ->
            val history = uiState.history[index]
            HistoryItem(
                showLoading = uiState.deleteId == history.id,
                weatherEntry = history,
                onNavToWeather = { onNavToWeather(history.id) },
                onDeleteItem = { onDeleteItem(history.id) }
            )
        }
    }
}