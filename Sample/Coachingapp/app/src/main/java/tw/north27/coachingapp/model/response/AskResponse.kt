package tw.north27.coachingapp.model.response

import com.google.gson.annotations.SerializedName

/**
 * 提問室推播開關
 * @param isSuccess >> 操作是否成功
 * @param msg >> 回傳訊息
 * @param roomId >> 房間id
 * @param isState >> 修改後的狀態
 * */
data class PushResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("msg") val msg: String = "",
    @SerializedName("room_id") var roomId: Long? = null,
    @SerializedName("is_state") var isState: Boolean? = null,
)

/**
 * 提問室推播聲音開關
 * @param isSuccess >> 操作是否成功
 * @param msg >> 回傳訊息
 * @param roomId >> 房間id
 * @param isState >> 修改後的狀態
 * */
data class SoundResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("msg") val msg: String = "",
    @SerializedName("room_id") var roomId: Long? = null,
    @SerializedName("is_state") var isState: Boolean? = null,
)