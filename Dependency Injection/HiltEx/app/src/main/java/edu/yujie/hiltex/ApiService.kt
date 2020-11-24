package edu.yujie.hiltex

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("ct/{path}")
    suspend fun postAppResult(
        @Path("path") path: String = "api.php",
        @Field("cmd") cmd: String = "get_version_android"
    ): AppResult

}