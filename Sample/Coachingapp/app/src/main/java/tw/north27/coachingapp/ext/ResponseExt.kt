package tw.north27.coachingapp.ext

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import tw.north27.coachingapp.util.http.Results
import java.io.IOException


suspend fun <T> Response<T>.safeApiResult(dispatcher: CoroutineDispatcher): Results<T> {
    val response = this
    val code = response.code()
    val body = response.body()
    val message = response.message()

    return withContext(dispatcher) {
        try {
            if (response.isSuccessful)
                Results.success(body!!)
            else
                Results.failed(code, message)
        } catch (e: IOException) {
            Results.netWorkError()
        }
    }
}

class ErrorResponse : Exception()