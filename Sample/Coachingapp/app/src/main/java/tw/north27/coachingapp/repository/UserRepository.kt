package tw.north27.coachingapp.repository

import android.content.Context
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.ext.safeApiResponseResults
import tw.north27.coachingapp.ext.safeApiResults
import tw.north27.coachingapp.model.result.SignInfo
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.repository.inter.IUserRepository

class UserRepository(val service: IApiService, val context: Context) : IUserRepository {

    override suspend fun checkSignIn(account: String, deviceId: String, fcmToken: String): ResponseResults<SignInfo> {
        return safeApiResponseResults { service.checkSignIn(account, deviceId, fcmToken) }
    }

    override suspend fun signIn(account: String, password: String, deviceId: String, fcmToken: String): ResponseResults<SignInfo> {
        return safeApiResponseResults { service.signIn(account, password, deviceId, fcmToken) }
    }

    override suspend fun signOut(account: String, deviceId: String): Results<SignInfo> {
        return safeApiResults { service.signOut(account, deviceId) }
    }


}