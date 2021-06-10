package tw.north27.coachingapp.repository

import android.content.Context
import com.yujie.utilmodule.http.ResponseResults
import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.ext2.safeApiResponseResults
import tw.north27.coachingapp.ext2.safeApiResults
import tw.north27.coachingapp.model.SignIn

class UserRepository(val service: IApiService, val context: Context) : IUserRepository {

    override suspend fun checkSignIn(uuid: String, account: String, accessToken: String, fcmToken: String): ResponseResults<SignIn> {
        return safeApiResponseResults { service.checkSignIn(uuid, account, accessToken, fcmToken) }
    }

    override suspend fun signIn(uuid: String, account: String, password: String, fcmToken: String): ResponseResults<SignIn> {
        return safeApiResponseResults { service.signIn(uuid, account, password, fcmToken) }
    }

    override suspend fun signOut(uuid: String, account: String): Results<SignIn> {
        return safeApiResults { service.signOut(uuid, account) }
    }

}