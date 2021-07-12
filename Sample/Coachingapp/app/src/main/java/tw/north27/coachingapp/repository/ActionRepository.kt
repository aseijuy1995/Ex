package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.AskRoomInfo
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.request.*
import tw.north27.coachingapp.model.response.PushResponse
import tw.north27.coachingapp.model.response.ReflectResponse
import tw.north27.coachingapp.model.response.SoundResponse

class ActionRepository(private val service: IApiService) : IActionRepository {

    override suspend fun fetchAskRoomList(askRoomRequest: AskRoomRequest): Results<List<AskRoom>> {
        return safeApiResults { service.fetchAskRoomList(askRoomRequest = askRoomRequest) }
    }

    override suspend fun updateAskRoomPush(pushRequest: PushRequest): Results<PushResponse> {
        return safeApiResults { service.updateAskRoomPush(pushRequest = pushRequest) }
    }

    override suspend fun updateAskRoomSound(soundRequest: SoundRequest): Results<SoundResponse> {
        return safeApiResults { service.updateAskRoomSound(soundRequest = soundRequest) }
    }

    override suspend fun fetchAskInfoList(askRoomInfoRequest: AskRoomInfoRequest): Results<List<AskRoomInfo>> {
        return safeApiResults { service.fetchAskRoomInfoList(askRoomInfoRequest = askRoomInfoRequest) }
    }

    override suspend fun fetchTeacherList(teacherRequest: TeacherRequest): Results<List<ClientInfo>> {
        return safeApiResults { service.fetchTeacherList(teacherRequest = teacherRequest) }
    }

    override suspend fun fetchCommentList(commentRequest: CommentRequest): Results<List<CommentInfo>> {
        return safeApiResults { service.fetchCommentList(commentRequest = commentRequest) }
    }

    override suspend fun insertReflect(reflectRequest: ReflectRequest): Results<ReflectResponse> {
        return safeApiResults { service.insertReflect(reflectRequest = reflectRequest) }
    }
}