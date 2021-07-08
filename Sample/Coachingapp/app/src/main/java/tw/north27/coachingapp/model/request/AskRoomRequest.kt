package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

/**
 * @param account >> 帳號
 * @param topAskId >> 最新提問條
 * */
data class AskRoomRequest(
    @SerializedName("account") val account: String,
    @SerializedName("ask_id") val topAskId: Long? = null,
)

/**
 * 提問室推播開關
 * @param roomId >> 房間id
 * @param account >> 帳號
 * @param isState >> 欲修改後的狀態
 * */
data class PushRequest(
    @SerializedName("room_id") val roomId: Long,
    @SerializedName("account") val account: String,
    @SerializedName("is_state") val isState: Boolean,
)

/**
 * 提問室推播聲音開關
 * @param roomId >> 房間id
 * @param account >> 帳號
 * @param isState >> 欲修改後的狀態
 * */
data class SoundRequest(
    @SerializedName("room_id") val roomId: Long,
    @SerializedName("account") val account: String,
    @SerializedName("is_state") val isState: Boolean,
)