package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName


/**
 * 請求提問室列表
 * @param clientId >> 用戶Id
 * @param askId >> 最新提問條Id
 * */
data class AskRoomRequest(
    @SerializedName("client_id") val clientId: String,
    @SerializedName("ask_id") val askId: Long? = null,
)

/**
 * 請求提問室列表詳細資訊
 * @param roomId >> 房間id
 * @param clientId >> 用戶Id
 * @param index >> 索引
 * @param num >> 筆數
 * */
data class AskRoomInfoRequest(
    @SerializedName("room_id") val roomId: Long,
    @SerializedName("client_id") val clientId: String,
    @SerializedName("index") val index: Int,
    @SerializedName("num") val num: Int,
)

/**
 * 提問室推播開關
 * @param roomId >> 房間id
 * @param clientId >> 用戶Id
 * @param isState >> 欲修改後的狀態
 * */
data class PushRequest(
    @SerializedName("room_id") val roomId: Long,
    @SerializedName("client_id") val clientId: String,
    @SerializedName("is_state") val isState: Boolean,
)

/**
 * 提問室推播聲音開關
 * @param roomId >> 房間id
 * @param clientId >> 用戶Id
 * @param isState >> 欲修改後的狀態
 * */
data class SoundRequest(
    @SerializedName("room_id") val roomId: Long,
    @SerializedName("client_id") val clientId: String,
    @SerializedName("is_state") val isState: Boolean,
)