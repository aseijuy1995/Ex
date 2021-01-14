package edu.yujie.socketex

import android.net.Uri
import java.io.File

sealed class SocketViewEvent {

    object Idle : SocketViewEvent()

    data class SendText(val str: String) : SocketViewEvent()

    data class SendImg(val uris: List<Uri>) : SocketViewEvent()

    data class SendRecorder(val file: File) : SocketViewEvent()


}