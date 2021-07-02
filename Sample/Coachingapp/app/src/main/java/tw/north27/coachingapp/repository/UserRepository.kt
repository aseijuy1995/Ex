package tw.north27.coachingapp.repository

import android.content.Context
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.SignIn
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.model.request.SignInRequest
import tw.north27.coachingapp.model.request.SignOutRequest
import tw.north27.coachingapp.model.request.UpdateUserRequest

class UserRepository(val service: IApiService, val context: Context) : IUserRepository {

    override suspend fun checkSignIn(): Results<SignIn> {
        return safeApiResults { service.checkSignIn() }
    }

    override suspend fun signIn(signInRequest: SignInRequest): Results<SignIn> {
        return safeApiResults { service.signIn(signInRequest = signInRequest) }
    }

    override suspend fun fetchUser(account: String): Results<UserInfo> {
        return safeApiResults { service.fetchUser(account = account) }
    }

    override suspend fun updateUser(updateUserRequest: UpdateUserRequest): Results<Boolean> {
        return safeApiResults { service.updateUser(updateUserRequest = updateUserRequest) }
    }

    override suspend fun signOut(signOutRequest: SignOutRequest): Results<SignIn> {
        return safeApiResults { service.signOut(signOutRequest = signOutRequest) }
    }

}