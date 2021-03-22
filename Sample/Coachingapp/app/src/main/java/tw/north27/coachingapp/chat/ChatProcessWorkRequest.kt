package tw.north27.coachingapp.chat

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.ext.*
import tw.north27.coachingapp.ext.ProcessLifeObs.Companion.IS_APP_ON_FOREGROUND
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.protobuf.PREF_USER_NAME
import tw.north27.coachingapp.protobuf.UserPreferencesSerializer

class ChatProcessWorkRequest(val cxt: Context, params: WorkerParameters) : CoroutineWorker(cxt, params), KoinComponent {

    private val chatModule by inject<IChatModule>()

    private val dataStore = cxt.createDataStorePref()

    private val userDataStore = cxt.createDataStoreProto(PREF_USER_NAME, UserPreferencesSerializer)

    companion object {
        val TAG = "ChatHandleWorker"
    }

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val isAppOnForeground = dataStore.getBoolean(IS_APP_ON_FOREGROUND).first()

            val account = userDataStore.getValue { it.account }.first()

            chatModule.messageRelay.subscribe {
                //判定是否為自己發出訊息
                if (account != it.sender.account) {
                    when (isAppOnForeground) {
                        true -> {
                            sendNotification(it)
                        }
                        false -> {
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