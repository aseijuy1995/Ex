package edu.yujie.socketex.const

import edu.yujie.socketex.bean.InitSettingBean
import retrofit2.http.GET

interface IApiService {

    @GET
    suspend fun getInit(): InitSettingBean

}