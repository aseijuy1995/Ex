package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.AppConfig
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.model.EducationData

interface IPublicRepository {

    suspend fun getEducationData(): Results<EducationData>

    suspend fun getAppConfig(): Results<AppConfig>

    //
    //
    //
    //
    suspend fun getCommentList(account: String, educationId: Long?, gradeId: Long?, subjectId: Long?, unitId: Long?, index: Int, num: Int): Results<List<CommentInfo>>
}