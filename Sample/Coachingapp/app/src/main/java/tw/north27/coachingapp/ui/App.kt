package tw.north27.coachingapp.ui

import com.yujie.utilmodule.base.BaseApplication
import com.yujie.utilmodule.ext.startKoinModules
import tw.north27.coachingapp.consts.httpModules
import tw.north27.coachingapp.consts.moduleModules
import tw.north27.coachingapp.consts.repoModules
import tw.north27.coachingapp.consts.viewModelModules

class App : BaseApplication() {

    override fun onCreate() {
//        if (BuildConfig.DEBUG) startStrictMode()
        super.onCreate()
        startKoinModules(
            httpModules,
            viewModelModules,
            repoModules,
            moduleModules,
            //
//            utilModules,

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

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

}