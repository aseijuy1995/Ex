//package tw.north27.coachingapp.chat
//
//import com.jakewharton.rxrelay3.BehaviorRelay
//import okhttp3.Response
//import okhttp3.WebSocket
//import okhttp3.WebSocketListener
//import okio.ByteString
//import timber.log.Timber
//
//open class IWebSocketListener(private val results: (SocketResults) -> Unit) : WebSocketListener() {
//
//    //info開關
//    val switchInfoRelay = BehaviorRelay.createDefault<Boolean>(false)
//
//    //Result開關
//    val switchResultRelay = BehaviorRelay.createDefault<Boolean>(true)
//
//    override fun onOpen(webSocket: WebSocket, response: Response) {
//        super.onOpen(webSocket, response)
//        if (switchInfoRelay.value) Timber.d("onOpen()\nresponse = ${response}\nrequest.headers = ${response.request.headers}\nresponse.headers = ${response.headers}")
//        if (switchResultRelay.value) results.invoke(SocketResults.open(webSocket, response))
//    }
//
//    override fun onMessage(webSocket: WebSocket, text: String) {
//        super.onMessage(webSocket, text)
//        if (switchInfoRelay.value) Timber.d("onMessage()\ntext = $text")
//        if (switchResultRelay.value) results.invoke(SocketResults.message(webSocket, text, null))
//    }
//
//    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//        super.onMessage(webSocket, bytes)
//        if (switchInfoRelay.value) Timber.d("onMessage()\nbytes = $bytes")
//        if (switchResultRelay.value) results.invoke(SocketResults.message(webSocket, null, bytes))
//    }
//
//    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//        super.onClosing(webSocket, code, reason)
//        if (switchInfoRelay.value) Timber.d("onClosing()\ncode = $code\nreason = $reason")
//        if (switchResultRelay.value) results.invoke(SocketResults.closing(webSocket, code, reason))
//    }
//
//    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
//        super.onClosed(webSocket, code, reason)
//        if (switchInfoRelay.value) Timber.d("onClosed()\ncode = $code\nreason = $reason")
//        if (switchResultRelay.value) results.invoke(SocketResults.closed(webSocket, code, reason))
//    }
//
//    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//        super.onFailure(webSocket, t, response)
//        if (switchInfoRelay.value) Timber.d("onFailure()\nThrowable = $t\nrequest.headers = ${response?.request?.headers}\nresponse.headers = ${response?.headers}")
//        if (switchResultRelay.value) results.invoke(SocketResults.failure(webSocket, t, response))
//    }
//
//}
//
//sealed class SocketResults {
//
//    companion object {
//        fun open(webSocket: WebSocket, response: Response): SocketResults = OPEN(webSocket, response)
//
//        fun message(webSocket: WebSocket, text: String?, bytes: ByteString?): SocketResults = MESSAGE(webSocket, text, bytes)
//
//        fun closing(webSocket: WebSocket, code: Int, reason: String): SocketResults = CLOSING(webSocket, code, reason)
//
//        fun closed(webSocket: WebSocket, code: Int, reason: String): SocketResults = CLOSED(webSocket, code, reason)
//
//        fun failure(webSocket: WebSocket, t: Throwable, response: Response?): SocketResults = FAILURE(webSocket, t, response)
//    }
//
//    data class OPEN(val webSocket: WebSocket, val response: Response) : SocketResults()
//
//    data class MESSAGE(val webSocket: WebSocket, val text: String?, val bytes: ByteString?) : SocketResults()
//
//    data class CLOSING(val webSocket: WebSocket, val code: Int, val reason: String) : SocketResults()
//
//    data class CLOSED(val webSocket: WebSocket, val code: Int, val reason: String) : SocketResults()
//
//    data class FAILURE(val webSocket: WebSocket, val t: Throwable, val response: Response?) : SocketResults()
//}