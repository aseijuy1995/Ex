package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

/**
 * 提問室推播開關
 * @param roomId >> 房間id
 * @param account >> 帳號
 * @param state >> 當前狀態
 * */
data class PushRequest(
    @SerializedName("room_id") val roomId: Long,
    @SerializedName("account") val account: String,
    @SerializedName("state") val state: Boolean,
)