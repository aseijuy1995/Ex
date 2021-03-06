package edu.yujie.socketex

import edu.yujie.socketex.bean.ChatItem

sealed class SocketState {

    object Idle : SocketState()

    data class onServerOpen(val msg: String) : SocketState()

    data class onServerMessage(val msg: String) : SocketState()

    data class onServerClosing(val msg: String) : SocketState()

    data class onServerClosed(val msg: String) : SocketState()

    data class onServerFailure(val msg: String) : SocketState()

    data class onClientOpen(val msg: String) : SocketState()

    data class onClientMessage(val msg: String, val chatItem: ChatItem) : SocketState()

    data class onClientClosing(val msg: String) : SocketState()

    data class onClientClosed(val msg: String) : SocketState()

    data class onClientFailure(val msg: String) : SocketState()

    //
    data class ShowChat(val chatItem: ChatItem) : SocketState()

    data class RecordState(val state: Boolean) : SocketState()

    object RecordAudioData : SocketState()

    data class RecordError(val msg: String) : SocketState()

}