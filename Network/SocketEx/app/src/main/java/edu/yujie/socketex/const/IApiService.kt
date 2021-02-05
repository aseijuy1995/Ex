package edu.yujie.socketex.const

import edu.yujie.socketex.SignInStorage
import edu.yujie.socketex.UserBean
import edu.yujie.socketex.finish.result.InitResult
import edu.yujie.socketex.finish.result.SignInResultInfo
import retrofit2.http.GET
import retrofit2.http.POST

interface IApiService {

    @GET
    suspend fun getInit(): InitResult

    @POST
    suspend fun postSignIn(signInStorage:SignInStorage): SignInResultInfo

}