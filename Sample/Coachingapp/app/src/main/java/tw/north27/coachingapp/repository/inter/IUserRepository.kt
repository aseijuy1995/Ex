package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.model.result.SignInfo
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.module.http.Results

interface IUserRepository {

    suspend fun checkSignIn(account: String, deviceId: String, fcmToken: String): ResponseResults<SignInfo>

    suspend fun signIn(account: String, password: String, deviceId: String, fcmToken: String): ResponseResults<SignInfo>

    suspend fun signOut(account: String, deviceId: String): Results<SignInfo>

}