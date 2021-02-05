package edu.yujie.socketex.finish.inter

import edu.yujie.socketex.SignInStorage
import edu.yujie.socketex.UserBean
import edu.yujie.socketex.finish.result.InitResult
import edu.yujie.socketex.finish.result.SignInResultInfo

interface IApiRepo {

    /**
     * 初始數據
     * */
    suspend fun getInit(): InitResult

    /**
     * 檢查帳號 & 密碼
     * */
    suspend fun postCheckSignIn(signInStorage: SignInStorage): SignInResultInfo
}