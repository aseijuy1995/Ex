package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.ext.SignInResults
import tw.north27.coachingapp.model.result.SignInResult

interface ISignInRepository {

    /**
     * 登入Api
     * */
    suspend fun checkSignInResult(account: String, password: String): SignInResults<SignInResult>
}