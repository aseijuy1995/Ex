package tw.north27.coachingapp.model.result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class SignInfo(
    val signState: SignState,
    val isFirst: Boolean? = null,// 第一次登入
    val user: UserInfo? = null,// 用戶資訊
    //
    val accessToken: String? = null,// 訪問用token
    val refreshToken: String? = null,// 刷新用token
)

enum class SignState {
    SIGN_IN_SUCCESS,//登入成功
    SIGN_IN_FAILURE,//登入失敗
    SIGN_OUT_SUCCESS//登出成功
}

enum class Identity {
    STUDENT,// 學生
    TEACHER,// 老師
}

@Parcelize
data class UserInfo(
    val id: Int,
    val account: String,
    val avatarPath: String,
    val name: String,
    val email: String? = null,
    val fcmToken: String? = null,//推撥token
) : Parcelable