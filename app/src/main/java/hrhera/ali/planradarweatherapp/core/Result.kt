package hrhera.ali.planradarweatherapp.core

sealed class Result<out T> {
    data class Success<T : Any>(val data: T) : Result<T>()
    data object Loading : Result<Nothing>()
    data class Error(val message: String) : Result<Nothing>()
}