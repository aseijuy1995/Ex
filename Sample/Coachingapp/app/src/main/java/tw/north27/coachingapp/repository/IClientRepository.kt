package tw.north27.coachingapp.repository

import com.yujie.core_lib.http.Results
import com.yujie.core_lib.http.okhttp.TokenInfo
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.SignInfo
import tw.north27.coachingapp.model.request.*

interface IClientRepository {

    fun refreshToken(tokenRequest: TokenRequest): TokenInfo

    suspend fun checkSign(signRequest: SignRequest): Results<SignInfo>

    suspend fun signIn(signInRequest: SignInRequest): Results<SignInfo>

    suspend fun fetchTeacherList(teacherRequest: TeacherRequest): Results<List<ClientInfo>>

    suspend fun fetchTeacherPair(pairRequest: PairRequest): Results<ClientInfo?>

    suspend fun fetchClient(clientRequest: ClientRequest): Results<ClientInfo>

}