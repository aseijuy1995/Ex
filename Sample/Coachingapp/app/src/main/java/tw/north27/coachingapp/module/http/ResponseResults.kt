package tw.north27.coachingapp.module.http

import okhttp3.Headers
import java.io.IOException

sealed class ResponseResults<out T> {

    companion object {
        fun <T> informational(code: Int, header: Headers): ResponseResults<T> = Informational(code, header)

        fun <T> successful(code: Int, header: Headers, data: T): ResponseResults<T> = Successful<T>(code, header, data)

        fun <T> redirection(code: Int, header: Headers): ResponseResults<T> = Redirection(code, header)

        fun <T> clientErrors(code: Int, headers: Headers): ResponseResults<T> = ClientErrors(code, headers)

        fun <T> serverError(code: Int, headers: Headers): ResponseResults<T> = ServerError(code, headers)

        fun <T> netWorkError(error: IOException): ResponseResults<T> = NetWorkError(error)
    }

    //1XX
    data class Informational<out T>(val code: Int, val header: Headers) : ResponseResults<T>()

    //2xx
    data class Successful<out T>(val code: Int, val headers: Headers, val data: T) : ResponseResults<T>()

    //3xx
    data class Redirection<out T>(val code: Int, val header: Headers) : ResponseResults<T>()

    //4xx
    data class ClientErrors(val code: Int, val headers: Headers) : ResponseResults<Nothing>()

    //5xx
    data class ServerError(val code: Int, val headers: Headers) : ResponseResults<Nothing>()

    //IOException
    data class NetWorkError(val error: IOException) : ResponseResults<Nothing>()
}
