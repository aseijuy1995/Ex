package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.SignIn

interface IUserRepository {

    suspend fun checkSignIn(uuid: String, account: String, pushToken: String): Results<SignIn>

    suspend fun signIn(uuid: String, account: String, password: String, pushToken: String): Results<SignIn>

    suspend fun signOut(uuid: String, account: String): Results<SignIn>

}