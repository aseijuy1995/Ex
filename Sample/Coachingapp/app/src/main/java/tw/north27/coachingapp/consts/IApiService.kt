package tw.north27.coachingapp.consts

//https://www.jianshu.com/p/62ab11ddacc8
//https://www.huaweicloud.com/articles/138c673c96294a6661b16960ff4db613.html

import retrofit2.Response
import retrofit2.http.*
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.result.*

interface IApiService {

    /**
     * 取得更新資訊
     * @param uuid >> 唯一id（下載量 & 登入數量）
     * @param fcmToken >> firebase cloud messaging（未登入可收推播）
     * */
    @GET
    suspend fun getAppConfig(
        @Query("uuid") uuid: String,
        @Query("fcm_token") fcmToken: String
    ): AppConfig

    /**
     * 檢查登入
     * @param uuid >> 唯一id（下載量 & 登入數量）
     * @param account >> 帳號（驗證用）
     * @param accessToken >> 訪問token（驗證用）
     * @param fcmToken >> firebase cloud messaging token（驗證成功需刷新）
     * */
    //走POST將account&accessToken存於header即可
    @POST
    suspend fun checkSignIn(
        @Field("uuid") uuid: String,
        @Field("account") account: String,
        @Field("access_token") accessToken: String,
        @Field("fcm_token") fcmToken: String,
    ): Response<SignIn>

    /**
     * 登入
     * @param uuid >> 唯一id（下載量 & 登入數量）
     * @param account >> 帳號（驗證用）
     * @param password >> 密碼（驗證用）
     * @param fcmToken >> firebase cloud messaging token（驗證成功需綁定帳號）
     * */
    @GET
    suspend fun signIn(
        @Query("uuid") uuid: String,
        @Query("account") account: String,
        @Query("password") password: String,
        @Query("fcmToken") fcmToken: String
    ): Response<SignIn>

    /**
     * 登出
     * @param uuid >> 唯一id（解除綁定）
     * @param account >> 帳號（指定登出號）
     * */
    @GET
    suspend fun signOut(
        @Query("uuid") deviceId: String,
        @Query("account") account: String,
    ): SignIn

    /**
     * 刷新token
     * */
    @GET
    suspend fun refreshToken(
//        @Query("account") account: String,
//        @Query("access_token") accessToken: String,
        @Query("refresh_token") refreshToken: String
    ): TokenInfo

    /**
     * 獲取老師列表
     * */
    @GET
    suspend fun getLoadTeacher(): List<UserInfo>

    /**
     * 取得學級
     * */
    @GET
    suspend fun getGrade(): List<Grade>

    /**
     * 取得科目
     * @param gradeId >> 學級id
     * */
    @GET
    suspend fun getSubject(
        @Query("grade_id") gradeId: Long? = 0,
    ): List<Subject>

    /**
     * 取得單元
     * @param gradeId >> 學級id
     * @param subjectId >> 科目id
     * */
    @GET
    suspend fun getChapter(
        @Query("grade_id") gradeId: Long? = 0,
        @Query("subject_id") subjectId: Long? = 0
    ): List<Chapter>

    //
    //
    //
    //
    //
    //
    //
    //


    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////


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
    suspend fun getChatList(
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