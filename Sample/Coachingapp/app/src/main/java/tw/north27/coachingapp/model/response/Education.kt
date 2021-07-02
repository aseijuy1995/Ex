package tw.north27.coachingapp.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * 教育程度數據
 * @param educationLevelList >> 教育列表
 * @param gradeList >> 年級列表
 * @param subjectList >> 科目列表
 * @param unitList >> 單元列表
 * */
@Parcelize
data class Education(
    @SerializedName("education_level_list") val educationLevelList: List<EducationLevel> = emptyList(),
    @SerializedName("grade_list") val gradeList: List<Grade> = emptyList(),
    @SerializedName("subject_list") val subjectList: List<Subject> = emptyList(),
    @SerializedName("unit_list") val unitList: List<Units> = emptyList()
) : Parcelable

/**
 * 教育程度
 * 預設(id == -1)
 * @param id >> 教育id
 * @param name >> 教育名稱
 * */
@Parcelize
data class EducationLevel(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
) : Parcelable

/**
 * 年級
 * 預設(id == -1)
 * @param id >> 年級id
 * @param name >> 年級名稱
 * @param educationLevelId >> 教育程度id
 * @param subjectIdList >> 科目id列表
 * */
@Parcelize
data class Grade(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("education_level_id") val educationLevelId: Long,
    @SerializedName("subject_id_list") val subjectIdList: List<Long>,
) : Parcelable

/**
 * 科目
 * 預設(id == -1)
 * @param id >> 科目id
 * @param name >> 科目名稱
 *
 * */
@Parcelize
data class Subject(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
) : Parcelable


/**
 * 單元
 * @param id >> 單元id
 * @param name >> 單元名稱
 * @param educationLevelId >> 教育程度id
 * @param gradeId >> 年級id
 * @param subjectId >> 科目id
 * */
@Parcelize
data class Units(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("education_level_id") val educationLevelId: Long,
    @SerializedName("grade_id") val gradeId: Long,
    @SerializedName("subject_id") val subjectId: Long
) : Parcelable