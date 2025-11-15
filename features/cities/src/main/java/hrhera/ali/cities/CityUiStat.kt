package hrhera.ali.cities

import hrhera.ali.core.UiState
import hrhera.ali.domain.models.City


data class CityUiStat(
    val isLoading: Boolean = false,
    val isAutoObserved: Boolean = false,
    val showAddCityBottomSheet: Boolean = false,
    val cities: List<City> = emptyList(),
    val moveToCity: City? = null,
    val searchLoading: Boolean = false,
    val searchQuery: String = "",
    val error: String = ""
) : UiState