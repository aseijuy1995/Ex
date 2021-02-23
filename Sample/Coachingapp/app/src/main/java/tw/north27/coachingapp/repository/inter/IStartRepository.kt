package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.ext.Results
import tw.north27.coachingapp.model.result.SignInResult
import tw.north27.coachingapp.model.result.VersionResult

interface IStartRepository {

    /**
     * 獲得起始數據
     * */
    suspend fun getVersionCtrlResult(): Results<VersionResult>

    /**
     * 檢查登入狀態
     * */
    suspend fun checkSignInResult(account: String): Results<SignInResult>

}