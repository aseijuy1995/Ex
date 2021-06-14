package tw.north27.coachingapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * 教育
 * id >> 教育id
 * text >> 教育名稱
 * */
data class Education(
    val id: Long?,
    val text: String
)

/**
 * 年級
 * id >> 學級id
 * text >> 學級名稱
 * educationId >> 教育id //僅測試用
 * */
@Parcelize
data class Grade(
    val id: Long?,
    val text: String,
    val educationId: Long?
) : Parcelable

/**
 * 科目
 * id >> 科目id
 * text >> 科目名稱
 * educationId >> 教育id //僅測試用
 * gradleId >> 年級id //僅測試用
 * */
@Parcelize
data class Subject(
    val id: Long?,
    val text: String,
    val educationIdList: List<Long>,
    val gradleIdList: List<Long>,
) : Parcelable


/**
 * id >> 單元id
 * text >> 單元名稱
 * educationId >> 教育id
 * gradleId >> 年級id
 * subjectId >> 科目id
 * */
@Parcelize
data class Unit(
    val id: Long?,
    val text: String,
    val educationId: Long?,
    val gradeId: Long?,
    val subjectId: Long?
) : Parcelable