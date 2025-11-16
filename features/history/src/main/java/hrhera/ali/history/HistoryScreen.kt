package hrhera.ali.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hrhera.ali.history.components.HistoryItem


@Composable
fun HistoryScreenRoute(
    cityName: String?,
    onNavToWeather: (String) -> Unit,
) {
    val viewModel: HistoryViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(cityName) {
        cityName?.let {
            viewModel.emitAction(HistoryActions.OnFetchWeather(it))
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullToRefresh(
                isRefreshing = uiState.isRefresh,
                onRefresh = {
                    viewModel.emitAction(HistoryActions.OnRefreshFetchWeather)
                },
                enabled = !uiState.isRefresh,
                state = rememberPullToRefreshState(),
            ),
        contentAlignment = Alignment.Center
    ) {
        HistoriesList(
            uiState = uiState,
            onNavToWeather = onNavToWeather
        )
        if (uiState.isLoading) CircularProgressIndicator()
    }


}


@Composable
private fun HistoriesList(
    uiState: HistoryUiState,
    onNavToWeather: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = uiState.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        items(
            count = uiState.history.size,
            key = { index ->
                index
            }
        ) { index ->
            val history = uiState.history[index]
            HistoryItem(
                weatherEntry = history,
                onNavToWeather = { onNavToWeather(uiState.name) },
            )
        }
    }
}




