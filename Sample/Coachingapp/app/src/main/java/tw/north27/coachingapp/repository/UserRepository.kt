package tw.north27.coachingapp.repository

import android.content.Context
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.ext2.safeApiResponseResults
import tw.north27.coachingapp.ext2.safeApiResults
import tw.north27.coachingapp.model.result.SignIn
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.repository.inter.IUserRepository

class UserRepository(val service: IApiService, val context: Context) : IUserRepository {

    override suspend fun checkSignIn(uuid: String, account: String, accessToken: String, fcmToken: String): ResponseResults<SignIn> {
        return safeApiResponseResults { service.checkSignIn(uuid, account, accessToken, fcmToken) }
    }

    override suspend fun signIn(account: String, password: String, deviceId: String, fcmToken: String): ResponseResults<SignIn> {
        return safeApiResponseResults { service.signIn(account, password, deviceId, fcmToken) }
    }

    override suspend fun signOut(account: String, deviceId: String): Results<SignIn> {
        return safeApiResults { service.signOut(account, deviceId) }
    }


}