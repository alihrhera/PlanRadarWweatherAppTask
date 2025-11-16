package hrhera.ali.history

import hrhera.ali.core.UiEvent

sealed class HistoryActions : UiEvent {
    data class OnFetchWeather(val name: String) : HistoryActions()
    data class OnMoveToDetails(val id: Long) : HistoryActions()
    data object OnRefreshFetchWeather : HistoryActions()
}