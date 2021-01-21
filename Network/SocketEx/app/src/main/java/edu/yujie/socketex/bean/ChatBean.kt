package edu.yujie.socketex.bean

data class ChatItem(
    val id: Int,
    val name: String,
    val headUrl: String? = null,
    val time: String,
    val isOwner: Boolean,
    //
    val msg: String? = null,
    val chatImgList: List<ChatImg>? = null,
    val recorderBytes: ByteArray? = null
)

data class ChatImg(
    val id: Int,
    val byteArray: ByteArray
)