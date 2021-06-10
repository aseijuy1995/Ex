//package tw.north27.coachingapp.chat
//
//import android.content.Context
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import androidx.work.workDataOf
//import com.yujie.utilmodule.util.logE
//import kotlinx.coroutines.coroutineScope
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
//
//class ServerChatWorker(cxt: Context, params: WorkerParameters) : CoroutineWorker(cxt, params), KoinComponent {
//    private val chatRepo by inject<IChatRepository>()
//
//    companion object {
//        val TAG = "ServerChatWorker"
//
//        val SERVER_URL = "SERVER_URL"
//    }
//
//    override suspend fun doWork(): Result = coroutineScope {
//        try {
//            val url = chatRepo.executeServer()
//            val data = workDataOf(SERVER_URL to url)
//            Result.success(data)
//        } catch (e: Exception) {
//            logE(e)
//            Result.failure()
//        }
//    }
//}