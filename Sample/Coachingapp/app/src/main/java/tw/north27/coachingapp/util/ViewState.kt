package tw.north27.coachingapp.util

import retrofit2.HttpException
import java.io.IOException

sealed class ViewState<out T> {

    companion object {
        fun <T> load(): ViewState<T> = Load

        fun <T> empty(str: String? = null): ViewState<T> = Empty(str)

        fun <T> data(data: T): ViewState<T> = Data<T>(data)

        fun <T> error(e: HttpException): ViewState<T> = Error(e)

        fun <T> network(e: IOException): ViewState<T> = Network(e)
    }

    object Load : ViewState<Nothing>()

    data class Empty(val str: String?) : ViewState<Nothing>()

    data class Data<out T>(val data: T) : ViewState<T>()

    data class Error(val e: HttpException) : ViewState<Nothing>()

    data class Network(val e: IOException) : ViewState<Nothing>()
}