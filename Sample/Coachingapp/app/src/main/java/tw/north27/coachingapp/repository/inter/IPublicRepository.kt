package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.Chapter
import tw.north27.coachingapp.module.http.Results

interface IPublicRepository {

    suspend fun getAppConfig(
        uuid: String,
        fcmToken: String
    ): Results<AppConfig>

    suspend fun getLoadTeacher(gradeId: Long? = null, subjectId: Long? = null, chapterId:Long? = null): Results<List<UserInfo>>

    suspend fun getGrade(): Results<List<Grade>>

    suspend fun getSubject(gradeId: Long? = null): Results<List<Subject>>

    suspend fun getChapter(gradeId: Long? = null, subjectId: Long? = null): Results<List<Chapter>>
}