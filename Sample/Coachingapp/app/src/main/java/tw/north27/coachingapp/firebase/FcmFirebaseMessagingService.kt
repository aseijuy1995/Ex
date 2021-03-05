package tw.north27.coachingapp.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.ui.CoachingActivity

class FcmFirebaseMessagingService : FirebaseMessagingService() {

    lateinit var cxt: Context

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Timber.d("fcmToken = $p0")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        applicationContext.also { cxt = it }

        Timber.d("from = ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("data = ${remoteMessage.data}")

            val intent = Intent(this, CoachingActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }

            NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id)).apply {
                setSmallIcon(R.mipmap.ic_pencil_logo)
                    .setContentTitle("Title")
                    .setContentText("Text")
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(PendingIntent.getActivity(cxt, 0, intent, PendingIntent.FLAG_ONE_SHOT))
                    .setDefaults(Notification.DEFAULT_ALL)

                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(NotificationChannel(getString(R.string.default_notification_channel_id), getString(R.string.default_notification_channel_id), NotificationManager.IMPORTANCE_HIGH))
                    setChannelId(getString(R.string.default_notification_channel_id))
                }
                notificationManager.notify(0, build())
            }
        }


        //background
        remoteMessage.notification?.let {
            Timber.d("notification = ${remoteMessage.notification}")
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()

    }
}