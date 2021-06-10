package tw.north27.coachingapp.ui

import com.yujie.utilmodule.base.BaseApplication
import com.yujie.utilmodule.ext.startKoinModules
import com.yujie.utilmodule.util.startStrictMode
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.consts.viewModelModules

class App : BaseApplication() {

    override fun onCreate() {
        if (BuildConfig.DEBUG) startStrictMode()
        super.onCreate()
        startKoinModules(
            viewModelModules
            //
//            utilModules,
//            modelModules,
//            repoModules,
        )

//        startChatManager()
//
//        applicationContext.startNetworkReceive(object : ConnectivityManager.NetworkCallback() {
//
//
//        })

    }

//    private fun startChatManager() {
//        //server chat
//        val serverWorkerRequest = OneTimeWorkRequestBuilder<ServerChatWorker>()
//            .addTag(ServerChatWorker.TAG)
//            .build()
//
//        val chatWorkerRequest = OneTimeWorkRequestBuilder<ClientChatWorker>()
//            //            .setInputData(workDataOf("WEBSOCKET_URL") to WS_URL)
//            .addTag(ClientChatWorker.TAG)
//            .build()
//
//        val chatProcessWorkRequest = OneTimeWorkRequestBuilder<ChatProcessWorkRequest>()
//            .addTag(ChatProcessWorkRequest.TAG)
//            .build()
//
//        val workerManager = WorkManager.getInstance(applicationContext)
//
//        workerManager
//            .beginWith(serverWorkerRequest)
//            .then(chatWorkerRequest)
//            .then(chatProcessWorkRequest)
//            .enqueue()
//    }

}