package tw.north27.coachingapp.push

import com.google.firebase.messaging.RemoteMessage
import com.yujie.pushmodule.fcm.FirebaseService

class FcmService : FirebaseService() {

    override fun receiveData(map: Map<String, String>) {

    }

    override fun receiveNotification(notification: RemoteMessage.Notification) {

    }

}