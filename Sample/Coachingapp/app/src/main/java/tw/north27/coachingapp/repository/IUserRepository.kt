package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.Gender
import tw.north27.coachingapp.model.SignIn
import tw.north27.coachingapp.model.UserInfo
import java.util.*

interface IUserRepository {

    suspend fun checkSignIn(uuid: String, account: String, pushToken: String): Results<SignIn>

    suspend fun signIn(uuid: String, account: String, password: String, pushToken: String): Results<SignIn>

    suspend fun signOut(uuid: String, account: String): Results<SignIn>

    suspend fun getTeacherList(educationId: Long? = null, gradeId: Long? = null, subjectId: Long? = null, unitId: Long? = null): Results<List<UserInfo>>

    suspend fun getUser(account: String): Results<UserInfo>

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