package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.AppConfig

interface IPublicRepository {

    suspend fun getAppConfig(uuid: String, pushToken: String): Results<AppConfig>

//    suspend fun getLoadTeacher(gradeId: Long? = null, subjectId: Long? = null, chapterId: Long? = null): Results<List<UserInfo>>
//
//    suspend fun getGrade(): Results<List<Grade>>
//
//    suspend fun getSubject(gradeId: Long? = null): Results<List<Subject>>
//
//    suspend fun getChapter(gradeId: Long? = null, subjectId: Long? = null): Results<List<Chapter>>
}