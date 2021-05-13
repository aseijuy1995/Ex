package tw.north27.coachingapp.ext2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import tw.north27.coachingapp.R

class Notify {

    companion object {
        fun with(cxt: Context, channelId: String): Builder {
            return Builder(cxt, channelId)
        }
    }

    class Builder(private val cxt: Context, private val channelId: String) {
        var icon = R.mipmap.ic_launcher
        var title: String? = null
        var text: String? = null
        var pendingIntent: PendingIntent? = null//PendingIntent單擊通知時提供一個要發送的信息。
        var time: Long = System.currentTimeMillis()
        var isAutoCancel: Boolean = true//設置此標誌將使其生效，因此，當用戶在面板中單擊它時，通知將自動取消。
        var badgeIconType: Int = NotificationCompat.BADGE_ICON_NONE//設置要顯示為此通知的徽章的圖標。
        var category: String = NotificationCompat.CATEGORY_MESSAGE//設置通知類別。
        var priority: Int = NotificationCompat.PRIORITY_HIGH//設置此通知的相對優先級。
        var defaults: Int = NotificationCompat.DEFAULT_ALL//設置將使用的默認通知選項。
        var notifyId: Int = 0

        //
        lateinit var notification: Notification

        fun builder(builder: Notify.Builder.() -> Unit): Builder {
            builder(this)
            notification = NotificationCompat.Builder(cxt, channelId!!)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setWhen(time)
                .setAutoCancel(isAutoCancel)
                .setBadgeIconType(badgeIconType)
                .setCategory(category)
                .setPriority(priority)
                .setDefaults(defaults)
                .setChannelId(channelId)
                .build()
            return this
        }

        fun show() {
            val notificationManager = cxt.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(notificationChannel)
                notificationManager.notify(notifyId, notification)
            } else {
                val notificationManagerCompat = NotificationManagerCompat.from(cxt)
                notificationManagerCompat.notify(notifyId, notification)
            }
        }
    }

}
