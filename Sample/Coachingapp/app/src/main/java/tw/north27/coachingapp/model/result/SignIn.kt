package tw.north27.coachingapp.model.result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 可用於登入&登出
 * 登入:
 *  成功回傳signState = SIGN_IN, signInInfo
 *  失敗回傳signState = SIGN_OUT, signOutInfo（錯誤訊息）
 * 登出:
 *  成功回傳signState = SIGN_OUT, signOutInfo（無錯誤訊息）
 *  失敗回傳signState = SIGN_IN（基本上不可失敗）
 *
 * signState >> 登入狀態
 * signInInfo >> 登入資訊
 * signOutInfo >> 登出資訊
 * */
data class SignIn(
    val signInState: SignInState,
    val signInInfo: SignInInfo? = null,
    val signOutInfo: SignOutInfo? = null,
)

/**
 * SIGN_IN >> 登入中
 * SIGN_OUT >> 登出中
 * */
enum class SignInState {
    SIGN_IN,
    SIGN_OUT,
}

/**
 * userInfo >> 用戶資訊
 * isFirst >> 是否第一次登入
 * accessToken >> 訪問用token
 * refreshToken >> 刷新用token
 * fcmToken >> 推撥token
 * */
data class SignInInfo(
    val userInfo: UserInfo? = null,
    val isFirst: Boolean? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val fcmToken: String? = null
)

/**
 * code >> 代碼
 * msg >> 訊息
 * */
data class SignOutInfo(
//    val code: Int? = null,
    val msg: String? = null,
)

@Parcelize
data class UserInfo(
    val id: Int,
    val account: String,
    val auth: Authority,
    val avatarPath: String,
    val name: String,
    val email: String? = null
//    val fcmToken: String? = null,//推撥token
) : Parcelable

/**
 * STUDENT >> 學生
 * TEACHER >> 老師
 * */
enum class Authority {
    STUDENT, TEACHER
}