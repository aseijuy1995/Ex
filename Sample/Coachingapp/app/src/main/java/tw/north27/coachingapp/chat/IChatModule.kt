package tw.north27.coachingapp.chat

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.ReplayRelay
import okhttp3.Headers
import okhttp3.WebSocket
import tw.north27.coachingapp.model.result.ChatInfo

interface IChatModule {

    var webSocket: WebSocket

    fun execute(url: String): WebSocket

    val chatWebSocketListener: IWebSocketListener

    val infoLogRelay: BehaviorRelay<Boolean>

    val receiveSwitchRelay: BehaviorRelay<Boolean>

    val messageRelay: ReplayRelay<ChatInfo>

    fun transformToChat(text: String): ChatInfo

    fun transformToJson(chatInfo: ChatInfo): String

    fun send(webSocket: WebSocket, chatInfo: ChatInfo)

    val infoRelay: ReplayRelay<Headers>

    /**---------------------------------------------------------------------------------------------------------------------------
     * Server Socket
     * */

    suspend fun executeServer(): String

    val serverWebSocketListener: IWebSocketListener

    val serverInfoLogRelay: BehaviorRelay<Boolean>

    val serverReceiveSwitchRelay: BehaviorRelay<Boolean>

    val serverMessageRelay: ReplayRelay<ChatInfo>

    val serverInfoRelay: ReplayRelay<Headers>

}