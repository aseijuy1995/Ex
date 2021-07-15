package com.yujie.utilmodule.media.model

/**
 * 媒體文件夾
 * @param name >> 文件名稱
 * @param filePath >> 文件路徑
 * */
data class MediaFolder(
		val name: String,
		val filePath: String
) {
		val mediaList = mutableListOf<Media>()
}