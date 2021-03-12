package tw.north27.coachingapp.chat

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class ChatWorker(cxt: Context, params: WorkerParameters) : CoroutineWorker(cxt, params), KoinComponent {
    private val chatModule by inject<IChatModule>()

    companion object {
        val TAG = "ChatWorker"
    }

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val url = inputData.getString(ServerChatWorker.SERVER_URL) ?: return@coroutineScope Result.retry()
            chatModule.infoLogRelay.accept(true)
            chatModule.execute(url)
            Result.success()
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure()
        }
    }
}