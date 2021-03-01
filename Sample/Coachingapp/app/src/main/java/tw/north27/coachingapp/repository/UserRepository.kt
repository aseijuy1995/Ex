package tw.north27.coachingapp.repository

import android.content.Context
import kotlinx.coroutines.flow.first
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.result.SignInInfo
import tw.north27.coachingapp.module.ext.safeApiResults
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.module.pref.SignInModule
import tw.north27.coachingapp.repository.inter.IUserRepository

class UserRepository(val service: IApiService, val context: Context) : IUserRepository {

    private val signInModule = SignInModule(context)

    override suspend fun postCheckSignIn(): Results<SignInInfo> {
        val signInDataStore = signInModule.getValue { it }.first()
        return safeApiResults { service.postCheckSignIn(signInDataStore.account, "deviceId001") }
    }

    override suspend fun postSignIn(account: String, password: String?, deviceId: String): Results<SignInInfo> {
        return safeApiResults { service.postSignIn(account, password, deviceId) }
    }


}