package hrhera.ali.cities

import dagger.hilt.android.lifecycle.HiltViewModel
import hrhera.ali.core.BaseViewModel
import hrhera.ali.core.ResultSource
import hrhera.ali.domain.models.City
import hrhera.ali.domain.usecase.GetCitiesUseCase
import hrhera.ali.domain.usecase.GetCityWeatherUseCase
import hrhera.ali.domain.usecase.RemoveCityUseCase
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getCityWeatherUsecase: GetCityWeatherUseCase,
    private val removeCityUseCase: RemoveCityUseCase,
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

            is CitiesScreenEvents.LoadData -> loadCities(oldState)
            is CitiesScreenEvents.GotoCityHistory -> updateState {
                oldState.copy(
                    moveToCity = City(name = action.name, emptyList()),
                )
            }

            is CitiesScreenEvents.SearchForCity -> {
                searchForCity(oldState, action.cityName)
            }

            is CitiesScreenEvents.DismissAddCityBottomSheet -> {
                updateState { oldState.copy(showAddCityBottomSheet = false) }
            }

            is CitiesScreenEvents.FetchWeatherAndMoveToDetails -> {
                searchForCity(oldState, action.name)
            }

            is CitiesScreenEvents.DeleteCity -> {
                deleteCity(oldState, action.name)
            }
        }
    }

    private fun deleteCity(oldState: CityUiStat, name: String) {
        updateState { oldState.copy(deleteCityName = name) }
        launchTask {
            removeCityUseCase(name).collect {
                when (it) {
                    is ResultSource.Error -> updateState {
                        uiState.value.copy(
                            error = it.message,
                            deleteCityName = null
                        )
                    }
                    ResultSource.Loading -> {}
                    is ResultSource.Success -> {
                        updateState {
                            uiState.value.copy(
                                cities = it.data,
                                deleteCityName = null
                            )
                        }

                    }
                }
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
                        oldState.copy(
                            searchLoading = false,
                            error = it.message,
                            moveToCity = null,
                            detailsId = null
                        )
                    }

                    is ResultSource.Loading -> updateState {
                        oldState.copy(
                            searchLoading = true,
                            moveToCity = null,
                            detailsId = null
                        )
                    }

                    is ResultSource.Success -> {
                        updateState {
                            oldState.copy(
                                searchLoading = false,
                                moveToCity = City(name = cityName, history = it.data),
                                detailsId = it.data.firstOrNull()?.id,
                                showAddCityBottomSheet = false
                            )
                        }
                    }
                }
            }

        }
    }


}