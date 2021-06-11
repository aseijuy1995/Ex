package tw.north27.coachingapp.repository

import android.content.Context
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.SignIn

class UserRepository(val service: IApiService, val context: Context) : IUserRepository {

    override suspend fun checkSignIn(uuid: String, account: String, pushToken: String): Results<SignIn> {
        return safeApiResults { service.checkSignIn(uuid, account, pushToken) }
    }

    override suspend fun signIn(uuid: String, account: String, password: String, pushToken: String): Results<SignIn> {
        return safeApiResults { service.signIn(uuid, account, password, pushToken) }
    }

    override suspend fun signOut(uuid: String, account: String): Results<SignIn> {
        return safeApiResults { service.signOut(uuid, account) }
    }

}