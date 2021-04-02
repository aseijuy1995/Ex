package tw.north27.coachingapp.model.result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class SignInInfo(
    val signInState: SignInState,
    val isFirst: Boolean? = null,// 第一次登入
    val user: UserInfo? = null,// 用戶資訊
    //
    val accessToken: String? = null,// 訪問用token
    val refreshToken: String? = null,// 刷新用token
)

enum class SignInState {
    SUCCESS,// 成功
    FAILURE;// 失敗
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