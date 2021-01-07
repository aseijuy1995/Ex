package edu.yujie.socketex

import android.content.ContentResolver

sealed class SocketViewEvent {

    data class SendClick(val str: String?) : SocketViewEvent()

    object RecordClick : SocketViewEvent()

    data class ReCordStart(val contentResolver: ContentResolver) : SocketViewEvent()

    object ReCordStop : SocketViewEvent()
}