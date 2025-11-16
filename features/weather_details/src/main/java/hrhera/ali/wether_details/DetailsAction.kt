package hrhera.ali.wether_details

import hrhera.ali.core.UiEvent

sealed class DetailsAction : UiEvent {
    data class OnGetWeatherDetails(val id: Long) : DetailsAction()
}
