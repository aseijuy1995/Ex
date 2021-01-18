package edu.yujie.socketex.bean

data class ChatItem(
    val id: Int,
    val name: String,
    val msg: String? = null,
    val time: String,
    val isOwner: Boolean,
    val imgBytes: List<ChatImgItem?>? = null,
    val recorderBytes: ByteArray? = null
)

data class ChatImgItem(val id: Int, val imgByteArray: ByteArray)