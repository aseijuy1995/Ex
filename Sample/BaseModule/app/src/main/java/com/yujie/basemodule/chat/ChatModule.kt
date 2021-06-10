//package tw.north27.coachingapp.chat
//
//import com.google.gson.Gson
//import com.jakewharton.rxrelay3.PublishRelay
//import com.yujie.utilmodule.http.OkHttpUtil
//import com.yujie.utilmodule.http.createWebSocket
//import com.yujie.utilmodule.util.logD
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Job
//import okhttp3.Headers
//import okhttp3.WebSocket
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.MockWebServer
//import tw.north27.coachingapp.model.result.ChatInfo
//import kotlin.coroutines.CoroutineContext
//
////class ChatModule(val url: String, val okHttpUtil: OkHttpUtil) : CoroutineScope {
//class ChatModule(val okHttpUtil: OkHttpUtil) : IChatModule, CoroutineScope {
//
//    override var webSocket: WebSocket? = null
//
//    //    fun execute() = okHttpUtil.createWebSocket(url, chatWebSocketListener).also {
//    override fun execute(url: String) = okHttpUtil.createWebSocket(url, chatWebSocketListener).also {
//        webSocket = it
//    }
//
//    override val chatWebSocketListener: IWebSocketListener = IWebSocketListener {
//        when (it) {
//            is SocketResults.OPEN -> {
//                infoRelay.accept(it.response.headers)
//            }
//            is SocketResults.MESSAGE -> {
//                it.text?.let {
//                    val chat = transformToChat(it)
//                    messageRelay.accept(chat)
//                }
//            }
//            is SocketResults.CLOSING -> {
//            }
//            is SocketResults.CLOSED -> {
//            }
//            is SocketResults.FAILURE -> {
//            }
//        }
//    }
//
//    override val switchInfoRelay = chatWebSocketListener.switchInfoRelay
//
//    override val switchResultRelay = chatWebSocketListener.switchResultRelay
//
//    override val messageRelay = PublishRelay.create<ChatInfo>()
//
//    override fun transformToChat(text: String): ChatInfo {
//        return Gson().fromJson(text, ChatInfo::class.java)
//    }
//
//    override fun transformToJson(chat: ChatInfo): String {
//        return Gson().toJson(chat)
//    }
//
//    override fun send(webSocket: WebSocket, chat: ChatInfo) {
//        val json = transformToJson(chat)
//        webSocket.send(json)
//    }
//
//    override val infoRelay: PublishRelay<Headers> = PublishRelay.create<Headers>()
//
//    /**---------------------------------------------------------------------------------------------------------------------------
//     * Server Socket
//     * */
//
//    override suspend fun executeServer(): String {
//        val server = MockWebServer()
//        server.enqueue(MockResponse().withWebSocketUpgrade(serverWebSocketListener))
//        val hostName = server.hostName
//        val port = server.port
//        logD("hostName = $hostName\nport = $port")
//        return "ws://$hostName:$port"
//    }
//
//    override val serverWebSocketListener: IWebSocketListener = IWebSocketListener {
//        when (it) {
//            is SocketResults.OPEN -> {
//                serverInfoRelay.accept(it.response.headers)
//            }
//            is SocketResults.MESSAGE -> {
//                val webSocket = it.webSocket
//                it.text?.let {
//                    val chat = transformToChat(it)
//                    chat.text = "Server - ${chat.text}"
//                    chat.recipient = chat.sender.also { chat.sender = chat.recipient }
//                    serverMessageRelay.accept(chat)
//                    send(webSocket, chat)
//                }
//            }
//            is SocketResults.CLOSING -> {
//            }
//            is SocketResults.CLOSED -> {
//            }
//            is SocketResults.FAILURE -> {
//            }
//        }
//    }
//
//    override val serverSwitchInfoRelay = serverWebSocketListener.switchInfoRelay
//
//    override val serverSwitchResultRelay = serverWebSocketListener.switchResultRelay
//
//    override val serverMessageRelay = PublishRelay.create<ChatInfo>()
//
//    override val serverInfoRelay: PublishRelay<Headers> = PublishRelay.create<Headers>()
//
//    override val coroutineContext: CoroutineContext
//        get() = Job()
//
//}