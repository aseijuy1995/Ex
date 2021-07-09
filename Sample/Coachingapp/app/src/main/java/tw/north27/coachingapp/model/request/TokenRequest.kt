package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("client_id") val clientId: Long,
    @SerializedName("refresh_token") val refreshToken: String
)