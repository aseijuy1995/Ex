package tw.north27.coachingapp.repository

import android.content.Context
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.Gender
import tw.north27.coachingapp.model.SignIn
import tw.north27.coachingapp.model.UserInfo
import java.util.*

class UserRepository(val service: IApiService, val context: Context) : IUserRepository {

    override suspend fun checkSignIn(account: String, pushToken: String): Results<SignIn> {
        return safeApiResults { service.checkSignIn(account = account, pushToken = pushToken) }
    }

    override suspend fun signIn(uuid: String, account: String, password: String, pushToken: String): Results<SignIn> {
        return safeApiResults { service.signIn(uuid = uuid, account = account, password = password, pushToken = pushToken) }
    }

    override suspend fun signOut(uuid: String, account: String): Results<SignIn> {
        return safeApiResults { service.signOut(uuid = uuid, account = account) }
    }

    override suspend fun getTeacherList(educationId: Long?, gradeId: Long?, subjectId: Long?, unitId: Long?): Results<List<UserInfo>> {
        return safeApiResults { service.getTeacherList(educationId = educationId, gradeId = gradeId, subjectId = subjectId, unitId = unitId) }
    }

    override suspend fun getUser(account: String): Results<UserInfo> {
        return safeApiResults { service.getUser(account = account) }
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