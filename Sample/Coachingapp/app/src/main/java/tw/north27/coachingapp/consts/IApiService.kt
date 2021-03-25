package tw.north27.coachingapp.consts

//https://www.jianshu.com/p/62ab11ddacc8
//https://www.huaweicloud.com/articles/138c673c96294a6661b16960ff4db613.html

import retrofit2.Response
import retrofit2.http.*
import tw.north27.coachingapp.model.result.*

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
    suspend fun getAppConfig(
        @Query("fcmToken") fcmToken: String
    ): AppConfig

    /**
     * 檢查登入
     * */
    @FormUrlEncoded
    @POST
    suspend fun postCheckSignIn(
        @Field("account") account: String,
        @Field("deviceId") deviceId: String,
        @Field("fcmToken") fcmToken: String
    ): Response<SignInInfo>


    /**
     * 登入
     * */
    @FormUrlEncoded
    @POST
    suspend fun postSignIn(
        @Field("account") account: String,
        @Field("password") password: String,
        @Field("deviceId") deviceId: String,
        @Field("fcmToken") fcmToken: String
    ): Response<SignInInfo>


//    /**
//     * 檢查登入 & 登入
//     * */
//    @FormUrlEncoded
//    @POST("ct/api.php")
//    suspend fun getVersion(
//        @Field("cmd") cmd: String = "get_version_android",
//    ): Response<AppResult>


    //Chat
    /**
     * 獲取聊天列表
     * @param
     * account
     * */
    @GET
    suspend fun getLoadChat(): List<ChatInfo>

    /**
     * 聊天聲音開關
     * @param
     * chat.id / chat.isSound
     * @return
     * true - execute success
     * false - execute failed
     * */
    @POST
    suspend fun postSwitchChatSound(
        @Field("chat") chat: ChatInfo
    ): Boolean

    /**
     * 刪除聊天室
     * @param
     * chat.id
     * @return
     * true - execute success
     * false - execute failed
     * */
    @DELETE
    suspend fun deleteChatRoom(
        @Field("chat") chat: ChatInfo
    ): Boolean


    /**
     * 獲取指定聊天數據
     * */
    @GET
    suspend fun getChatListFromChat(
        @Field("chat") chat: ChatInfo
    ): List<ChatInfo>


    //Notify
    /**
     * 獲取通知
     * */
    @GET
    suspend fun getLoadNotify(
        @Query("page") page: Int
    ): List<NotifyInfo>

    /**
     * 總通知開關
     * response:
     * true - execute success
     * false - execute failed
     * */
    @FormUrlEncoded
    @POST
    suspend fun postSwitchNotify(
        @Field("isOpen") isOpen: Boolean
    ): Boolean

    /**
     * 通知全部讀取
     * response:
     * true - execute success
     * false - execute failed
     * */
    @FormUrlEncoded
    @POST
    suspend fun postReadAllNotify(): Boolean

    /**
     * 刪除通知
     * request:
     * notify.id
     * response:
     * true - execute success
     * false - execute failed
     * */
    @FormUrlEncoded
    @DELETE
    suspend fun deleteNotify(notify: NotifyInfo): Boolean


}