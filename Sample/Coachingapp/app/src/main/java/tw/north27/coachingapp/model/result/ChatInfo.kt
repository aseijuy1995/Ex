package tw.north27.coachingapp.model.result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatInfo(
    val id: Int,
    val sender: UserInfo,//發送者資訊
    val recipient: UserInfo,//接收者資訊
    val sendTime: String,//發送時間
    var chatType: ChatType,//種類
    var text: String? = null,//文字
    var image: List<ChatImage>? = null,//圖片
    var audios: ChatAudio? = null,//音訊
    var videos: List<ChatVideo>? = null,//影片
    var read: ChatRead,
    var unReadCount: Int,//未讀數量
    var isSound: Boolean,//聲音開關
    var isSwipe: Boolean = false//滑動開關
) : Parcelable

enum class ChatType {
    TEXT, IMAGE, MULTIPLE_IMAGE, AUDIO, VIDEO
}

@Parcelize
data class ChatImage(
    val id: Int,
    val url: String
//    val byteArray: ByteArray
) : Parcelable

@Parcelize
data class ChatAudio(
    val id: Int,
    val url: String,
//    val byteAttay: ByteArray,
    val time: Int
) : Parcelable

@Parcelize
data class ChatVideo(
    val id: Int,
    val url: String,
//    val byteArray: Byte
    val time: Int
) : Parcelable

enum class ChatRead {
    HAVE_READ, UN_READ
}