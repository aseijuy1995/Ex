package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.SignInfo
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.request.SignInRequest
import tw.north27.coachingapp.model.request.UpdateClientRequest

interface IUserRepository {

    suspend fun checkSignIn(account: String): Results<SignInfo>

    //
    suspend fun fetchUser(account: String): Results<ClientInfo>

    suspend fun signIn(signInRequest: SignInRequest): Results<SignInfo>

    suspend fun updateUser(updateClientRequest: UpdateClientRequest): Results<Boolean>

}