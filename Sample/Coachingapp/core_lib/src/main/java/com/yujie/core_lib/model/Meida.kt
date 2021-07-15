package com.yujie.core_lib.model

//image
const val MEDIA_ALBUM_IMAGE = "MEDIA_ALBUM_IMAGE"

//audio
const val MEDIA_ALBUM_AUDIO = "MEDIA_ALBUM_AUDIO"

//video
const val MEDIA_ALBUM_VIDEO = "MEDIA_ALBUM_VIDEO"

/**
 * 媒體資訊
 * @param
 * */
data class Media(
		val id: Int,
		val mimeType: String,
		val defaultSortOrder: String,
		val relativePath: String,
		val displayName: String,
		val size: Long,
		val height: Int,
		val width: Int,
)