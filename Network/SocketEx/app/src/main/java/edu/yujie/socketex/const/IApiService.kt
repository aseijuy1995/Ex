package edu.yujie.socketex.const

import edu.yujie.socketex.finish.bean.InitBean
import retrofit2.http.GET
import retrofit2.http.POST

interface IApiService {

    @GET
    suspend fun getInit(): InitBean

    @POST
    suspend fun postAccount()

}