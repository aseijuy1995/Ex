package tw.north27.coachingapp.chat

import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.core.Observable
import okhttp3.Headers
import okhttp3.WebSocket
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.Results

interface IChatRepository {

    suspend fun loadChat(): Results<List<ChatInfo>>

    var webSocket: WebSocket?

    fun execute(url: String): WebSocket

    val switchInfoRelay: BehaviorRelay<Boolean>

    val switchResultRelay: BehaviorRelay<Boolean>

    val message: Observable<ChatInfo>

    fun send(webSocket: WebSocket, chat: ChatInfo)

    val info: Observable<Headers>

    //---------------------------------------------------------------------------------------------------------------------------

    suspend fun loadChatList(chat: ChatInfo): Results<List<ChatInfo>>

    /**---------------------------------------------------------------------------------------------------------------------------
     * Server Socket
     * */

    suspend fun executeServer(): String

    val serverSwitchInfoRelay: BehaviorRelay<Boolean>

    val serverSwitchResultRelay: BehaviorRelay<Boolean>

    val serverMessage: Observable<ChatInfo>

    val serverInfo: Observable<Headers>

    /**---------------------------------------------------------------------------------------------------------------------------*/

    suspend fun switchChatSound(chat: ChatInfo): Results<Boolean>

    suspend fun deleteChatRoom(chat: ChatInfo): Results<Boolean>


}