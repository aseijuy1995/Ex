package edu.yujie.socketex.bean

const val ALL_MEDIA_ALBUM_NAME = "ALL_MEDIA_ALBUM_NAME"

data class MediaAlbumItem(
    val name: String,
    val folder: String,
    val covertPath: String
) {
    val mediaList = mutableListOf<Media>()
}

data class MediaSetting(
    val mimeType: MimeType = MimeType.ALL,
    val isMultipleSelection: Boolean = false,
    val maxSelectionCount: Int = 6,
    val imageMaxSize: Long? = 0L,//圖片大小
    val videoMaxDuration: Int? = null,//視頻最大時長
    val videoMinDuration: Int? = null,//視頻最小時長
    val audioMaxDuration: Int? = null,//音訊最大時長
    val audioMinDuration: Int? = null//音訊最小時長
)

data class Media(
    val id: Int,
    val bucketDisplayName: String,
    val bucketId: Int,
    val dateModified: Long,
    val displayName: String,
    val duration: Long,
    val height: Int,
    val mimeType: String,
    val data: String,
    val size: Long,
    val title: String,
    val width: Int
)

enum class MimeType(private val typeName: String) {
    ALL("all"),
    IMAGE("image"),
    VIDEO("video");

    override fun toString() = typeName
}

