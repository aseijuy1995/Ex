package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.CommentInfo

interface IPersonalRepository {

    suspend fun getCommentList(account: String, score: Double?, educationId: Long?, gradeId: Long?, subjectId: Long?, unitId: Long?, index: Int, num: Int): Results<List<CommentInfo>>
}