package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.SignInfo
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.request.SignInRequest
import tw.north27.coachingapp.model.request.UpdateClientRequest

class UserRepository(val service: IApiService) : IUserRepository {

    override suspend fun checkSignIn(account: String): Results<SignInfo> {
        return safeApiResults { service.checkSign(account = account) }
    }

    override suspend fun signIn(signInRequest: SignInRequest): Results<SignInfo> {
        return safeApiResults { service.signIn(signInRequest = signInRequest) }
    }

    override suspend fun fetchUser(account: String): Results<ClientInfo> {
        return safeApiResults { service.fetchClient(account = account) }
    }

    override suspend fun updateUser(updateClientRequest: UpdateClientRequest): Results<Boolean> {
        return safeApiResults { service.updateClient(updateClientRequest = updateClientRequest) }
    }

}