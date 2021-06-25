package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.request.ReflectRequest
import tw.north27.coachingapp.model.response.ReflectResponse

class ActionRepository(private val service: IApiService) : IActionRepository {

    override suspend fun insertReflect(reflectRequest: ReflectRequest): Results<ReflectResponse> {
        return safeApiResults { service.insertReflect(reflectRequest = reflectRequest) }
    }
}