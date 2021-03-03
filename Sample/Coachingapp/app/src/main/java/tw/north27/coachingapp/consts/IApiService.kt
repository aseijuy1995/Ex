package tw.north27.coachingapp.consts

//https://www.jianshu.com/p/62ab11ddacc8
//https://www.huaweicloud.com/articles/138c673c96294a6661b16960ff4db613.html

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import tw.north27.coachingapp.model.result.AppConfig
import tw.north27.coachingapp.model.result.NotifyInfo
import tw.north27.coachingapp.model.result.SignInInfo
import tw.north27.coachingapp.model.result.TokenInfo

interface IApiService {

    /**
     * 刷新token
     * */
    @FormUrlEncoded
    @POST
    suspend fun refreshToken(@Field("refresh_token") refreshToken: String): TokenInfo

    /**
     * 取得更新資訊
     * */
    @GET
    suspend fun getAppConfig(): AppConfig

    /**
     * 檢查登入
     * */
    @FormUrlEncoded
    @POST
    suspend fun postCheckSignIn(
        @Field("account") account: String,
        @Field("deviceId") deviceId: String
    ): Response<SignInInfo>


    /**
     * 登入
     * */
    @FormUrlEncoded
    @POST
    suspend fun postSignIn(
        @Field("account") account: String,
        @Field("password") password: String,
        @Field("deviceId") deviceId: String
    ): Response<SignInInfo>


//    /**
//     * 檢查登入 & 登入
//     * */
//    @FormUrlEncoded
//    @POST("ct/api.php")
//    suspend fun getVersion(
//        @Field("cmd") cmd: String = "get_version_android",
//    ): Response<AppResult>


    //Notify
    /**
     * 獲取通知
     * */
    @FormUrlEncoded
    @POST
    suspend fun postLoadNotify(
        @Field("page") page: Int
    ): List<NotifyInfo>

}