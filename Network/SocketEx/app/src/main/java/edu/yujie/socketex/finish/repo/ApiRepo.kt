package edu.yujie.socketex.finish.repo

import edu.yujie.socketex.SignInStorage
import edu.yujie.socketex.const.IApiService
import edu.yujie.socketex.finish.inter.IApiRepo
import edu.yujie.socketex.finish.result.InitResult
import edu.yujie.socketex.finish.result.SignInResultInfo

class ApiRepo(private val service: IApiService) : IApiRepo {

    override suspend fun getInit(): InitResult = service.getInit()

    override suspend fun postCheckSignIn(signInStorage: SignInStorage): SignInResultInfo = service.postSignIn(signInStorage)


}