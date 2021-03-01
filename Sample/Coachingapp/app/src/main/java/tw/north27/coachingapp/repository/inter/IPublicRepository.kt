package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.consts.AppResult
import tw.north27.coachingapp.model.result.SignInResult
import tw.north27.coachingapp.model.result.UpdateInfo
import tw.north27.coachingapp.module.http.Results

interface IStartRepository {

    suspend fun getVersionCtrlResult(): Results<UpdateInfo>

    /**
     * 檢查登入狀態
     * */
    suspend fun checkSignInResult(account: String): Results<SignInResult>


    //    suspend fun postVersion(cmd: String): AppResult
    suspend fun postVersion(cmd: String): Results<AppResult>
}