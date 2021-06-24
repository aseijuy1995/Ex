package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

/**
 * 登入請求Body
 * @param uuid >> 設備Id
 * @param account >> 帳號（驗證用）
 * @param password >> 密碼（驗證用）
 * @param pushToken >> firebase cloud messaging token（驗證成功需綁定帳號）
 * */
data class SignInRequest(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("account") val account: String,
    @SerializedName("password") val password: String,
    @SerializedName("push_token") val pushToken: String,
)