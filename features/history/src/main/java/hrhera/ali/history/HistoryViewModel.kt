package hrhera.ali.history

import dagger.hilt.android.lifecycle.HiltViewModel
import hrhera.ali.core.BaseViewModel
import hrhera.ali.core.ResultSource
import hrhera.ali.core.UiEvent
import hrhera.ali.domain.usecase.GetCityWeatherUseCase
import hrhera.ali.domain.usecase.GetLocalCityWeatherUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getCityWeatherUseCase: GetCityWeatherUseCase,
    private val fetchLocalCityWeatherUseCase: GetLocalCityWeatherUseCase
) :
    BaseViewModel<HistoryUiState, UiEvent>(HistoryUiState()) {
    override fun processAction(
        oldState: HistoryUiState,
        action: UiEvent
    ) {
        when (action) {
            is HistoryActions.OnFetchWeather -> fetchLocalCityWeatherUseCase(
                city = action.name,
                oldState = oldState.copy(name = action.name)
            )

            is HistoryActions.OnRefreshFetchWeather -> refreshCityWeather(oldState)
            is HistoryActions.OnMoveToDetails -> updateState {
                oldState.copy(detailsId = action.id)
            }
        }
    }

    fun refreshCityWeather(oldState: HistoryUiState) {
        launchTask {
            getCityWeatherUseCase(oldState.name).collect {
                when (it) {
                    is ResultSource.Success -> updateState {
                        oldState.copy(
                            history = it.data,
                            isRefresh = false
                        )
                    }

                    is ResultSource.Error -> updateState {
                        oldState.copy(
                            error = it.message,
                            isRefresh = false
                        )
                    }

                    is ResultSource.Loading -> updateState { oldState.copy(isRefresh = true) }
                }
            }
        }
    }

    fun fetchLocalCityWeatherUseCase(city: String, oldState: HistoryUiState) {
        launchTask {
            fetchLocalCityWeatherUseCase(city).collect {
                when (it) {
                    is ResultSource.Success -> updateState { oldState.copy(history = it.data) }
                    is ResultSource.Error -> updateState { oldState.copy(error = it.message) }
                    is ResultSource.Loading -> updateState { oldState.copy(isLoading = true) }
                }
            }
        }
    }

}