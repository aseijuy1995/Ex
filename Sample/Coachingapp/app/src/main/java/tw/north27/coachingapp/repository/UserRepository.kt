package tw.north27.coachingapp.repository

import com.yujie.core_lib.http.Results
import com.yujie.core_lib.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.request.*
import tw.north27.coachingapp.model.response.UpdateClientResponse

class UserRepository(val service: IApiService) : IUserRepository {

    override suspend fun updateClient(updateClientRequest: UpdateClientRequest): Results<UpdateClientResponse> =
        safeApiResults { service.updateClient(updateClientRequest = updateClientRequest) }

}