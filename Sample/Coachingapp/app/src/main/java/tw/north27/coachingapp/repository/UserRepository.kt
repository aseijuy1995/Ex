package tw.north27.coachingapp.repository

import android.content.Context
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.SignIn
import tw.north27.coachingapp.model.UserInfo

class UserRepository(val service: IApiService, val context: Context) : IUserRepository {

    override suspend fun checkSignIn(uuid: String, account: String, pushToken: String): Results<SignIn> {
        return safeApiResults { service.checkSignIn(uuid = uuid, account = account, pushToken = pushToken) }
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

}