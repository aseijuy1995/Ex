package com.yujie.core_lib.model

/**
 * 媒體類型
 * @param ALL >> 全部
 * @param IMAGE >> 照片
 * @param AUDIO >> 音訊
 * @param VIDEO >> 影片
 * */
enum class MimeType(private val value: String) {
		ALL("all"),
		IMAGE("image/"),
		AUDIO("audio/"),
		VIDEO("video/");

		override fun toString() = value
}
