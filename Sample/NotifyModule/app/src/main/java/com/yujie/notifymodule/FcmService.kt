package com.yujie.notifymodule

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {

		private lateinit var cxt: Context

		private lateinit var channelId: String

		companion object {
				private val NOTIFICATION_ID = 0

				private const val TYPE = "type"
				private const val TITLE = "title"
				private const val TEXT = "text"
		}

		override fun onNewToken(token: String) {
				super.onNewToken(token)
		}

		override fun onMessageReceived(p0: RemoteMessage) {
				super.onMessageReceived(p0)
		}

		override fun onDeletedMessages() {
				super.onDeletedMessages()
		}

//		/**
//		 * data: foreground
//		 * notification: background
//		 * */
//		override fun onMessageReceived(remoteMessage: RemoteMessage) {
//				super.onMessageReceived(remoteMessage)
//				cxt = applicationContext
//				channelId = getString(R.string.default_notification_channel_id)
//				//data
//				if (remoteMessage.data.isNotEmpty()) {
//						val appViewState = runBlocking { cxt.dataStore.getString(APP_VIEW_STATE).first() }
//						when (appViewState) {
//								//foreground
//								AppViewState.FOREGROUND.toString() -> {
//										sendNotification(remoteMessage)
//								}
//								//background
//								AppViewState.BACKGROUND.toString() -> {
//										sendNotification(remoteMessage)
//								}
//						}
//				}
//				//notification
//				remoteMessage.notification?.let {
//						Timber.d("notification.title = ${remoteMessage.notification!!.title}")
//						Timber.d("notification.body = ${remoteMessage.notification!!.body}")
//				}
//		}
//
		private fun sendNotification(remoteMessage: RemoteMessage) {
				val type = remoteMessage.data[TYPE]
				val title: String = remoteMessage.data[TITLE]!!
				val text: String = remoteMessage.data[TEXT]!!
				val time = System.currentTimeMillis()
				val pendingIntent = PendingIntent.getActivity(
						cxt,
						0,
						Intent(this, HomeActivity::class.java)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
						PendingIntent.FLAG_ONE_SHOT
				)

				when (type) {
						Type.ACTIVITY.toString() -> {

						}

						Type.CHAT.toString() -> {

						}

						else -> {

						}
				}

				val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

				val builder = NotificationCompat.Builder(this, channelId)
						.setSmallIcon(R.mipmap.ic_pencil_logo)
						.setContentTitle(title)
						.setContentText(text)
						.setContentIntent(pendingIntent)//PendingIntent單擊通知時提供一個要發送的信息。
						.setWhen(time)
						.setAutoCancel(true)//設置此標誌將使其生效，因此，當用戶在面板中單擊它時，通知將自動取消。
						.setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)//設置要顯示為此通知的徽章的圖標。
						.setCategory(NotificationCompat.CATEGORY_MESSAGE)//設置通知類別。
						.setPriority(NotificationCompat.PRIORITY_HIGH)//設置此通知的相對優先級。
						.setDefaults(NotificationCompat.DEFAULT_ALL)//設置將使用的默認通知選項。
						.setChannelId(channelId)

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
						val notificationChannel = NotificationChannel(channelId, getString(R.string.default_notification_channel_id), NotificationManager.IMPORTANCE_HIGH)
						notificationManager.createNotificationChannel(notificationChannel)
						notificationManager.notify(NOTIFICATION_ID, builder.build())
				} else {
						val notificationManagerCompat = NotificationManagerCompat.from(cxt)
						notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
				}
		}

		override fun onDeletedMessages() {
				super.onDeletedMessages()
		}
}