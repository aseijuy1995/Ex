package tw.north27.coachingapp.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.ui.CoachingActivity


//https://stackoverflow.com/questions/46956121/heads-up-notification-using-fcm

class FcmService : FirebaseMessagingService() {

    private lateinit var cxt: Context

    private lateinit var channelId: String

    companion object {
        private val NOTIFICATION_ID = 0


        private const val NOTIFICATION_TYPE = "notification_type"
        private const val TITLE = "title"
        private const val TEXT = "text"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("fcmToken = $token")
    }

    /**
     * notification
     * data
     * */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        cxt = applicationContext
        channelId = getString(R.string.default_notification_channel_id)
        Timber.d("from = ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            sendNotification(remoteMessage)

//            when (BaseApplication.appForegroundRelay.value) {
//                //foreground
//                true -> {
//                    Timber.d("appForegroundRelay = true, data = ${remoteMessage.data}")
//                }
//                //background
//                false -> {
//                    Timber.d("appForegroundRelay = false, data = ${remoteMessage.data}")
//
//                    sendNotification(remoteMessage)
//                }
//            }
        }
        Timber.d("remoteMessage.notification == null: ${remoteMessage.notification == null}")
        if (remoteMessage.notification != null) {
            Timber.d("remoteMessage.notification.body: ${remoteMessage.notification!!.title}")
            Timber.d("remoteMessage.notification.body: ${remoteMessage.notification!!.body}")

        }


        remoteMessage.notification?.let {
            Timber.d("notification = ${remoteMessage.notification.toString()}")
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {

        val type = remoteMessage.data[NOTIFICATION_TYPE]
        val title: String = remoteMessage.data[TITLE]!!
        val text: String = remoteMessage.data[TEXT]!!
//        when (type) {
//            NotificationType.ACTIVITY.toString() -> {
//                title = "ACTIVITY = ${remoteMessage.data[TITLE]!!}"
//                text = "ACTIVITY = ${remoteMessage.data[TEXT]!!}"
//
//            }
//            NotificationType.CHAT.toString() -> {
//                title = "CHAT = ${remoteMessage.data[TITLE]!!}"
//                text = "CHAT = ${remoteMessage.data[TEXT]!!}"
//            }
//            else -> {
//                title = "ELSE = ${remoteMessage.data[TITLE]!!}"
//                text = "ELSE = ${remoteMessage.data[TEXT]!!}"
//            }
//        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(this, channelId)
            .setAutoCancel(true)//設置此標誌將使其生效，因此，當用戶在面板中單擊它時，通知將自動取消。
            .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)//設置要顯示為此通知的徽章的圖標。
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)//設置通知類別。
            .setChannelId(channelId)//指定通知應在其上傳遞的渠道。
            .setColorized(true)//設置此通知是否應為彩色。
            .setContentIntent(
                PendingIntent.getActivity(
                    cxt,
                    0,
                    Intent(this, CoachingActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                    FLAG_ONE_SHOT
                )
            )//PendingIntent單擊通知時提供一個要發送的信息。
            .setContentText(text)//在標准通知中設置通知的文本（第二行）。
            .setContentTitle(title)//在標准通知中設置通知的標題（第一行）。
//            .setDefaults(Notification.DEFAULT_ALL)//設置將使用的默認通知選項。
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)//設置將使用的默認通知選項。
            .setOnlyAlertOnce(true)//如果您只希望在通知尚未顯示的情況下播放聲音，振動和聲音，請設置此標誌。
            .setPriority(NotificationCompat.PRIORITY_HIGH)//設置此通知的相對優先級。
            .setSmallIcon(R.mipmap.ic_pencil_logo)//設置要在通知佈局中使用的小圖標。
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)//集Notification.visibility。
            .setWhen(System.currentTimeMillis())//設置事件發生的時間。

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel = NotificationChannel(channelId, getString(R.string.default_notification_channel_id), NotificationManager.IMPORTANCE_HIGH).apply {
            val notificationChannel = NotificationChannel(channelId, getString(R.string.default_notification_channel_id), NotificationManager.IMPORTANCE_MAX).apply {
                setShowBadge(true)
                enableLights(true)//設置發佈到此頻道的通知是否應在支持該功能的設備上顯示通知燈。
                enableVibration(true)//設置發佈到此頻道的通知是否應該振動。
                vibrationPattern = longArrayOf(0L, 1000L, 500L, 1000L)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                importance = NotificationManager.IMPORTANCE_HIGH
            }
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        } else {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                notificationManager.notify(NOTIFICATION_ID, builder.build())
            } else {
                val notificationManagerCompat = NotificationManagerCompat.from(cxt)
                notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
            }
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()

    }

    enum class NotificationType {
        ACTIVITY, CHAT
    }
}