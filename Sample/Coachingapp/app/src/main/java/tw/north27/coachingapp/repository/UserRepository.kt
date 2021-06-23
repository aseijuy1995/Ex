package tw.north27.coachingapp.repository

import android.content.Context
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.*
import java.util.*

class UserRepository(val service: IApiService, val context: Context) : IUserRepository {

    override suspend fun checkSignIn(): Results<SignIn> {
        return safeApiResults { service.checkSignIn() }
    }

    override suspend fun signIn(signInBody: SignInBody): Results<SignIn> {
        return safeApiResults { service.signIn(signInBody = signInBody) }
    }

    override suspend fun getUser(json: String): Results<UserInfo> {
        return safeApiResults { service.getUser(json = json) }
    }

    override suspend fun getCommentList(commentBody: CommentBody): Results<List<CommentInfo>> {
        return safeApiResults {
            service.getCommentList(commentBody = commentBody)
        }
    }
    //
    //
    //
    //
    //

    override suspend fun signOut(uuid: String, account: String): Results<SignIn> {
        return safeApiResults { service.signOut(uuid = uuid, account = account) }
    }

    override suspend fun getTeacherList(educationId: Long?, gradeId: Long?, subjectId: Long?, unitId: Long?): Results<List<UserInfo>> {
        return safeApiResults { service.getTeacherList(educationId = educationId, gradeId = gradeId, subjectId = subjectId, unitId = unitId) }
    }

    override suspend fun updateUser(
        account: String,
        bgPath: String,
        avatarPath: String,
        name: String,
        gender: Gender,
        intro: String,
        birthday: Date?,
        cellPhone: String,
        homePhone: String,
        email: String,
        school: String?,
        gradeId: Long?
    ): Results<Boolean> {
        return safeApiResults { service.updateUser(account = account, bgPath = bgPath, avatarPath = avatarPath, name = name, gender = gender, intro = intro, birthday = birthday, cellPhone = cellPhone, homePhone = homePhone, email = email, school = school, gradeId = gradeId) }
    }

}