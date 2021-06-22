package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.AppConfig
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.model.EducationData

class PublicRepository(val service: IApiService) : IPublicRepository {

    override suspend fun getEducationData(): Results<EducationData> = safeApiResults { service.getEducationData() }

    override suspend fun getAppConfig(): Results<AppConfig> = safeApiResults { service.getAppConfig() }

    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    override suspend fun getCommentList(account: String, educationId: Long?, gradeId: Long?, subjectId: Long?, unitId: Long?, index: Int, num: Int): Results<List<CommentInfo>> = safeApiResults {
        service.getCommentList(account = account, educationId = educationId, gradeId = gradeId, subjectId = subjectId, unitId = unitId, index = index, num = num)
    }

}