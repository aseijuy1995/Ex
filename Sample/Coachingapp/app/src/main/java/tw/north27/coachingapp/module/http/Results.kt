package tw.north27.coachingapp.module.http

import okhttp3.Headers
import java.io.IOException

sealed class Results<out T> {

    companion object {
        fun <T> informational(code: Int, header: Headers): Results<T> = Informational(code, header)

        fun <T> successful(code: Int, header: Headers, data: T): Results<T> = Successful<T>(code, header, data)

        fun <T> redirection(code: Int, header: Headers): Results<T> = Redirection(code, header)

        fun <T> authError(code: Int, headers: Headers): Results<T> = AuthError(code, headers)

        fun <T> clientErrors(code: Int, headers: Headers): Results<T> = ClientErrors(code, headers)

        fun <T> serverError(code: Int, headers: Headers): Results<T> = ServerError(code, headers)

        fun <T> netWorkError(error: IOException): Results<T> = NetWorkError(error)
    }

    //1XX
    data class Informational<out T>(val code: Int, val header: Headers) : Results<T>()

    //2xx
    data class Successful<out T>(val code: Int, val headers: Headers, val data: T) : Results<T>()

    //3xx
    data class Redirection<out T>(val code: Int, val header: Headers) : Results<T>()

    //401
    data class AuthError(val code: Int, val headers: Headers) : Results<Nothing>()

    //4xx
    data class ClientErrors(val code: Int, val headers: Headers) : Results<Nothing>()

    //5xx
    data class ServerError(val code: Int, val headers: Headers) : Results<Nothing>()

    //IOException
    data class NetWorkError(val error: IOException) : Results<Nothing>()
}
