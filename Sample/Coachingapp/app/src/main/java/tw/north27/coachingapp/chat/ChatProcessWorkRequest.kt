package tw.north27.coachingapp.chat

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.ext.AppState
import tw.north27.coachingapp.ext.Notify
import tw.north27.coachingapp.ext.getAppState
import tw.north27.coachingapp.model.result.ChatInfo

class ChatProcessWorkRequest(val cxt: Context, params: WorkerParameters) : CoroutineWorker(cxt, params), KoinComponent {
    private val chatModule by inject<IChatModule>()

    companion object {
        val TAG = "ChatHandleWorker"
    }

    override suspend fun doWork(): Result = coroutineScope {

        try {
            chatModule.messageRelay.subscribe {
                when (getAppState().value) {
                    AppState.FOREGROUND -> {
                        sendNotification(it)
                    }
                    AppState.BACKGROUND -> {
                        sendNotification(it)
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure()
        }

    }

    private lateinit var channelId: String

    private fun sendNotification(chat: ChatInfo) {
        channelId = cxt.getString(R.string.default_notification_channel_id)

        Notify.with(cxt, channelId).builder {
            icon = R.mipmap.ic_pencil_logo
            title = chat.sender.name
            text = chat.text
//            pendingIntent = PendingIntent.getActivity()
        }.show()
    }
}