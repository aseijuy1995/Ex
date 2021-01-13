package edu.yujie.socketex.socket

import edu.yujie.socketex.bean.ChatImgBean
import okio.ByteString

data class ChatBean(
    val id: Int,
    val name: String,
    val msg: String? = null,
    val isOneSelf: Boolean,
    val time: String,
    val imgBytes: List<ChatImgBean?>? = null,
    val recorderBytes: ByteArray? = null
)