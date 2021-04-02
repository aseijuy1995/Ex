package tw.north27.coachingapp.module.http

import okhttp3.Headers
import retrofit2.HttpException
import java.io.IOException

sealed class ResponseResults<out T> {

    companion object {
        fun <T> informational(code: Int, header: Headers): ResponseResults<T> = Informational(code, header)

        fun <T> successful(code: Int, header: Headers, data: T): ResponseResults<T> = Successful<T>(code, header, data)

        fun <T> redirection(code: Int, header: Headers): ResponseResults<T> = Redirection(code, header)

        fun <T> clientErrors(code: Int, headers: Headers, e: HttpException): ResponseResults<T> = ClientErrors(code, headers, e)

        fun <T> serverError(code: Int, headers: Headers, e: HttpException): ResponseResults<T> = ServerError(code, headers, e)

        fun <T> netWorkError(e: IOException): ResponseResults<T> = NetWorkError(e)
    }

    //1XX
    data class Informational<out T>(val code: Int, val header: Headers) : ResponseResults<T>()

    //2xx
    data class Successful<out T>(val code: Int, val headers: Headers, val data: T) : ResponseResults<T>()

    //3xx
    data class Redirection<out T>(val code: Int, val header: Headers) : ResponseResults<T>()

    //4xx
    data class ClientErrors(val code: Int, val headers: Headers, val e: HttpException) : ResponseResults<Nothing>()

    //5xx
    data class ServerError(val code: Int, val headers: Headers, val e: HttpException) : ResponseResults<Nothing>()

    //IOException
    data class NetWorkError(val e: IOException) : ResponseResults<Nothing>()
}
