package tw.north27.coachingapp.model

import android.os.Parcelable
import com.yujie.utilmodule.UserPref
import kotlinx.parcelize.Parcelize

/**
 * 可用於登入&登出
 * 登入:
 *  成功回傳signState = SIGN_IN, signInInfo
 *  失敗回傳signState = SIGN_OUT, signOutInfo（錯誤訊息，帳密錯誤、不存在、封鎖中）
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
 * msg >> 訊息
 * */
data class SignInInfo(
    val userInfo: UserInfo? = null,
    val isFirst: Boolean? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val pushToken: String? = null,
    val msg: String? = null,
)

/**
 * msg >> 訊息
 * */
data class SignOutInfo(
    val msg: String? = null,
)

/**
 * desc >> 簡介
 * */
@Parcelize
data class UserInfo(
    val id: Long,
    val account: String,
    val auth: UserPref.Authority,
    val avatarPath: String? = null,
    val name: String,
    val email: String? = null,
    val desc: String? = null,
    //
    val studentInfo: StudentInfo? = null,
    val teacherInfo: TeacherInfo? = null,


    ) : Parcelable

@Parcelize
data class StudentInfo(
    val desc: String
) : Parcelable

/**
 * score >> 評分
 * answerNum >> 回答數
 * responseRate >> 回覆率
 * unitList >> 單元列表
 * */
@Parcelize
data class TeacherInfo(
    val score: Double,
    val answerNum: Int,
    val responseRate: Int,
    val subjectList: List<Subject>,
    val unitList: List<Unit>,
) : Parcelable

/**
 * CHINESE >> 國語
 * ENGLISH >> 英文
 * MATH >> 數學
 * NATURAL >> 自然
 * SOCIETY >> 社會
 * */
//enum class Subject {
//    CHINESE, ENGLISH, MATH, NATURAL, SOCIETY
//}