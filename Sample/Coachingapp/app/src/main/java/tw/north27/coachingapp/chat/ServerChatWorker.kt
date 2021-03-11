package tw.north27.coachingapp.chat

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.coroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class ServerChatWorker(cxt: Context, params: WorkerParameters) : CoroutineWorker(cxt, params), KoinComponent {
    private val chatModule by inject<IChatModule>()

    companion object {
        val TAG = "ServerChatWorker"

        val SERVER_URL = "SERVER_URL"
    }

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val url = chatModule.executeServer()
            chatModule.serverIntoLogRelay.accept(true)
            val data = workDataOf(SERVER_URL to url)

            chatModule.serverMessageRelay

            Result.success(data)
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure()
        }
    }
}