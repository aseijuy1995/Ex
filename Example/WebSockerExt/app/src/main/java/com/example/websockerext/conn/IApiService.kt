package com.example.websockerext.conn

import com.example.websockerext.AppResult
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface IApiService {

    //now
    suspend fun postAppResult(
        @Path("path") path: String = "api.php",
        @Field("cmd") cmd: String = "get_version_android"
    ): AppResult

    //before
    @FormUrlEncoded
    @POST("ct/{path}")
    fun postAppResultDef(
        @Path("path") path: String = "api.php",
        @Field("cmd") cmd: String = "get_version_android"
    ): Deferred<AppResult>

}