package tw.north27.coachingapp.repository.nofinish

import tw.north27.coachingapp.model.result.SignIn
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.module.http.Results

interface IUserRepository {

    suspend fun checkSignIn(uuid: String, account: String, accessToken: String, fcmToken: String): ResponseResults<SignIn>

    suspend fun signIn(uuid: String, account: String, password: String, fcmToken: String): ResponseResults<SignIn>

    suspend fun signOut(uuid: String, account: String): Results<SignIn>

}