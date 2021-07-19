package tw.north27.coachingapp.repository

import com.yujie.core_lib.http.Results
import tw.north27.coachingapp.model.request.UpdateClientRequest
import tw.north27.coachingapp.model.response.UpdateClientResponse

interface IUserRepository {

    suspend fun updateClient(updateClientRequest: UpdateClientRequest): Results<UpdateClientResponse>

}