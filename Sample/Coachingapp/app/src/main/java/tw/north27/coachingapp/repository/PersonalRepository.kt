package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.CommentInfo

class PersonalRepository(private val service: IApiService) : IPersonalRepository {

    override suspend fun getCommentList(account: String, score: Double?, educationId: Long?, gradeId: Long?, subjectId: Long?, unitId: Long?, index: Int, num: Int): Results<List<CommentInfo>> {
        return safeApiResults { service.getCommentList(account = account, score = score, educationId = educationId, gradeId = gradeId, subjectId = subjectId, unitId = unitId, index = index, num = num) }
    }

}