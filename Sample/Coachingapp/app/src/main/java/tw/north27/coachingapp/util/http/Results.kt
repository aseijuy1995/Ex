package tw.north27.coachingapp.util.http

sealed class Results<out T> {

    companion object {
        fun <T> success(data: T): Results<T> = Success<T>(data)

        fun <T> failed(code: Int, msg: String): Results<T> = Failed(code, msg)

        fun <T> netWorkError(): Results<T> = NetWorkError
    }

    data class Success<out T>(val data: T) : Results<T>()

    data class Failed(val code: Int, val msg: String) : Results<Nothing>()

    object NetWorkError : Results<Nothing>()
}