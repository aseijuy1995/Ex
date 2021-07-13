package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

/**
 * 請求反映問題
 * @param account >> 帳號
 * @param id >> 類型Id
 * @param content >> 內文
 * */
data class ReflectRequest(
    @SerializedName("account") val account: String,
    @SerializedName("id") val id: Long,
    @SerializedName("content") val content: String,
)