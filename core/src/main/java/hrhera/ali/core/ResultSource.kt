package hrhera.ali.core

sealed class ResultSource<out T> {
    data class Success<T>(val data: T) : ResultSource<T>()
    data object Loading : ResultSource<Nothing>()
    data class Error(val message: String) : ResultSource<Nothing>()

}