package tw.north27.coachingapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
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
    @SerializedName("sign_in_code") val signInCode: Int,
    @SerializedName("sign_in_info") val signInInfo: SignInInfo? = null,
    @SerializedName("sign_out_info") val signOutInfo: SignOutInfo? = null,
)

/**
 * 登出入狀態碼
 * @param SIGN_IN_SUCCESS >> 登入成功(1000)
 * @param SIGN_IN_FAILED >> 登入失敗(1001)
 * @param SIGN_OUT_SUCCESS >> 登出成功(1002)
 * @param SIGN_OUT_FAILED >> 登出失敗(1003)
 * */
enum class SignInCode(val code: Int) {
    SIGN_IN_SUC(1000),
    SIGN_IN_FAIL(1001),
    SIGN_OUT_SUCCESS(1002),
    SIGN_OUT_FAILED(1003),
}

/**
 * 登入資訊
 * @param userInfo >> 用戶資訊
 * @param expireTime >> accessToken過期時間
 * @param accessToken >> 訪問用token
 * @param refreshToken >> 刷新用token
 * @param isFirst >> 是否第一次登入
 * @param pushToken >> 推撥token
 * @param msg >> 登入成功or失敗訊息
 * */
data class SignInInfo(
    @SerializedName("user_info") val userInfo: UserInfo? = null,
    @SerializedName("expire_time") val expireTime: Long? = null,
    @SerializedName("access_token") val accessToken: String? = null,
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("is_first") val isFirst: Boolean? = null,
    @SerializedName("push_token") val pushToken: String? = null,
    @SerializedName("msg") val msg: String? = null,
)

/**
 * 登出資訊
 * @param msg >> 登出成功or失敗訊息
 * */
data class SignOutInfo(
    @SerializedName("msg") val msg: String? = null,
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
    @SerializedName("id") val id: String,
    @SerializedName("account") val account: String,
    @SerializedName("auth") val auth: UserPref.Authority,
    @SerializedName("bg_url") val bgUrl: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Gender? = null,
    @SerializedName("intro") val intro: String? = null,
    @SerializedName("birthday") val birthday: Date? = null,
    @SerializedName("cell_phone") val cellPhone: String? = null,
    @SerializedName("home_phone") val homePhone: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("student_info") val studentInfo: StudentInfo? = null,
    @SerializedName("teacher_info") val teacherInfo: TeacherInfo? = null,
    @SerializedName("user_config") val userConfig: UserConfig? = null,//用戶設定
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
    @SerializedName("school") val school: String? = null,
    @SerializedName("grade_id") val gradeId: Long? = null
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
    @SerializedName("comment_score_avg") val commentScoreAvg: Double = 5.0,
    @SerializedName("comment_score_count_list") val commentScoreCountList: List<ScoreCountInfo>? = null,
    @SerializedName("reply_rate") val replyRate: Double = 100.0,
    @SerializedName("reply_count_list") val replyCountList: List<ReplyCountInfo>? = null,
    @SerializedName("units_list") val unitsList: List<Units>? = null,
) : Parcelable

/**
 * 用戶設定
 * @param replyRemind >> 回覆提醒
 * @param msgRemind >> 訊息提醒
 * */
@Parcelize
data class UserConfig(
    @SerializedName("reply_notice") val replyNotice: Boolean? = null,
    @SerializedName("msg_notice") val msgNotice: Boolean? = null,
) : Parcelable

/**
 * 評分數量列表
 * @param score >> 評分
 * @param count >> 數量
 * */
@Parcelize
data class ScoreCountInfo(
    @SerializedName("score") val score: Double,
    @SerializedName("count") val count: Int
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
    @SerializedName("id") val id: Long,
    @SerializedName("send_account") val sendAccount: String,
    @SerializedName("send_name") val sendName: String,
    @SerializedName("receive_account") val receiveAccount: String,
    @SerializedName("receive_name") val receiveName: String,
    @SerializedName("score") val score: Double,
    @SerializedName("content") val content: String,
    @SerializedName("date") val date: Date,
    @SerializedName("education_id") val educationId: Long,
    @SerializedName("grade_id") val gradeId: Long,
    @SerializedName("subject_id") val subjectId: Long,
    @SerializedName("unit_id") val unitId: Long
) : Parcelable

/**
 * 回覆數量列表
 * @param reply >> 已回覆、未回覆
 * @param count >> 數量
 * */
@Parcelize
data class ReplyCountInfo(
    @SerializedName("reply") val reply: String,
    @SerializedName("count") val count: Int
) : Parcelable
