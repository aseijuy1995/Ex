package tw.north27.coachingapp.module.ext

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.module.http.SimpleResults
import java.io.IOException


suspend fun <T> safeApiResults(data: suspend () -> Response<T>): Results<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = data.invoke()
            val code = response.code()
            val headers = response.headers()
            val body = response.body()
            when (code) {
                in 100 until 200 -> Results.informational(code, headers)
                in 200 until 300 -> Results.successful(code, headers, body!!)
                in 300 until 400 -> Results.redirection(code, headers)
                401, 403 -> Results.authError(code, headers)
                in 400 until 500 -> Results.clientErrors(code, headers)
                else -> Results.serverError(code, headers)
            }
        } catch (e: IOException) {
            Results.netWorkError(e)
        }
    }
}

suspend fun <T> safeApiSimpleResults(data: suspend () -> T): SimpleResults<T> {
    return withContext(Dispatchers.IO) {
        try {
            SimpleResults.successful(data.invoke())
        } catch (e: HttpException) {
            SimpleResults.clientErrors(e)
        } catch (e: IOException) {
            SimpleResults.netWorkError(e)
        }
    }
}