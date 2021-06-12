package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.Unit

interface IPublicRepository {

    suspend fun getAppConfig(uuid: String, pushToken: String): Results<AppConfig>

    //    suspend fun getLoadTeacher(gradeId: Long? = null, subjectId: Long? = null, chapterId: Long? = null): Results<List<UserInfo>>
//
    suspend fun getEducationList(): Results<List<Education>>

    suspend fun getGradeList(educationId: Long?): Results<List<Grade>>

    suspend fun getSubjectList(educationId: Long?, gradeId: Long?): Results<List<Subject>>

    suspend fun getUnitList(educationId: Long?, gradeId: Long?, subjectId: Long?): Results<List<Unit>>
}