package tw.north27.coachingapp.base

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.chat.ChatProcessWorkRequest
import tw.north27.coachingapp.chat.ClientChatWorker
import tw.north27.coachingapp.chat.ServerChatWorker
import tw.north27.coachingapp.consts.modelModules
import tw.north27.coachingapp.consts.repoModules
import tw.north27.coachingapp.consts.utilModules
import tw.north27.coachingapp.consts.viewModelModules
import tw.north27.coachingapp.ext.ProcessLifeObs
import tw.north27.coachingapp.ext.startKoinModules
import tw.north27.coachingapp.ext.startStrictMode
import tw.north27.coachingapp.ext.startTimberLogger

class BaseApplication : Application() {

    companion object {
        lateinit var appForegroundRelay: BehaviorRelay<Boolean>
    }

    override fun onCreate() {
        if (BuildConfig.DEBUG) startStrictMode()
        super.onCreate()
        if (BuildConfig.DEBUG) startTimberLogger()

        ProcessLifeObs.appStateRelay.subscribeBy { Timber.d("AppState = $it") }

        startKoinModules(utilModules, modelModules, repoModules, viewModelModules)

        //server chat
        val serverWorkerRequest = OneTimeWorkRequestBuilder<ServerChatWorker>()
            .addTag(ServerChatWorker.TAG)
            .build()

        val chatWorkerRequest = OneTimeWorkRequestBuilder<ClientChatWorker>()
//            .setInputData(workDataOf("WEBSOCKET_URL") to WS_URL)
            .addTag(ClientChatWorker.TAG)
            .build()

        val chatHandleWorkerRequest = OneTimeWorkRequestBuilder<ChatProcessWorkRequest>()
            .addTag(ChatProcessWorkRequest.TAG)
            .build()

        val workerManager = WorkManager.getInstance(applicationContext)

        workerManager
            .beginWith(serverWorkerRequest)
            .then(chatWorkerRequest)
            .then(chatHandleWorkerRequest)
            .enqueue()
    }

}