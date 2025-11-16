package hrhera.ali.history

import hrhera.ali.core.UiEvent

sealed class HistoryActions : UiEvent {
    data class OnFetchWeather(val name: String) : HistoryActions()
    data object OnRefreshFetchWeather : HistoryActions()
}