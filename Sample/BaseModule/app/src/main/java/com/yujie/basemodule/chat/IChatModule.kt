//package tw.north27.coachingapp.chat
//
//import com.jakewharton.rxrelay3.BehaviorRelay
//import com.jakewharton.rxrelay3.PublishRelay
//import okhttp3.Headers
//import okhttp3.WebSocket
//import tw.north27.coachingapp.model.result.ChatInfo
//
//interface IChatModule {
//
//    var webSocket: WebSocket?
//
//    fun execute(url: String): WebSocket
//
//    val chatWebSocketListener: IWebSocketListener
//
//    val switchInfoRelay: BehaviorRelay<Boolean>
//
//    val switchResultRelay: BehaviorRelay<Boolean>
//
//    val messageRelay: PublishRelay<ChatInfo>
//
//    fun transformToChat(text: String): ChatInfo
//
//    fun transformToJson(chat: ChatInfo): String
//
//    fun send(webSocket: WebSocket, chat: ChatInfo)
//
//    val infoRelay: PublishRelay<Headers>
//
//    /**---------------------------------------------------------------------------------------------------------------------------
//     * Server Socket
//     * */
//
//    suspend fun executeServer(): String
//
//    val serverWebSocketListener: IWebSocketListener
//
//    val serverSwitchInfoRelay: BehaviorRelay<Boolean>
//
//    val serverSwitchResultRelay: BehaviorRelay<Boolean>
//
//    val serverMessageRelay: PublishRelay<ChatInfo>
//
//    val serverInfoRelay: PublishRelay<Headers>
//
//}