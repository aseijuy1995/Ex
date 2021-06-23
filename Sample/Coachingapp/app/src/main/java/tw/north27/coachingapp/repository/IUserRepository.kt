package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.*
import java.util.*

interface IUserRepository {

    suspend fun checkSignIn(): Results<SignIn>

    suspend fun signIn(signInBody: SignInBody): Results<SignIn>

    suspend fun getUser(json: String): Results<UserInfo>

    suspend fun getCommentList(commentBody: CommentBody): Results<List<CommentInfo>>

    suspend fun signOut(uuid: String, account: String): Results<SignIn>
    //
    //
    //
    //

    suspend fun getTeacherList(educationId: Long? = null, gradeId: Long? = null, subjectId: Long? = null, unitId: Long? = null): Results<List<UserInfo>>

    suspend fun updateUser(
        account: String,
        bgPath: String,
        avatarPath: String,
        name: String,
        gender: Gender,
        intro: String,
        birthday: Date? = null,
        cellPhone: String,
        homePhone: String,
        email: String,
        school: String? = null,
        gradeId: Long? = null
    ): Results<Boolean>
}