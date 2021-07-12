package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

/**
 * 請求檢查
 * @param clientId >> 用戶id
 * */
data class SignRequest(
    @SerializedName("client_id") val clientId: String
)

/**
 * 請求登入
 * @param uuid >> 設備Id
 * @param account >> 帳號
 * @param password >> 密碼
 * @param pushToken >> 推播token
 * */
data class SignInRequest(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("account") val account: String,
    @SerializedName("password") val password: String,
    @SerializedName("push_token") val pushToken: String,
)