package hrhera.ali.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

val defaultDispatcher = Dispatchers.IO

inline fun <T> buildTask(
    dispatcher: CoroutineDispatcher = defaultDispatcher,
    crossinline task: suspend () -> T
): Flow<ResultSource<T>> = flow<ResultSource<T>> {
    emit(ResultSource.Success(data = task()))
}
    .flowOn(dispatcher)
    .onStart {
        emit(ResultSource.Loading)
    }.catch {
        emit(ResultSource.Error(message = it.message ?: "Unknown Error"))
    }