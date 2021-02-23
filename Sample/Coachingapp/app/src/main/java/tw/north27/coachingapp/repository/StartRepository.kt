package tw.north27.coachingapp.repository

import kotlinx.coroutines.Dispatchers
import tw.north27.coachingapp.const.IApiService
import tw.north27.coachingapp.ext.Results
import tw.north27.coachingapp.ext.safeApiResults
import tw.north27.coachingapp.model.result.SignInResult
import tw.north27.coachingapp.model.result.VersionResult
import tw.north27.coachingapp.repository.inter.IStartRepository

class StartRepository(val service: IApiService) : IStartRepository {

    override suspend fun getVersionCtrlResult(): Results<VersionResult> {
        return service.getVersionCtrlResult().safeApiResults(Dispatchers.IO)
    }

    override suspend fun checkSignInResult(account: String): Results<SignInResult> {
        return service.checkSignIn(account).safeApiResults(Dispatchers.IO)
    }

}