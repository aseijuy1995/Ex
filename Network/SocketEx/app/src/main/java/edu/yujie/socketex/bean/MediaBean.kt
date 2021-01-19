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
    val maxCount: Int? = null,
    val maxSecond: Int? = null,
    val minSecond: Int? = null
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
    val relativePath: String,
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

