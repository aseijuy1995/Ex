package tw.north27.coachingapp.repository

import com.yujie.core_lib.http.Results
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.AskRoomInfo
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.model.request.*
import tw.north27.coachingapp.model.response.AskRoomResponse
import tw.north27.coachingapp.model.response.PushResponse
import tw.north27.coachingapp.model.response.ReflectResponse
import tw.north27.coachingapp.model.response.SoundResponse

interface IAskRoomRepository {

    //
    suspend fun fetchAskRoomList(askRoomRequest: AskRoomRequest): Results<List<AskRoom>>

    suspend fun updateAskRoomPush(pushRequest: PushRequest): Results<PushResponse>

    suspend fun updateAskRoomSound(soundRequest: SoundRequest): Results<SoundResponse>

    suspend fun findAskRoom(setupAskRoomRequest: SetupAskRoomRequest): Results<AskRoomResponse>

    suspend fun setupAskRoom(setupAskRoomRequest: SetupAskRoomRequest): Results<AskRoom>
    //
    suspend fun fetchAskRoomInfoList(askRoomInfoRequest: AskRoomInfoRequest): Results<List<AskRoomInfo>>
    //

    suspend fun fetchCommentList(commentRequest: CommentRequest): Results<List<CommentInfo>>

    suspend fun insertReflect(reflectRequest: ReflectRequest): Results<ReflectResponse>


}