package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.model.result.SignInResult
import tw.north27.coachingapp.module.http.Results

interface ISignInRepository {

    /**
     * 登入Api
     * */
    suspend fun checkSignInResult(account: String, password: String): Results<SignInResult>
}