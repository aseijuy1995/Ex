package edu.yujie.socketex

import android.content.ContentResolver
import android.net.Uri
import java.io.File

sealed class SocketViewEvent {

    data class SendText(val str: String) : SocketViewEvent()

    data class SendImg(val uriList: List<Uri>) : SocketViewEvent()

    data class SendRecorder(val file: File) : SocketViewEvent()


}