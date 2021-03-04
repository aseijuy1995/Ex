package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.model.result.SignInInfo
import tw.north27.coachingapp.module.http.ResponseResults

interface IUserRepository {

    suspend fun postCheckSignIn(): ResponseResults<SignInInfo>

    suspend fun postSignIn(account: String, password: String, deviceId: String): ResponseResults<SignInInfo>

}