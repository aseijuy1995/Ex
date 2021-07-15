package tw.north27.coachingapp.repository

import com.yujie.core_lib.http.Results
import com.yujie.core_lib.http.safeApiResults
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

    override suspend fun fetchTeacherList(teacherRequest: TeacherRequest): Results<List<ClientInfo>> =
        safeApiResults { service.fetchTeacherList(teacherRequest = teacherRequest) }

    override suspend fun fetchAskRoomList(askRoomRequest: AskRoomRequest): Results<List<AskRoom>> =
        safeApiResults { service.fetchAskRoomList(askRoomRequest = askRoomRequest) }

    override suspend fun updateAskRoomPush(pushRequest: PushRequest): Results<PushResponse> =
        safeApiResults { service.updateAskRoomPush(pushRequest = pushRequest) }

    override suspend fun updateAskRoomSound(soundRequest: SoundRequest): Results<SoundResponse> =
        safeApiResults { service.updateAskRoomSound(soundRequest = soundRequest) }

    override suspend fun fetchAskRoomInfoList(askRoomInfoRequest: AskRoomInfoRequest): Results<List<AskRoomInfo>> =
        safeApiResults { service.fetchAskRoomInfoList(askRoomInfoRequest = askRoomInfoRequest) }

    override suspend fun fetchCommentList(commentRequest: CommentRequest): Results<List<CommentInfo>> =
        safeApiResults { service.fetchCommentList(commentRequest = commentRequest) }

    override suspend fun insertReflect(reflectRequest: ReflectRequest): Results<ReflectResponse> =
        safeApiResults { service.insertReflect(reflectRequest = reflectRequest) }
}