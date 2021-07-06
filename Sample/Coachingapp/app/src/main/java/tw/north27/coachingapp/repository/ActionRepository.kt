package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.AskInfos
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.model.request.AskRequest
import tw.north27.coachingapp.model.request.CommentRequest
import tw.north27.coachingapp.model.request.ReflectRequest
import tw.north27.coachingapp.model.request.TeacherRequest
import tw.north27.coachingapp.model.response.ReflectResponse

class ActionRepository(private val service: IApiService) : IActionRepository {

    override suspend fun fetchAskRoomList(askRequest: AskRequest): Results<List<AskRoom>> {
        return safeApiResults { service.fetchAskRoomList(askRequest = askRequest) }
    }

    override suspend fun fetchAskList(id: Long): Results<AskInfos> {
        return safeApiResults { service.fetchAskList(id = id) }
    }

    override suspend fun fetchTeacherList(teacherRequest: TeacherRequest): Results<List<UserInfo>> {
        return safeApiResults { service.fetchTeacherList(teacherRequest = teacherRequest) }
    }

    override suspend fun fetchCommentList(commentRequest: CommentRequest): Results<List<CommentInfo>> {
        return safeApiResults { service.fetchCommentList(commentRequest = commentRequest) }
    }

    override suspend fun insertReflect(reflectRequest: ReflectRequest): Results<ReflectResponse> {
        return safeApiResults { service.insertReflect(reflectRequest = reflectRequest) }
    }
}