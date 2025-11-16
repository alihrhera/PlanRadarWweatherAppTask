package hrhera.ali.history

import hrhera.ali.core.UiState
import hrhera.ali.domain.models.Weather

data class HistoryUiState(
    val name: String = "",
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    var detailsId: Long? = null,
    val history: List<Weather> = emptyList()
) : UiState