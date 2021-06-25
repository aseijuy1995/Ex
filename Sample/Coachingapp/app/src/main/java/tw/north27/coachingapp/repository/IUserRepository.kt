package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.model.SignIn
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.model.request.CommentRequest
import tw.north27.coachingapp.model.request.SignInRequest
import tw.north27.coachingapp.model.request.SignOutRequest
import tw.north27.coachingapp.model.request.UpdateUserRequest

interface IUserRepository {

    suspend fun checkSignIn(): Results<SignIn>

    suspend fun signIn(signInRequest: SignInRequest): Results<SignIn>

    suspend fun fetchUser(json: String): Results<UserInfo>

    suspend fun updateUser(updateUserRequest: UpdateUserRequest): Results<Boolean>

    suspend fun fetchCommentList(commentRequest: CommentRequest): Results<List<CommentInfo>>

    suspend fun signOut(signOutRequest: SignOutRequest): Results<SignIn>
    //
    //
    //
    //

    suspend fun getTeacherList(educationId: Long? = null, gradeId: Long? = null, subjectId: Long? = null, unitId: Long? = null): Results<List<UserInfo>>


}