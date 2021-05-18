package com.yujie.pushmodule.fcm

import com.google.firebase.messaging.FirebaseMessaging

object FirebaseMsg {

    var fcmToken: String? = null

    fun getInstance(complete: (fcmToken: String) -> Unit, failure: ((Exception) -> Unit)? = null) =
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                it.result?.takeIf { it.isNotEmpty() }?.let { fcmToken ->
                    this.fcmToken = fcmToken
                    complete.invoke(fcmToken)
                }
            }.addOnFailureListener {
                failure?.invoke(it)
            }
}