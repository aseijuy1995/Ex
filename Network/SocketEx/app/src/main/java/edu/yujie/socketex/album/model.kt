package edu.yujie.socketex.album

import java.io.Serializable

data class AlbumItem(
    val name: String,
    val folder: String,
    val coverImagePath: String
){
    val mediaList = mutableListOf<Media>()
}

class AlbumSetting : Serializable {
    var mimeType = MimeType.ALL
    var multipleSelection: Boolean = false
    var maxSelection = 10
    var imageMaxSize: Long? = null
    var videoMaxSecond: Int? = null
    var videoMinSecond: Int? = null
}

data class Media(
    val path: String,
    var name: String?,
    var album: String?,
    var size: Long?,
    var datetime: Long?,
    var duration: Long?,
    var width: Int?,
    var height: Int?
)

//data class Media(
//    val id: String,
//    val data: String,
//    val dataAdded: String,
//    val mediaType: String,
//    val mimeType: String,
//    val duration: Int,
//    val title: String,
//    val size: Int
//)

enum class MimeType(private val typeName: String) {
    ALL("all"),
    IMAGE("image"),
    VIDEO("video");

    override fun toString() = typeName
}