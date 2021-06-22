package tw.north27.coachingapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * 教育數據
 * educationList >> 教育列表
 * gradeList >> 年級列表
 * subjectList >> 科目列表
 * unitList >> 單元列表
 * */
@Parcelize
data class EducationData(
    @SerializedName("education_list") val educationList: List<Education>,
    @SerializedName("grade_list") val gradeList: List<Grade>,
    @SerializedName("subject_list") val subjectList: List<Subject>,
    @SerializedName("unit_list") val unitList: List<Units>
) : Parcelable

/**
 * 教育
 * 預設(id == -1)
 * id >> 教育id
 * name >> 教育名稱
 * */
@Parcelize
data class Education(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
) : Parcelable

/**
 * 年級
 * 預設(id == -1)
 * id >> 年級id
 * name >> 年級名稱
 * educationId >> 教育id
 * */
@Parcelize
data class Grade(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("education_id") val educationId: Long
) : Parcelable

/**
 * 科目
 * 預設(id == -1)
 * id >> 科目id
 * name >> 科目名稱
 * */
@Parcelize
data class Subject(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
) : Parcelable


/**
 * 單元
 * id >> 單元id
 * name >> 單元名稱
 * subjectId >> 科目id
 * */
@Parcelize
data class Units(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("subject_id") val subjectId: Long
) : Parcelable