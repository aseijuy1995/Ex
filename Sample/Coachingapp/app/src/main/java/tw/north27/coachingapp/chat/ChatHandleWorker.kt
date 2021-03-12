package tw.north27.coachingapp.chat

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.ui.CoachingActivity

class ChatHandleWorker(val cxt: Context, params: WorkerParameters) : CoroutineWorker(cxt, params), KoinComponent {
    private val chatModule by inject<IChatModule>()

    companion object {
        val TAG = "ChatHandleWorker"

        private val NOTIFICATION_ID = 0
    }

    override suspend fun doWork(): Result = coroutineScope {

        try {


            Result.success()
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure()
        }

    }

    private lateinit var channelId: String

    private fun sendNotification() {
        channelId = cxt.getString(R.string.default_notification_channel_id)

//        val type = remoteMessage.data[FcmService.TYPE]
//        val title: String = remoteMessage.data[FcmService.TITLE]!!
//        val text: String = remoteMessage.data[FcmService.TEXT]!!
        val time = System.currentTimeMillis()
        val pendingIntent = PendingIntent.getActivity(
            cxt,
            0,
            Intent(cxt, CoachingActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
            PendingIntent.FLAG_ONE_SHOT
        )

        val notificationManager = cxt.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_pencil_logo)
            .setContentTitle("title")
            .setContentText("text")
            .setContentIntent(pendingIntent)//PendingIntent單擊通知時提供一個要發送的信息。
            .setWhen(time)
            .setAutoCancel(true)//設置此標誌將使其生效，因此，當用戶在面板中單擊它時，通知將自動取消。
            .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)//設置要顯示為此通知的徽章的圖標。
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)//設置通知類別。
            .setPriority(NotificationCompat.PRIORITY_HIGH)//設置此通知的相對優先級。
            .setDefaults(NotificationCompat.DEFAULT_ALL)//設置將使用的默認通知選項。
            .setChannelId(channelId)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, cxt.getString(R.string.default_notification_channel_id), NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        } else {
            val notificationManagerCompat = NotificationManagerCompat.from(cxt)
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
        }
    }
}