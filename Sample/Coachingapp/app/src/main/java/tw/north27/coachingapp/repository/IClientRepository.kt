package tw.north27.coachingapp.repository

import com.yujie.core_lib.http.Results
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.request.PairRequest
import tw.north27.coachingapp.model.request.TeacherRequest

interface IClientRepository {

    suspend fun fetchTeacherList(teacherRequest: TeacherRequest): Results<List<ClientInfo>>

    suspend fun fetchTeacherPair(pairRequest: PairRequest): Results<ClientInfo?>

}