package tw.north27.coachingapp.repository

import kotlinx.coroutines.Dispatchers
import tw.north27.coachingapp.const.IApiService
import tw.north27.coachingapp.ext.SignInResults
import tw.north27.coachingapp.ext.safeApiSignInResults
import tw.north27.coachingapp.model.result.SignInResult
import tw.north27.coachingapp.repository.inter.ISignInRepository

class SignInRepository(val service: IApiService) : ISignInRepository {

    override suspend fun checkSignInResult(account: String, password: String): SignInResults<SignInResult> {
        return service.checkSignIn(account, password).safeApiSignInResults(Dispatchers.IO)
    }


}