package tw.north27.coachingapp.model.response

import com.google.gson.annotations.SerializedName

/**
 * 更新用戶資訊
 * @param isSuccess >> 操作是否成功
 * @param msg >> 回傳訊息
 * */
data class UpdateClientResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
    @SerializedName("msg") val msg: String = ""
)
