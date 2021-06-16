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
 * 登出入狀態
 * SIGN_IN >> 登入
 * SIGN_OUT >> 登出
 * */
enum class SignInState {
    SIGN_IN,
    SIGN_OUT,
}

/**
 * 登入資訊
 * userInfo >> 用戶資訊
 * isFirst >> 是否第一次登入
 * accessToken >> 訪問用token
 * refreshToken >> 刷新用token
 * pushToken >> 推撥token
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
 * 登出資訊
 * msg >> 訊息
 * */
data class SignOutInfo(
    val msg: String? = null,
)

/**
 * 用戶資訊
 * id >> 用戶id
 * account >> 帳號
 * auth >> 權限，UNKNOWN、STUDENT、TEACHER
 * avatarPath >> 頭貼
 * name >> 名稱
 * gender >> 性別
 * desc >> 簡介
 * birthday >> 生日
 * cellPhone >> 手機號
 * homePhone >> 家電號
 * email >> E-Mail
 * replyRemind >> E-Mail
 * */
@Parcelize
data class UserInfo(
    val id: Long,
    val account: String,
    val auth: UserPref.Authority,
    val avatarPath: String? = null,
    val name: String,
    val gender: Gender? = null,
    val desc: String? = null,
//    val birthday: LocalDate? = null,
    val birthday: String? = null,
    val cellPhone: String? = null,
    val homePhone: String? = null,
    val email: String? = null,
    val studentInfo: StudentInfo? = null,
    val teacherInfo: TeacherInfo? = null,
    //
    val userConfig: UserConfig? = null,//訊息通知開關
    //

) : Parcelable

/**
 * MALE >> 男
 * FEMALE >> 女
 * */
enum class Gender {
    MALE, FEMALE
}

/**
 * school >> 學校
 * grade >> 年級
 * */
@Parcelize
data class StudentInfo(
    val school: String,
    val grade: Grade
) : Parcelable

/**
 * avgReviewScore >> 平均分數
 * eachScoreList >> 各評分數量
 * replyRate >> 回覆率
 * replyNum >> 回覆數

 * commectList >> 評論列表
 * subjectList >> (暫時)科目列表
 * unitList >> 單元列表
 *
 *
 * */
@Parcelize
data class TeacherInfo(
    val avgCommectScore: Double,
    val eachScoreList: List<Score>? = null,
    val replyRate: Int,
    val replyNum: Int,
//    val commectList: List<Commect>? = null,
    val subjectList: List<Subject>,
    val unitList: List<Unit>,
) : Parcelable

/**
 * 用戶設定
 * replyRemind >> 回覆提醒開關
 * msgRemind >> 訊息提醒開關
 * */
@Parcelize
data class UserConfig(
    val replyNotice: Boolean? = null,
    val msgNotice: Boolean? = null,
) : Parcelable

/**
 * grade >> 等級
 * count >> 數量
 * */
@Parcelize
data class Score(
    val grade: Float,
    val count: Int
) : Parcelable

/**
 * 評論
 * id >> 評論id
 * account >> 評論者id
 * score >> 評分，1~5分
 * msg >> 評論內容
 * */
@Parcelize
data class Commect(
    val id: Long,
    val account: String,
    val score: Int,
    val msg: String,
) : Parcelable