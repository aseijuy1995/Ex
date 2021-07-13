package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.TokenInfo
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.SignInfo
import tw.north27.coachingapp.model.request.*
import tw.north27.coachingapp.model.response.UpdateClientResponse

class UserRepository(val service: IApiService) : IUserRepository {

    override fun refreshToken(tokenRequest: TokenRequest): TokenInfo =
        service.refreshToken(tokenRequest = tokenRequest)

    override suspend fun checkSign(signRequest: SignRequest): Results<SignInfo> =
        safeApiResults { service.checkSign(signRequest = signRequest) }

    override suspend fun signIn(signInRequest: SignInRequest): Results<SignInfo> =
        safeApiResults { service.signIn(signInRequest = signInRequest) }

    override suspend fun fetchClient(clientRequest: ClientRequest): Results<ClientInfo> =
        safeApiResults { service.fetchClient(clientRequest = clientRequest) }

    override suspend fun updateClient(updateClientRequest: UpdateClientRequest): Results<UpdateClientResponse> =
        safeApiResults { service.updateClient(updateClientRequest = updateClientRequest) }

}