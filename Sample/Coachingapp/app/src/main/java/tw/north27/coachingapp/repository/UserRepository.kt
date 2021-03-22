package tw.north27.coachingapp.repository

import android.content.Context
import kotlinx.coroutines.flow.first
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.result.SignInInfo
import tw.north27.coachingapp.module.ext.safeApiResponseResults
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.module.pref.UserModule
import tw.north27.coachingapp.repository.inter.IUserRepository

class UserRepository(val service: IApiService, val context: Context) : IUserRepository {

    private val signInModule = UserModule(context)

    override suspend fun postCheckSignIn(fcmToken: String): ResponseResults<SignInInfo> {
        val signInDataStore = signInModule.getValue { it }.first()
        return safeApiResponseResults { service.postCheckSignIn(signInDataStore.account, "deviceId001", fcmToken) }
    }

    override suspend fun postSignIn(account: String, password: String, deviceId: String, fcmToken: String): ResponseResults<SignInInfo> {
        return safeApiResponseResults { service.postSignIn(account, password, deviceId, fcmToken) }
    }


}