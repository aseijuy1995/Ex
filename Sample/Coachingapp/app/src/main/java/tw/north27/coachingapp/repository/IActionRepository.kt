package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.request.ReflectRequest
import tw.north27.coachingapp.model.response.ReflectResponse

interface IActionRepository {

    suspend fun insertReflect(reflectRequest: ReflectRequest): Results<ReflectResponse>

}