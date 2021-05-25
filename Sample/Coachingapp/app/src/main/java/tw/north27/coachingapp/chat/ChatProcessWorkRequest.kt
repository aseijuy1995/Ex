package tw.north27.coachingapp.chat

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yujie.prefmodule.dataStore.*
import com.yujie.pushmodule.Notify
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.util.AppViewState
import tw.north27.coachingapp.util.ProcessLifeObs.Companion.APP_VIEW_STATE

class ChatProcessWorkRequest(val cxt: Context, params: WorkerParameters) : CoroutineWorker(cxt, params), KoinComponent {

    private val chatRepo by inject<IChatRepository>()

    companion object {
        val TAG = "ChatHandleWorker"
    }

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val appViewState = cxt.dataStore.getString(APP_VIEW_STATE).first()

            val account = cxt.userPref.getAccount().first()

            chatRepo.message.subscribe {
                //判定是否為自己發出訊息
                if (account != it.sender.account) {
                    when (appViewState) {
                        AppViewState.FOREGROUND.toString() -> {
                            sendNotification(it)
                        }
                        AppViewState.BACKGROUND.toString() -> {
                            sendNotification(it)
                        }
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