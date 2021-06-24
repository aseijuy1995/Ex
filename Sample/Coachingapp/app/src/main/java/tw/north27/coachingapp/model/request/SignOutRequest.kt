package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

/**
 * 登出請求Body
 * @param uuid >> 設備Id
 * @param account >> 帳號（驗證用）
 * */
data class SignOutRequest(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("account") val account: String
)