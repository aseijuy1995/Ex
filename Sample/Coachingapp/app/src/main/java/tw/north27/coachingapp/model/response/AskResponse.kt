package tw.north27.coachingapp.model.response

import com.google.gson.annotations.SerializedName

/**
 * 提問室推播開關
 * @param isSuccess >> 操作是否成功
 * @param roomId >> 房間id
 * @param isState >> 修改後狀態
 * @param msg >> 回傳訊息
 * */
data class PushResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("room_id") val roomId: Long,
    @SerializedName("is_state") val isState: Boolean,
    @SerializedName("msg") val msg: String = ""
)

/**
 * 提問室推播聲音開關
 * @param isSuccess >> 操作是否成功
 * @param roomId >> 房間id
 * @param isState >> 修改後狀態
 * @param msg >> 回傳訊息
 * */
data class SoundResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("room_id") val roomId: Long,
    @SerializedName("is_state") val isState: Boolean,
    @SerializedName("msg") val msg: String = ""
)