package edu.yujie.retrofitex

import retrofit2.http.GET

//https://github.com/square/retrofit
//https://square.github.io/retrofit/
//https://developer.mozilla.org/zh-TW/docs/Web/HTTP/Methods
//https://blog.csdn.net/carson_ho/article/details/73732076

//https://jsonplaceholder.typicode.com/

interface IApiService {
//    @HTTP(method = "", path = "", hasBody = true)
//
//    @GET(value = "")
//    @HEAD(value = "")
//
//    @POST(value = "")
//    @DELETE(value = "")
//    @PUT(value = "")
//    @PATCH(value = "")
//    @OPTIONS(value = "")
//
//    @Header(value = "")
//    @Headers(value = ["Host: <calculated when request is sent>", "User-Agent: PostmanRuntime/7.26.8"])
//    @HeaderMap
//
//    @Path(value = "", encoded = true)
//    @Url
//    @Body
//
//    @Query(value="",encoded = true)
//    @QueryMap(encoded = true)
//    @QueryName(encoded = true)
//
//    @FormUrlEncoded
//    @Field(value = "", encoded = true)
//    @FieldMap(encoded = true)
//
//    @Multipart
//    @Part(value="",encoding = "")
//    @PartMap(encoding = "")
//
//    @Streaming
//
//    @Tag

    @GET(value = "aseijuy1995/json/db")
    suspend fun getGithubDatas(): GithubBean

    @GET(value = "posts")
    suspend fun getOtherGithubData(): List<OtherGithubBean>

}