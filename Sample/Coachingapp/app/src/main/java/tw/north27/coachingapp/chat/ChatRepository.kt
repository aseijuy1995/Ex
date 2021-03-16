package tw.north27.coachingapp.chat

import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Headers
import okhttp3.WebSocket
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.ext.safeApiResults
import tw.north27.coachingapp.module.http.Results

class ChatRepository(val service: IApiService, val chatModule: IChatModule) : IChatRepository {

    override suspend fun loadChat(): Results<List<ChatInfo>> {
        return safeApiResults { service.getLoadChat() }
    }

    override fun send(webSocket: WebSocket, chat: ChatInfo) = chatModule.send(webSocket, chat)

    override var webSocket: WebSocket = chatModule.webSocket

    override fun execute(url: String): WebSocket = chatModule.execute(url)

    override val infoLogRelay: BehaviorRelay<Boolean> = chatModule.infoLogRelay

    override val receiveSwitchRelay: BehaviorRelay<Boolean> = chatModule.receiveSwitchRelay

    override val message: Observable<ChatInfo> = chatModule.messageRelay
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override val info: Observable<Headers> = chatModule.infoRelay
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override suspend fun executeServer(): String = chatModule.executeServer()

    override val serverInfoLogRelay: BehaviorRelay<Boolean> = chatModule.serverInfoLogRelay

    override val serverReceiveSwitchRelay: BehaviorRelay<Boolean> = chatModule.serverReceiveSwitchRelay

    override val serverMessage: Observable<ChatInfo> = chatModule.serverMessageRelay
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override val serverInfo: Observable<Headers> = chatModule.serverInfoRelay
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override suspend fun switchChatSound(chat: ChatInfo): Results<Boolean> {
        return safeApiResults { service.postSwitchChatSound(chat) }
    }

}