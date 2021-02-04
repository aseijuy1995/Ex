package edu.yujie.socketex.finish.inter

import edu.yujie.socketex.UserBean
import edu.yujie.socketex.finish.bean.InitBean

interface IApiRepo {

    /**
     * 初始數據
     * */
    suspend fun getInit(): InitBean

    /**
     * 檢查帳號 & 密碼
     * */
    suspend fun postCheckSignIn(account: String, authToken: String, deviceToken: String): UserBean
}