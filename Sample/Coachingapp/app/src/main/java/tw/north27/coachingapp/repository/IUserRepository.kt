package tw.north27.coachingapp.repository

import com.yujie.core_lib.http.Results
import com.yujie.core_lib.http.okhttp.TokenInfo
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.SignInfo
import tw.north27.coachingapp.model.request.*
import tw.north27.coachingapp.model.response.UpdateClientResponse

interface IUserRepository {

    fun refreshToken(tokenRequest: TokenRequest): TokenInfo

    suspend fun checkSign(signRequest: SignRequest): Results<SignInfo>

    suspend fun signIn(signInRequest: SignInRequest): Results<SignInfo>

    suspend fun fetchClient(clientRequest: ClientRequest): Results<ClientInfo>

    suspend fun updateClient(updateClientRequest: UpdateClientRequest): Results<UpdateClientResponse>

}