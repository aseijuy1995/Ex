package tw.north27.coachingapp.chat

import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.core.Observable
import okhttp3.Headers
import okhttp3.WebSocket
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.Results

interface IChatRepository {

    suspend fun loadChat(): Results<List<ChatInfo>>

    var webSocket: WebSocket

    fun execute(url: String): WebSocket

    val infoLogRelay: BehaviorRelay<Boolean>

    val receiveSwitchRelay: BehaviorRelay<Boolean>

    val message: Observable<ChatInfo>

    fun send(webSocket: WebSocket, chatInfo: ChatInfo)

    val info: Observable<Headers>

    /**---------------------------------------------------------------------------------------------------------------------------
     * Server Socket
     * */

    suspend fun executeServer(): String

    val serverInfoLogRelay: BehaviorRelay<Boolean>

    val serverReceiveSwitchRelay: BehaviorRelay<Boolean>

    val serverMessage: Observable<ChatInfo>

    val serverInfo: Observable<Headers>

}