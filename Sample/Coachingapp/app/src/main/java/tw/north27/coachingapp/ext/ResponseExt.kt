package tw.north27.coachingapp.ext

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.Headers
import retrofit2.Response
import java.io.IOException


suspend fun <T> Response<T>.safeApiResults(dispatcher: CoroutineDispatcher): Results<T> {
    val code = code()
    val headers = headers()
    val body = body()
    val message = message()
    return withContext(dispatcher) {
        try {
            when (code) {
                in 100 until 200 -> Results.informational(code, headers)
                in 200 until 300 -> Results.successful(code, headers, body!!)
                in 300 until 400 -> Results.redirection(code, headers)
                in 400 until 500 -> Results.clientErrors(code, headers, message)
                else -> Results.serverError(code, headers)
            }
        } catch (e: IOException) {
            Results.netWorkError(e)
        }
    }
}

sealed class Results<out T> {

    companion object {
        open fun <T> informational(code: Int, header: Headers): Results<T> = Informational(code, header)

        open fun <T> successful(code: Int, header: Headers, data: T): Results<T> = Successful<T>(code, header, data)

        open fun <T> redirection(code: Int, header: Headers): Results<T> = Redirection(code, header)

        open fun <T> clientErrors(code: Int, headers: Headers, msg: String): Results<T> = ClientErrors(code, headers, msg)

        open fun <T> serverError(code: Int, headers: Headers): Results<T> = ServerError(code, headers)

        open fun <T> netWorkError(error: IOException): Results<T> = NetWorkError(error)
    }

    data class Informational<out T>(val code: Int, val header: Headers) : Results<T>()

    data class Successful<out T>(val code: Int, val header: Headers, val data: T) : Results<T>()

    data class Redirection<out T>(val code: Int, val header: Headers) : Results<T>()

    data class ClientErrors(val code: Int, val headers: Headers, val msg: String) : Results<Nothing>()

    data class ServerError(val code: Int, val headers: Headers) : Results<Nothing>()

    data class NetWorkError(val error: IOException) : Results<Nothing>()
}

suspend fun <T> Response<T>.safeApiSignInResults(dispatcher: CoroutineDispatcher): SignInResults<T> {
    val code = code()
    val headers = headers()
    val body = body()
    val message = message()

    return withContext(dispatcher) {
        try {
            when (code) {
                in 100 until 200 -> SignInResults.informational(code, headers)
                in 200 until 300 -> SignInResults.successful(code, headers, body!!)
                in 300 until 400 -> SignInResults.redirection(code, headers)
                403 -> SignInResults.notAuth(code, headers)
                in 400 until 500 -> SignInResults.clientErrors(code, headers, message)
                else -> SignInResults.serverError(code, headers)
            }
        } catch (e: IOException) {
            SignInResults.netWorkError(e)
        }
    }

}

sealed class SignInResults<out T> {

    companion object {
        fun <T> informational(code: Int, header: Headers): SignInResults<T> = Informational(code, header)

        fun <T> successful(code: Int, header: Headers, data: T): SignInResults<T> = Successful(code, header, data)

        fun <T> redirection(code: Int, header: Headers): SignInResults<T> = Redirection(code, header)

        fun <T> notAuth(code: Int, headers: Headers): SignInResults<T> = NotAuth(code, headers)

        fun <T> clientErrors(code: Int, headers: Headers, msg: String): SignInResults<T> = ClientErrors(code, headers, msg)

        fun <T> serverError(code: Int, headers: Headers): SignInResults<T> = ServerError(code, headers)

        fun <T> netWorkError(error: IOException): SignInResults<T> = NetWorkError(error)
    }

    data class Informational<out T>(val code: Int, val header: Headers) : SignInResults<T>()

    data class Successful<out T>(val code: Int, val header: Headers, val data: T) : SignInResults<T>()

    data class Redirection<out T>(val code: Int, val header: Headers) : SignInResults<T>()

    data class NotAuth(val code: Int, val headers: Headers) : SignInResults<Nothing>()

    data class ClientErrors(val code: Int, val headers: Headers, val msg: String) : SignInResults<Nothing>()

    data class ServerError(val code: Int, val headers: Headers) : SignInResults<Nothing>()

    data class NetWorkError(val error: IOException) : SignInResults<Nothing>()
}