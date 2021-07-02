package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.AskInfo
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.model.request.CommentRequest
import tw.north27.coachingapp.model.request.ReflectRequest
import tw.north27.coachingapp.model.request.TeacherRequest
import tw.north27.coachingapp.model.response.ReflectResponse

interface IActionRepository {

    suspend fun fetchTeacherList(teacherRequest: TeacherRequest): Results<List<UserInfo>>

    suspend fun fetchCommentList(commentRequest: CommentRequest): Results<List<CommentInfo>>

    suspend fun insertReflect(reflectRequest: ReflectRequest): Results<ReflectResponse>

    suspend fun fetchAskList(askId: Long?): Results<List<AskInfo>>

    suspend fun fetchAskRoomList(account: String, id: Long): Results<AskRoom>

}