package tw.north27.coachingapp.model.result

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
    var read: ChatRead
)

enum class ChatType {
    TEXT, IMAGE, MULTIPLE_IMAGE, AUDIO, VIDEO
}

data class ChatImage(
    val id: Int,
    val url: String
//    val byteArray: ByteArray
)

data class ChatAudio(
    val id: Int,
    val url: String,
//    val byteAttay: ByteArray,
    val time: Int
)

data class ChatVideo(
    val id: Int,
    val url: String,
//    val byteArray: Byte
    val time: Int
)

enum class ChatRead {
    HAVE_READ, UN_READ
}