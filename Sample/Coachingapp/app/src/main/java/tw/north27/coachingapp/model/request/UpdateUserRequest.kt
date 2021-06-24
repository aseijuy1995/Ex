package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName
import tw.north27.coachingapp.model.Gender
import java.util.*

/**
 * 更新用戶資訊
 * @param account >> 帳號
 * @param bgUrl? >> 背景
 * @param avatarUrl? >> 頭貼
 * @param name >> 名稱
 * @param gender >> 性別
 * @param intro >> 簡介
 * @param birthday? >> 生日
 * @param cellPhone >> 電話
 * @param homePhone >> 家電
 * @param email >> Email
 * @param school? >> 學校
 * @param gradeId? >> 年級Id
 * */
data class UpdateUserRequest(
    @SerializedName("account") val account: String,
    @SerializedName("bg_url") val bgUrl: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Gender,
    @SerializedName("intro") val intro: String,
    @SerializedName("birthday") val birthday: Date? = null,
    @SerializedName("cell_phone") val cellPhone: String,
    @SerializedName("home_phone") val homePhone: String,
    @SerializedName("email") val email: String,
    @SerializedName("school") val school: String? = null,
    @SerializedName("grade_id") val gradeId: Long? = null
)
