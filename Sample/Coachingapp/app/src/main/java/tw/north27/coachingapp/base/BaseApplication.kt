package tw.north27.coachingapp.base

import android.app.Application
import android.net.ConnectivityManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jakewharton.rxrelay3.BehaviorRelay
import com.yujie.utilmodule.ext.startTimberLogger
import tw.north27.coachingapp.chat.ChatProcessWorkRequest
import tw.north27.coachingapp.chat.ClientChatWorker
import tw.north27.coachingapp.chat.ServerChatWorker
import tw.north27.coachingapp.consts.modelModules
import tw.north27.coachingapp.consts.repoModules
import tw.north27.coachingapp.consts.utilModules
import tw.north27.coachingapp.consts.viewModelModules
import tw.north27.coachingapp.ext.startProcessLifeObs
import tw.north27.coachingapp.ext2.startKoinModules
import tw.north27.coachingapp.ext2.startNetworkReceive

class BaseApplication : Application() {

    companion object {
        lateinit var appForegroundRelay: BehaviorRelay<Boolean>
    }

    override fun onCreate() {
//        if (BuildConfig.DEBUG) startStrictMode()
        super.onCreate()
        startProcessLifeObs()
        startTimberLogger()


        startKoinModules(utilModules, modelModules, repoModules, viewModelModules)

        startChatManager()

        applicationContext.startNetworkReceive(object : ConnectivityManager.NetworkCallback() {


        })

    }

    private fun startChatManager() {
        //server chat
        val serverWorkerRequest = OneTimeWorkRequestBuilder<ServerChatWorker>()
            .addTag(ServerChatWorker.TAG)
            .build()

        val chatWorkerRequest = OneTimeWorkRequestBuilder<ClientChatWorker>()
            //            .setInputData(workDataOf("WEBSOCKET_URL") to WS_URL)
            .addTag(ClientChatWorker.TAG)
            .build()

        val chatProcessWorkRequest = OneTimeWorkRequestBuilder<ChatProcessWorkRequest>()
            .addTag(ChatProcessWorkRequest.TAG)
            .build()

        val workerManager = WorkManager.getInstance(applicationContext)

        workerManager
            .beginWith(serverWorkerRequest)
            .then(chatWorkerRequest)
            .then(chatProcessWorkRequest)
            .enqueue()
    }

}