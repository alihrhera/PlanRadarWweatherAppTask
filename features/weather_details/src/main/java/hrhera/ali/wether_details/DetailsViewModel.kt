package hrhera.ali.wether_details

import dagger.hilt.android.lifecycle.HiltViewModel
import hrhera.ali.core.BaseViewModel
import hrhera.ali.core.ResultSource
import hrhera.ali.domain.usecase.GetWeatherHistoryDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getWeatherDetailsUseCase: GetWeatherHistoryDetailsUseCase
) : BaseViewModel<DetailsUiState, DetailsAction>(DetailsUiState()) {

    override fun processAction(oldState: DetailsUiState, action: DetailsAction) {
        when (action) {
            is DetailsAction.OnGetWeatherDetails -> getWeatherDetails(action.id)
        }
    }

    fun getWeatherDetails(id: Long) {
        launchTask {
            getWeatherDetailsUseCase(id).collect { result ->
                when (result) {
                    is ResultSource.Success -> setState(
                        DetailsUiState().fromWeather(
                            weather = result.data
                        )
                    )

                    is ResultSource.Error -> updateState {
                        copy(errorEntity = result.message, isLoading = false)
                    }

                    is ResultSource.Loading -> updateState {
                        copy(isLoading = true)
                    }

                }
            }
        }
    }
}