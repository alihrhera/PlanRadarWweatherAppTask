package hrhera.ali.core

import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import java.io.IOException

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
        emit(ResultSource.Error(message = errorHandling(it)))
    }

fun errorHandling(e: Throwable): String {
    return when (e) {
        is HttpException -> {
            httpException(e)
        }
        is IOException -> {
            "Io Error: ${e.message}"
        }
        else -> {
            e.message ?: "Unknown Error"
        }
    }
}

private fun httpException(e: HttpException): String {
    val errorBody = e.response()?.errorBody()?.string()
    return if (!errorBody.isNullOrEmpty()) {
        try {
            val jsonElement = JsonParser.parseString(errorBody)
            val jsonObj = jsonElement.asJsonObject
            if (jsonObj.has("message")) {
                jsonObj["message"].asString
            } else {
                errorBody
            }
        } catch (ex: Exception) {
            "Error parsing error message"
        }
    } else {
        "HTTP ${e.code()} ${e.message()}"
    }
}
