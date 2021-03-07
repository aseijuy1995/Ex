package tw.north27.coachingapp.module.ext

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.module.http.SimpleResults
import java.io.IOException

suspend fun <T> safeApiResults(data: suspend () -> T): Results<T> {
    return withContext(Dispatchers.IO) {
        try {
            Results.successful(data.invoke())
        } catch (e: HttpException) {
            Results.clientErrors(e)
        } catch (e: IOException) {
            Results.netWorkError(e)
        }
    }
}


suspend fun <T> safeApiResponseResults(data: suspend () -> Response<T>): ResponseResults<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = data.invoke()
            val code = response.code()
            val headers = response.headers()
            val body = response.body()
            when (code) {
                in 100 until 200 -> ResponseResults.informational(code, headers)
                in 200 until 300 -> ResponseResults.successful(code, headers, body!!)
                in 300 until 400 -> ResponseResults.redirection(code, headers)
                in 400 until 500 -> ResponseResults.clientErrors(code, headers)
                else -> ResponseResults.serverError(code, headers)
            }
        } catch (e: IOException) {
            ResponseResults.netWorkError(e)
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