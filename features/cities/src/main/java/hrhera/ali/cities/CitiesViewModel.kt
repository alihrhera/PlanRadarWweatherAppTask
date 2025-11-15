package hrhera.ali.cities

import dagger.hilt.android.lifecycle.HiltViewModel
import hrhera.ali.core.BaseViewModel
import hrhera.ali.core.ResultSource
import hrhera.ali.domain.usecase.GetCitiesUseCase
import hrhera.ali.domain.usecase.GetCityWeatherUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getCityWeatherUsecase: GetCityWeatherUseCase,
) : BaseViewModel<CityUiStat, CitiesScreenEvents>(CityUiStat()) {
    override fun processAction(
        oldState: CityUiStat, action: CitiesScreenEvents
    ) {

        when (action) {
            is CitiesScreenEvents.ShowAddCityBottomSheet -> {
                updateState {
                    oldState.copy(
                        showAddCityBottomSheet = true,
                        searchQuery = "",
                        searchLoading = false
                    )
                }

            }

            is CitiesScreenEvents.AutoObserve -> {
                updateState { oldState.copy(isAutoObserved = !oldState.isAutoObserved) }
            }

            is CitiesScreenEvents.LoadData -> loadCities(oldState)
            is CitiesScreenEvents.SearchForCity -> {
                searchForCity(oldState, action.cityName)
            }

            is CitiesScreenEvents.DismissAddCityBottomSheet -> {
                updateState { oldState.copy(showAddCityBottomSheet = false) }
            }
        }
    }

    fun loadCities(oldState: CityUiStat) {
        launchTask {
            getCitiesUseCase().collect {
                when (it) {
                    is ResultSource.Error -> {
                        oldState.copy(isLoading = false, error = it.message)
                    }

                    is ResultSource.Loading -> {
                        updateState {
                            oldState.copy(isLoading = true, error = "")
                        }
                    }
                    is ResultSource.Success -> {
                        updateState {
                            oldState.copy(isLoading = false, cities = it.data)
                        }
                    }
                }
            }

        }
    }


    fun searchForCity(oldState: CityUiStat, cityName: String) {
        updateState {
            oldState.copy(searchQuery = cityName, searchLoading = true)
        }
        launchTask {
            getCityWeatherUsecase(cityName).collect {
                when (it) {
                    is ResultSource.Error -> updateState {
                        oldState.copy(searchLoading = false, error = it.message)
                    }

                    is ResultSource.Loading -> updateState {
                        oldState.copy(searchLoading = true)
                    }

                    is ResultSource.Success -> {
                        updateState {
                            oldState.copy(searchLoading = false)
                        }
                        delay(200)
                        emitAction(CitiesScreenEvents.DismissAddCityBottomSheet)
                    }
                }
            }

        }
    }


}