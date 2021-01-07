package edu.yujie.socketex

sealed class SocketViewEvent {

    data class SendClick(val str: String?) : SocketViewEvent()
}