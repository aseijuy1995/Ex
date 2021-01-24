package edu.yujie.socketex.bean

data class ChatItem(
    val id: Int,
    val name: String,
    val headerUrl: String? = null,
    val time: String,
    val textMsg: String? = null,
    val imgListMsg: List<ChatImg>? = null,
    val audioMsg: ByteArray? = null,
    val videoListMsg: List<ChatVideo>? = null,
    val sender: ChatSender
)

enum class ChatSender(val value: Int) {
    OWNER(1), OTHER(2)
}

data class ChatImg(
    val id: Int,
    val byteArray: ByteArray
)

data class ChatVideo(
    val id: Int,
    val byteArray: Byte
)