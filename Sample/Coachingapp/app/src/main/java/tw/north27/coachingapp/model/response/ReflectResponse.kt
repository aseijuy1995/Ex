package tw.north27.coachingapp.model.response

import com.google.gson.annotations.SerializedName

/**
 * 響應 - 反映
 * @param isState >> insert成功與否
 * @param msg >> 訊息
 * */
data class ReflectResponse(
    @SerializedName("is_state") val isState: Boolean,
    @SerializedName("msg") val msg: String,
)