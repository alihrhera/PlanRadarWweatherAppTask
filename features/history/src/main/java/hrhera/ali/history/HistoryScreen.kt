package hrhera.ali.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hrhera.ali.core.utils.capitalizeWords
import hrhera.ali.history.components.HistoriesList


@Composable
fun HistoryScreenRoute(
    cityName: String?,
    detailsId: Long?,
    onNavToWeather: (Long) -> Unit,
) {
    val viewModel: HistoryViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(cityName) {
        cityName?.let {
            viewModel.updateState {
                this.copy(detailsId = detailsId)
            }
            viewModel.emitAction(HistoryActions.OnFetchWeather(it))
        }

    }

    LaunchedEffect(uiState.detailsId) {
        uiState.detailsId?.let {
            if (it != -1L) {
                viewModel.updateState { this.copy(detailsId = null) }
                onNavToWeather(it)
            }
        }
    }

    ScreenContent(uiState, viewModel, onNavToWeather)
}

@Composable
private fun ScreenContent(
    uiState: HistoryUiState, viewModel: HistoryViewModel, onNavToWeather: (Long) -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .pullToRefresh(
                    isRefreshing = uiState.isRefresh,
                    onRefresh = {
                        viewModel.emitAction(HistoryActions.OnRefreshFetchWeather)
                    },
                    enabled = !uiState.isRefresh,
                    state = rememberPullToRefreshState(),
                ),
        ) {
            Text(
                text = uiState.name.capitalizeWords(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Box(
                contentAlignment = Alignment.Center
            ) {
                HistoriesList(
                    uiState = uiState, onNavToWeather = onNavToWeather
                )
                if (uiState.isLoading) CircularProgressIndicator()
            }
        }
    }
}







