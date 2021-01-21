package edu.yujie.socketex

import android.net.Uri
import java.io.File

sealed class SocketViewEvent {

    data class SendText(val str: String) : SocketViewEvent()

    data class SendImg(val uris: List<Uri>) : SocketViewEvent()

    data class SendImgPath(val paths: List<String>) : SocketViewEvent()

    data class SendRecorder(val file: File) : SocketViewEvent()

}