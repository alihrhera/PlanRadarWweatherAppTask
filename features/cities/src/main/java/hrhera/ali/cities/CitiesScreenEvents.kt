package hrhera.ali.cities

import hrhera.ali.core.UiEvent

sealed class CitiesScreenEvents : UiEvent {
    data object ShowAddCityBottomSheet : CitiesScreenEvents()
    data class SearchForCity(val cityName: String) : CitiesScreenEvents()
    data object DismissAddCityBottomSheet : CitiesScreenEvents()
    data object LoadData : CitiesScreenEvents()
    data object AutoObserve : CitiesScreenEvents()

}

