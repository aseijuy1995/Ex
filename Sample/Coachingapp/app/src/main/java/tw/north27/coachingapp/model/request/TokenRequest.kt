package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

/**
 * 請求Token資訊
 * @param clientId >> 用戶id
 * @param refreshToken >> 刷新用token
 * */
data class TokenRequest(
    @SerializedName("client_id") val clientId: String,
    @SerializedName("refresh_token") val refreshToken: String
)