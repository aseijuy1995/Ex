package com.yujie.utilmodule.media.model

/**
 * MediaAlbum設定
 * @param mimeType >> 媒體類型
 * @param minSec >> (音訊/影片)最少秒數
 * @param maxSec >> (音訊/影片)最長秒數
 * @param minSize >> (照片)最小大小
 * @param maxSize >> (照片)最大大小
 * */
data class MediaSetting(
		val mimeType: MimeType = MimeType.ALL,
		val minSec: Int? = null,
		val maxSec: Int? = null,
		val minSize: Long? = null,
		val maxSize: Long? = null,
)