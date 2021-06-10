package tw.north27.coachingapp.push

import com.google.firebase.messaging.RemoteMessage
import com.yujie.pushmodule.fcm.FirebaseService

class FcmService : FirebaseService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun receiveData(map: Map<String, String>) {
        println("receiveData = $map")
    }

    override fun receiveNotification(notification: RemoteMessage.Notification) {
        println("receiveNotification = notification.title = ${notification.title}")
        println("receiveNotification = notification.body = ${notification.body}")
    }
}