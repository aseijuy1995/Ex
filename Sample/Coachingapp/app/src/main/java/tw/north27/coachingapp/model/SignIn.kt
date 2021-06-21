package tw.north27.coachingapp.model

import android.os.Parcelable
import com.yujie.utilmodule.UserPref
import kotlinx.parcelize.Parcelize
import java.util.*

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
 * @param id >> 用戶id
 * @param account >> 帳號
 * @param auth >> 權限，STUDENT、TEACHER
 * @param bgPath >> 背景圖
 * @param avatarPath >> 頭貼
 * @param name >> 名稱
 * @param gender >> 性別
 * @param intro >> 簡介
 * @param birthday >> 生日
 * @param cellPhone >> 手機號
 * @param homePhone >> 家電號
 * @param email >> E-Mail
 * @param studentInfo >> 學生資訊
 * @param teacherInfo >> 老師資訊
 * @param userConfig >> 用戶設定
 * */
@Parcelize
data class UserInfo(
    val id: Long,
    val account: String,
    val auth: UserPref.Authority,
    val bgPath: String? = null,
    val avatarPath: String? = null,
    val name: String,
    val gender: Gender? = null,
    val intro: String? = null,
    val birthday: Date? = null,
    val cellPhone: String? = null,
    val homePhone: String? = null,
    val email: String? = null,
    val studentInfo: StudentInfo? = null,
    val teacherInfo: TeacherInfo? = null,
    val userConfig: UserConfig? = null,//訊息通知開關
) : Parcelable

/**
 * 性別
 * @param MALE >> 男
 * @param FEMALE >> 女
 * */
enum class Gender {
    MALE, FEMALE
}

/**
 * 學生資訊
 * @param school >> 學校
 * @param gradeId >> 年級Id
 * */
@Parcelize
data class StudentInfo(
    val school: String? = null,
    val gradeId: Long? = null
) : Parcelable

/**
 * 老師資訊
 * @param commentScoreAvg >> 評論均分
 * @param commentScoreCountList >> 1~5評分數量列表
 * @param replyRate >> 回覆率
 * @param replyCountList >> 已回覆、未回覆數量列表
 * @param unitList >> 單元列表
 * */
@Parcelize
data class TeacherInfo(
    val commentScoreAvg: Double = 5.0,
    val commentScoreCountList: List<ScoreCountInfo>? = null,
    val replyRate: Double = 100.0,
    val replyCountList: List<ReplyCountInfo>? = null,
    val subjectList: List<Subject>,
    val unitList: List<Unit>,
) : Parcelable

/**
 * 用戶設定
 * @param replyRemind >> 回覆提醒
 * @param msgRemind >> 訊息提醒
 * */
@Parcelize
data class UserConfig(
    val replyNotice: Boolean? = null,
    val msgNotice: Boolean? = null,
) : Parcelable

/**
 * 評分數量列表
 * @param score >> 評分
 * @param count >> 數量
 * */
@Parcelize
data class ScoreCountInfo(
    val score: Double,
    val count: Int
) : Parcelable

/**
 * 評論資訊
 * @param id >> 評論id
 * @param sendAccount >> 發送者帳號
 * @param sendName >> 發送者名稱
 * @param receiveAccount >> 接收者帳號
 * @param receiveName >> 接收者名稱
 * @param score >> 評分，1~5分
 * @param content >> 內容
 * @param date >> 日期
 * @param educationId >> 教育
 * @param gradeId >> 年級
 * @param subjectId >> 科目
 * @param unitId >> 單元
 * */
@Parcelize
data class CommentInfo(
    val id: Long,
    val sendAccount: String,
    val sendName: String,
    val receiveAccount: String,
    val receiveName: String,
    val score: Double,
    val content: String,
    val date: String,
    val educationId: Long,
    val gradeId: Long,
    val subjectId: Long,
    val unitId: Long
) : Parcelable

/**
 * 回覆數量列表
 * @param reply >> 已回覆、未回覆
 * @param count >> 數量
 * */
@Parcelize
data class ReplyCountInfo(
    val reply: String,
    val count: Int
) : Parcelable