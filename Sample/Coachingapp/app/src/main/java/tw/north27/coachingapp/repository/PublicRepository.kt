package tw.north27.coachingapp.repository//package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.Unit

class PublicRepository(val service: IApiService) : IPublicRepository {

    override suspend fun getAppConfig(uuid: String, pushToken: String): Results<AppConfig> = safeApiResults { service.getAppConfig(uuid, pushToken) }

//    override suspend fun getLoadTeacher(gradeId: Long?, subjectId: Long?, chapterId: Long?): Results<List<UserInfo>> = safeApiResults { service.getLoadTeacher(gradeId, subjectId, chapterId) }

    override suspend fun getEducationList(): Results<List<Education>> = safeApiResults { service.getEducationList() }

    override suspend fun getGradeList(educationId: Long?): Results<List<Grade>> = safeApiResults {
        if (educationId == 0L)
            service.getGradeList()
        else
            service.getGradeList(educationId)
    }

    override suspend fun getSubjectList(educationId: Long?, gradeId: Long?): Results<List<Subject>> = safeApiResults {
        if (educationId == 0L && gradeId == 0L)
            service.getSubjectList()
        else if (educationId == 0L)
            service.getSubjectList(gradeId = gradeId)
        else if (gradeId == 0L)
            service.getSubjectList(educationId = educationId)
        else
            service.getSubjectList(educationId, gradeId)
    }

    override suspend fun getUnitList(educationId: Long?, gradeId: Long?, subjectId: Long?): Results<List<Unit>> = safeApiResults {
        if (educationId == 0L && gradeId == 0L && subjectId == 0L)
            service.getUnitList()
        else if (educationId == 0L && gradeId == 0L)
            service.getUnitList(subjectId = subjectId)
        else if (gradeId == 0L && subjectId == 0L)
            service.getUnitList(educationId = educationId)
        else if (educationId == 0L && subjectId == 0L)
            service.getUnitList(gradeId = gradeId)
        else if (educationId == 0L)
            service.getUnitList(gradeId = gradeId, subjectId = subjectId)
        else if (gradeId == 0L)
            service.getUnitList(educationId = educationId, subjectId = subjectId)
        else if (subjectId == 0L)
            service.getUnitList(educationId = educationId, gradeId = gradeId)
        else
            service.getUnitList(educationId, gradeId, subjectId)
    }

}