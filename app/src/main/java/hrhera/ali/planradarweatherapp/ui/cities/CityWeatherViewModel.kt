package hrhera.ali.planradarweatherapp.ui.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
/*
class CityWeatherViewModel constructor(
    private val cityWeatherUseCase: GetCityWeatherUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CityUiStat())
    val state: StateFlow<CityUiStat> = _state.asStateFlow()

    fun fetch(city: String) {
        viewModelScope.launch {
            cityWeatherUseCase(city).collect { result ->
                updateUiState(result)
            }
        }
    }

    private fun updateUiState(result: Result<Weather>) {
        when (result) {
            is Result.Success -> _state.value = CityUiStat(weather = result.data)

            is Result.Error -> _state.value = CityUiStat(error = result.message)
            is Result.Loading -> _state.value = CityUiStat(isLoading = true)
        }
    }
}*/