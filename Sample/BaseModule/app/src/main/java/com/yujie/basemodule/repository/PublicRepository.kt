//package tw.north27.coachingapp.repository
//
//import com.yujie.utilmodule.http.Results
//import tw.north27.coachingapp.consts.IApiService
//import tw.north27.coachingapp.model.*
//import tw.north27.coachingapp.repository.inter.IPublicRepository
//
//class PublicRepository(val service: IApiService) : IPublicRepository {
//
//    override suspend fun getAppConfig(uuid: String, fcmToken: String): Results<AppConfig> = safeApiResults { service.getAppConfig(uuid, fcmToken) }
//
//    override suspend fun getLoadTeacher(gradeId: Long?, subjectId: Long?, chapterId: Long?): Results<List<UserInfo>> = safeApiResults { service.getLoadTeacher(gradeId, subjectId, chapterId) }
//
//    override suspend fun getGrade(): Results<List<Grade>> = safeApiResults { service.getGrade() }
//
//    override suspend fun getSubject(gradeId: Long?): Results<List<Subject>> = safeApiResults { service.getSubject(gradeId) }
//
//    override suspend fun getChapter(gradeId: Long?, subjectId: Long?): Results<List<Chapter>> = safeApiResults { service.getChapter(gradeId, subjectId) }
//}