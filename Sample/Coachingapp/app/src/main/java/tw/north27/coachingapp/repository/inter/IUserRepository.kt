package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.model.result.SignInInfo
import tw.north27.coachingapp.module.http.Results

interface IUserRepository {

    suspend fun postCheckSignIn(): Results<SignInInfo>

    suspend fun postSignIn(account: String, password: String, deviceId: String): Results<SignInInfo>

}