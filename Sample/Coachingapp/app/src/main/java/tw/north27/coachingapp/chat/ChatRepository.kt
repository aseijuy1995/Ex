package tw.north27.coachingapp.chat

import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Headers
import okhttp3.WebSocket
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.ext.safeApiResults
import tw.north27.coachingapp.module.http.Results
import java.util.concurrent.TimeUnit


class ChatRepository(val service: IApiService, val chatModule: IChatModule) : IChatRepository {

    override suspend fun loadChat(): Results<List<ChatInfo>> {
        return safeApiResults { service.getLoadChat() }
    }

    override fun send(webSocket: WebSocket, chat: ChatInfo) = chatModule.send(webSocket, chat)

    override var webSocket: WebSocket? = chatModule.webSocket

    override fun execute(url: String): WebSocket = chatModule.execute(url).also {
        webSocket = it
    }

    override val switchInfoRelay: BehaviorRelay<Boolean> = chatModule.switchInfoRelay

    override val switchResultRelay: BehaviorRelay<Boolean> = chatModule.switchResultRelay

    override val message: Observable<ChatInfo> = chatModule.messageRelay
        .subscribeOn(Schedulers.io())
        /**
         * 測試接收延遲
         * */
        .delay(1500, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())

    override val info: Observable<Headers> = chatModule.infoRelay
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override suspend fun loadChatListFromChat(chat: ChatInfo): Results<List<ChatInfo>> {
        return safeApiResults { service.getChatListFromChat(chat) }
    }

    override suspend fun executeServer(): String = chatModule.executeServer()

    override val serverSwitchInfoRelay: BehaviorRelay<Boolean> = chatModule.serverSwitchInfoRelay

    override val serverSwitchResultRelay: BehaviorRelay<Boolean> = chatModule.serverSwitchResultRelay

    override val serverMessage: Observable<ChatInfo> = chatModule.serverMessageRelay
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override val serverInfo: Observable<Headers> = chatModule.serverInfoRelay
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override suspend fun switchChatSound(chat: ChatInfo): Results<Boolean> {
        return safeApiResults { service.postSwitchChatSound(chat) }
    }

    override suspend fun deleteChatRoom(chat: ChatInfo): Results<Boolean> {
        return safeApiResults { service.deleteChatRoom(chat) }
    }

}