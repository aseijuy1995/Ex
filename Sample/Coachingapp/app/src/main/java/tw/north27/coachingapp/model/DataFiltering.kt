package tw.north27.coachingapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * id >> 學級id
 * text >> 學級名稱
 * */
data class Grade(
    val id: Long,
    val text: String
)

/**
 * id >> 科目id
 * text >> 科目名稱
 * */
@Parcelize
data class Subject(
    val id: Long,
    val gradeIdList: List<Long>,//串接數據需剃除
    val text: String
) : Parcelable


/**
 * id >> 單元id
 * text >> 單元名稱
 * */
data class Chapter(
    val id: Long,
    val gradeId: Long,//串接數據需剃除
    val subjectId: Long,//串接數據需剃除
    val text: String
)