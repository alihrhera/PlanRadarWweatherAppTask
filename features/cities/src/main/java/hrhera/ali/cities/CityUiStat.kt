package hrhera.ali.cities

import hrhera.ali.core.UiState
import hrhera.ali.domain.models.City


data class CityUiStat(
    val isLoading: Boolean = false,
    val isAutoObserved: Boolean = false,
    val showAddCityBottomSheet: Boolean = false,
    val cities: List<City> = emptyList(),
    val searchLoading: Boolean = false,
    val searchQuery: String = "",
    val error: String = ""
) : UiState