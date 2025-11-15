package hrhera.ali.planradarweatherapp.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State:UiState,Event:UiEvent>(initStateValue:State):
    ViewModel() {
    private var _uiState = MutableStateFlow<State>(initStateValue)
    val uiState: StateFlow<State> get() = _uiState

    fun emitAction(event: Event) {
        processAction(_uiState.value, event)
    }

    fun updateState(update: State.() -> State) {
        _uiState.update { currentState -> currentState.update() }
    }

    fun setState(newState: State) {
        _uiState.tryEmit(newState)
    }


    abstract fun processAction(oldState: State, action: Event)

    fun launchTask(dispatcher: CoroutineDispatcher,task: suspend () -> Unit) =
        viewModelScope.launch(dispatcher) { task() }

}

interface UiEvent
interface UiState