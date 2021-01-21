package edu.yujie.socketex.bean

import android.os.Parcel
import android.os.Parcelable

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
    val width: Int,
    //
    var isSelect: Boolean? = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
    }

    companion object CREATOR : Parcelable.Creator<Media> {
        override fun createFromParcel(parcel: Parcel): Media {
            return Media(parcel)
        }

        override fun newArray(size: Int): Array<Media?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeInt(id)
        parcel?.writeString(bucketDisplayName)
        parcel?.writeInt(bucketId)
        parcel?.writeLong(dateModified)
        parcel?.writeString(displayName)
        parcel?.writeLong(duration)
        parcel?.writeInt(height)
        parcel?.writeString(mimeType)
        parcel?.writeString(data)
        parcel?.writeLong(size)
        parcel?.writeString(title)
        parcel?.writeInt(width)
    }
}

enum class MimeType(private val typeName: String) {
    ALL("all"),
    IMAGE("image"),
    VIDEO("video");

    override fun toString() = typeName
}

