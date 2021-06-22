package com.yujie.pushmodule.fcm

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yujie.pushmodule.R

abstract class FirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        FirebaseMsg.fcmToken = token
    }

    private lateinit var cxt: Context

    private lateinit var channelId: String

    abstract fun receiveData(map: Map<String, String>)

    abstract fun receiveNotification(notification: RemoteMessage.Notification)

    /**
     * data: foreground
     * notification: background
     * */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        cxt = applicationContext
        channelId = getString(R.string.default_notification_channel_id)
        //data
        if (remoteMessage.data.isNotEmpty()) {
            receiveData(remoteMessage.data)
        }
        //notification
        remoteMessage.notification?.let { receiveNotification(it) }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}