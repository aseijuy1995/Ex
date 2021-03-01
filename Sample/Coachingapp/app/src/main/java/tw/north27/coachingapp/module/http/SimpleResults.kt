package tw.north27.coachingapp.module.http

import retrofit2.HttpException
import java.io.IOException

sealed class SimpleResults<out T> {

    companion object {
        fun <T> successful(data: T): SimpleResults<T> = Successful<T>(data)

        fun <T> clientErrors(error: HttpException): SimpleResults<T> = ClientErrors(error)

        fun <T> netWorkError(error: IOException): SimpleResults<T> = NetWorkError(error)
    }

    //2xx
    data class Successful<out T>(val data: T) : SimpleResults<T>()

    //HttpException
    data class ClientErrors(val error: HttpException) : SimpleResults<Nothing>()

    //IOException
    data class NetWorkError(val error: IOException) : SimpleResults<Nothing>()
}
