package tw.north27.coachingapp.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.yujie.core_lib.UserPref
import com.yujie.core_lib.http.okhttp.TokenInfo
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * 請求登入檢查
 * @param clientId >> 用戶id
 * */
data class SignRequest(
    @SerializedName("client_id") val clientId: String
)

/**
 * 請求登入
 * @param account >> 帳號
 * @param password >> 密碼
 * @param pushToken >> 推播token
 * */
data class SignInRequest(
    @SerializedName("account") val account: String,
    @SerializedName("password") val password: String,
    @SerializedName("push_token") val pushToken: String,
)

/**
 * 可用於登入&登出
 * @param signCode >> 登入狀態碼
 * @param signInInfo >> 登入資訊
 * @param msg >> 訊息
 * */
data class SignInfo(
    @SerializedName("code") val signCode: Int,
    @SerializedName("sign_in_info") val signInInfo: SignInInfo? = null,
    @SerializedName("msg") val msg: String? = null,
)

/**
 * 登入、登出狀態碼
 * @param SIGN_IN_SUCCESS >> 登入成功(2000)
 * @param SIGN_IN_FAILED >> 登入失敗(2001)
 * */
enum class SignCode(val code: Int) {
    SIGN_IN_SUC(2000),
    SIGN_IN_FAIL(2001);

    override fun toString(): String = code.toString()
}

/**
 * 登入資訊
 * @param clientInfo >> 用戶資訊
 * @param tokenInfo >> token資訊
 * @param isFirst >> 是否第一次登入
 * @param pushToken >> 推播token
 * */
data class SignInInfo(
    @SerializedName("client_info") val clientInfo: ClientInfo? = null,
    @SerializedName("token_info") val tokenInfo: TokenInfo? = null,
    @SerializedName("is_first") val isFirst: Boolean = true,
    @SerializedName("push_token") val pushToken: String = ""
)

/**
 * 用戶資訊
 * @param id >> 用戶Id
 * @param id >> 帳號
 * @param auth >> 權限，STUDENT、TEACHER
 * //
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
data class ClientInfo(
    @SerializedName("id") val id: String,
    @SerializedName("auth") val auth: UserPref.Authority = UserPref.Authority.NONE,
    //
    @SerializedName("bg_url") val bgUrl: String = "",
    @SerializedName("avatar_url") val avatarUrl: String = "",
    @SerializedName("name") val name: String = "",
    //
    @SerializedName("gender") val gender: Gender? = null,
    @SerializedName("intro") val intro: String = "",
    @SerializedName("birthday") val birthday: Date? = null,
    @SerializedName("cell_phone") val cellPhone: String = "",
    @SerializedName("home_phone") val homePhone: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("student_info") val studentInfo: StudentInfo? = null,
    @SerializedName("teacher_info") val teacherInfo: TeacherInfo? = null,
    @SerializedName("user_config") val userConfig: UserConfig? = null,
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
 * @param unitTypeList >> 單元列表
 * */
@Parcelize
data class TeacherInfo(
    @SerializedName("comment_score_avg") val commentScoreAvg: Double = 5.0,
    @SerializedName("comment_score_count_list") val commentScoreCountList: List<ScoreCountInfo>? = null,
    @SerializedName("reply_rate") val replyRate: Double = 100.0,
    @SerializedName("reply_count_list") val replyCountList: List<ReplyCountInfo>? = null,
    @SerializedName("units_list") val unitTypeList: List<UnitType>? = null,
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
 * @param senderId >> 發送者帳號
 * @param sendName >> 發送者名稱
 * @param receiveId >> 接收者帳號
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
    @SerializedName("sender_id") val senderId: String,
    @SerializedName("sender_name") val sendName: String,
    @SerializedName("receive_id") val receiveId: String,
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
