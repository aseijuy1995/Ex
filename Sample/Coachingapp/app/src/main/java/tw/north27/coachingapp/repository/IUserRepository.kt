package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.SignIn
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.model.request.SignInRequest
import tw.north27.coachingapp.model.request.SignOutRequest
import tw.north27.coachingapp.model.request.UpdateUserRequest

interface IUserRepository {

    suspend fun auditAccessToken(): Results<SignIn>

    suspend fun signIn(signInRequest: SignInRequest): Results<SignIn>

    suspend fun fetchUser(account: String): Results<UserInfo>

    suspend fun updateUser(updateUserRequest: UpdateUserRequest): Results<Boolean>

    suspend fun signOut(signOutRequest: SignOutRequest): Results<SignIn>

}