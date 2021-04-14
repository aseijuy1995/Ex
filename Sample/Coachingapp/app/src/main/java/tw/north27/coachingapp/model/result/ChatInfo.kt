package tw.north27.coachingapp.model.result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatInfo(
    val id: Int,
    var sender: UserInfo,//發送者資訊
    var recipient: UserInfo,//接收者資訊
    val sendTime: String,//發送時間
    var chatType: ChatType,//種類
    var text: String? = null,//文字
    var image: List<ChatImage>? = null,//圖片
    var audios: List<ChatAudio>? = null,//音訊
    var videos: List<ChatVideo>? = null,//影片
    var read: ChatRead,
    var unReadCount: Int = 0,//未讀數量
    var isSound: Boolean = true,//聲音開關

) : Parcelable

enum class ChatType {
    TEXT, IMAGE, MULTIPLE_IMAGE, AUDIO, VIDEO
}

@Parcelize
data class ChatImage(
    val id: Int,
    val url: String? = null,
    val byteArray: ByteArray? = null
) : Parcelable

@Parcelize
data class ChatAudio(
    val id: Int,
    val url: String
) : Parcelable

@Parcelize
data class ChatVideo(
    val id: Int,
    val url: String? = null,
//    val byteArray: ByteArray? = null,
    val time: Int
) : Parcelable

enum class ChatRead {
    HAVE_READ, UN_READ
}