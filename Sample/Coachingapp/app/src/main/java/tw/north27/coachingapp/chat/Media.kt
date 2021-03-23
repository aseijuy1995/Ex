package com.yujie.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//all
const val MEDIA_ALBUM_ALL = "MEDIA_ALBUM_ALL"

//audio
const val MEDIA_ALBUM_AUDIO = "MEDIA_ALBUM_AUDIO"

//video
const val MEDIA_ALBUM_VIDEO = "MEDIA_ALBUM_VIDEO"

/**
 * 設定媒體
 * */
data class MediaSetting(
    val mimeType: MimeType = MimeType.ALL,
    val isMultipleChoice: Boolean = false,//多選
    val maxCount: Int = 6,//最多數量
    val imgMaxSize: Long? = 0L,//圖片最大
    //
    val videoMaxDuration: Int? = null,//視頻最大時長
    val videoMinDuration: Int? = null,//視頻最小時長
    val audioMaxDuration: Int? = null,//音訊最大時長
    val audioMinDuration: Int? = null//音訊最小時長
)

/**
 * 媒體類別
 * */
enum class MimeType(private val mimeType: String) {
    ALL("all"),
    AUDIO("audio"),
    IMAGES("image"),
    VIDEO("video");

    override fun toString() = mimeType
}

/**
 * 專輯項目
 * */
data class MediaAlbum(
    val albumName: String,
    val folder: String,
    val file: String
) {
    val mediaList = mutableListOf<Media>()
}

@Parcelize
data class Media(
    val bucketDisplayName: String,
    val bucketId: Int,
    val data: String,
    val dateModified: Long,
    val displayName: String,
    val duration: Long,
    val height: Int,
    val mimeType: String,
    val size: Long,
    val title: String,
    val width: Int,
    val id: Int,
    //
    var isSelect: Boolean = false
) : Parcelable

