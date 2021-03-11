package tw.north27.coachingapp.chat

import com.google.gson.Gson
import com.jakewharton.rxrelay3.ReplayRelay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import okhttp3.WebSocket
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import timber.log.Timber
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.OkHttpUtil
import tw.north27.coachingapp.module.http.createWebSocket
import kotlin.coroutines.CoroutineContext

//class ChatModule(val url: String, val okHttpUtil: OkHttpUtil) : CoroutineScope {
class ChatModule(val okHttpUtil: OkHttpUtil) : IChatModule, CoroutineScope {

    override lateinit var webSocket: WebSocket

    //    fun execute() = okHttpUtil.createWebSocket(url, chatWebSocketListener).also {
    override fun execute(url: String) = okHttpUtil.createWebSocket(url, chatWebSocketListener).also {
        webSocket = it
    }

    override val chatWebSocketListener: IWebSocketListener = IWebSocketListener {
        when (it) {
            is SocketResults.OPEN -> {
            }
            is SocketResults.MESSAGE -> {
                it.text?.let {
                    val chat = transformToChat(it)
                    chatMessageRelay.accept(chat)
                }
            }
            is SocketResults.CLOSING -> {
            }
            is SocketResults.CLOSED -> {
            }
            is SocketResults.FAILURE -> {
            }
        }
    }

    override val infoLogRelay = chatWebSocketListener.infoLogRelay

    override val receiveSwitchRelay = chatWebSocketListener.receiveSwitchRelay

    override val chatMessageRelay = ReplayRelay.create<ChatInfo>()

    override fun transformToChat(text: String): ChatInfo {
        return Gson().fromJson(text, ChatInfo::class.java)
    }

    override fun transformToJson(chatInfo: ChatInfo): String {
        return Gson().toJson(chatInfo)
    }

    override fun send(chatInfo: ChatInfo) {
        val json = transformToJson(chatInfo)
        webSocket.send(json)
    }

    /**---------------------------------------------------------------------------------------------------------------------------
     * Server Socket
     * */

    override suspend fun executeServer(): String {
        val server = MockWebServer()
        server.enqueue(MockResponse().withWebSocketUpgrade(serverWebSocketListener))
        val hostName = server.hostName
        val port = server.port
        Timber.d("hostName = $hostName\nport = $port")
        return "ws://$hostName:$port"
    }

    override val serverWebSocketListener: IWebSocketListener = IWebSocketListener {
        when (it) {
            is SocketResults.OPEN -> {

            }
            is SocketResults.MESSAGE -> {
                val webSocket = it.webSocket
                it.text?.let {
                    val chat = transformToChat(it)
                    chat.text = "Server - ${chat.text}"
                    serverMessageRelay.accept(chat)
                    val json = transformToJson(chat)
                    webSocket.send(json)
                }
            }
            is SocketResults.CLOSING -> {
            }
            is SocketResults.CLOSED -> {
            }
            is SocketResults.FAILURE -> {
            }
        }
    }

    override val serverIntoLogRelay = serverWebSocketListener.infoLogRelay

    override val serverReceiveSwitchRelay = serverWebSocketListener.receiveSwitchRelay

    override val serverMessageRelay = ReplayRelay.create<ChatInfo>()

    override val coroutineContext: CoroutineContext
        get() = Job()

}