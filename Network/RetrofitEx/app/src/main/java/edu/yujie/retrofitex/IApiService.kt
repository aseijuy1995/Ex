package edu.yujie.retrofitex

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

//https://developer.mozilla.org/zh-TW/docs/Web/HTTP/Methods
//https://jsonplaceholder.typicode.com/

interface IApiService {

    @HTTP(method = "", path = "", hasBody = true)
    fun http()

    @GET(value = "")
    fun get()

    @FormUrlEncoded
    @POST(value = "ct/api.php")
    suspend fun post(@Field("cmd") cmd: String = "get_version_android"): AppResult

    @PUT(value = "")
    fun put()

    @PATCH(value = "")
    fun patch()

    @DELETE(value = "")
    fun delete()

    @OPTIONS(value = "")
    fun options()

    @HEAD(value = "")
    fun head()

//    @Multipart
//    @Part
//    @Headers()
//    @HeaderMap
}