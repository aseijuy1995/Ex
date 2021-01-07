package edu.yujie.socketex

import okio.ByteString

data class ChatBean(
    val id: Int,
    val name: String,
    val msg: String? = null,
    val isOneSelf: Boolean,
    val time: String,
    val imgByte: ByteString? = null,
    val recordByte: ByteString? = null
)