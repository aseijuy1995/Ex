package tw.north27.coachingapp.model

import android.os.Parcelable
import com.yujie.utilmodule.UserPref
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * 可用於登入&登出
 * 登入:
 *  成功回傳signCode = 1000, signInInfo
 *  失敗回傳signCode = 1001, signInInfo
 * 登出:
 *  成功回傳signState = 1002, signOutInfo
 *  失敗回傳signState = 1003, signOutInfo
 * @param signInCode >> 登入狀態碼
 * @param signInInfo >> 登入資訊
 * @param signOutInfo >> 登出資訊
 * */
data class SignIn(
    val signInCode: Int,
    val signInInfo: SignInInfo? = null,
    val signOutInfo: SignOutInfo? = null,
)

/**
 * 登出入狀態碼
 * @param SIGN_IN_SUCCESS >> 登入成功(1000)
 * @param SIGN_IN_FAILED >> 登入失敗(1001)
 * @param SIGN_OUT_SUCCESS >> 登出成功(1002)
 * @param SIGN_OUT_FAILED >> 登出失敗(1003)
 * */
enum class SignInCode(val code: Int) {
    SIGN_IN_SUCCESS(1000),
    SIGN_IN_FAILED(1001),
    SIGN_OUT_SUCCESS(1002),
    SIGN_OUT_FAILED(1003),
}

/**
 * 登入資訊
 * @param userInfo >> 用戶資訊
 * @param isFirst >> 是否第一次登入
 * @param accessToken >> 訪問用token
 * @param refreshToken >> 刷新用token
 * @param pushToken >> 推撥token
 * @param msg >> 登入成功or失敗訊息
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
 * @param msg >> 登出成功or失敗訊息
 * */
data class SignOutInfo(
    val msg: String? = null,
)

/**
 * 用戶資訊
 * @param id >> 用戶id
 * @param account >> 帳號
 * @param auth >> 權限，STUDENT、TEACHER
 * @param bgUrl >> 背景圖
 * @param avatarUrl >> 頭貼
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
    val bgUrl: String? = null,
    val avatarUrl: String? = null,
    val name: String,
    val gender: Gender? = null,
    val intro: String? = null,
    val birthday: Date? = null,
    val cellPhone: String? = null,
    val homePhone: String? = null,
    val email: String? = null,
    val studentInfo: StudentInfo? = null,
    val teacherInfo: TeacherInfo? = null,
    val userConfig: UserConfig? = null,//用戶設定
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
 * @param unitsList >> 單元列表
 * */
@Parcelize
data class TeacherInfo(
    val commentScoreAvg: Double = 5.0,
    val commentScoreCountList: List<ScoreCountInfo>? = null,
    val replyRate: Double = 100.0,
    val replyCountList: List<ReplyCountInfo>? = null,
    val unitsList: List<Units>? = null,
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