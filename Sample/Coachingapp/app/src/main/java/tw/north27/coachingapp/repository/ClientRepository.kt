package tw.north27.coachingapp.repository

import com.yujie.core_lib.http.Results
import com.yujie.core_lib.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.request.PairRequest
import tw.north27.coachingapp.model.request.TeacherRequest

class ClientRepository(private val service: IApiService) : IClientRepository {

    override suspend fun fetchTeacherList(teacherRequest: TeacherRequest): Results<List<ClientInfo>> =
        safeApiResults { service.fetchTeacherList(teacherRequest = teacherRequest) }

    override suspend fun fetchTeacherPair(pairRequest: PairRequest): Results<ClientInfo?> =
        safeApiResults { service.fetchTeacherPair(pairRequest = pairRequest) }

}