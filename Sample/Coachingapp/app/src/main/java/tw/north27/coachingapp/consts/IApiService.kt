package tw.north27.coachingapp.consts

//https://www.jianshu.com/p/62ab11ddacc8
//https://www.huaweicloud.com/articles/138c673c96294a6661b16960ff4db613.html

import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import tw.north27.coachingapp.model.*

interface IApiService {

    /**
     * 取得更新資訊
     * @param uuid >> 唯一id（下載量 & 登入數量）
     * @param pushToken >> firebase cloud messaging（未登入可收推播）
     * */
    @GET
    suspend fun getAppConfig(
        @Query("uuid") uuid: String,
        @Query("push_token") pushToken: String
    ): AppConfig

    /**
     * 檢查登入
     *
     * 將accessToken放於header - auth作為驗證依據
     *
     * @param uuid >> 唯一id（下載量 & 登入數量）
     * @param account >> 帳號（驗證成功綁定pushToken用）
     * @param pushToken >> firebase cloud messaging token（驗證成功需刷新）
     * */
    @POST
    suspend fun checkSignIn(
        @Field("uuid") uuid: String,
        @Field("account") account: String,
        @Field("push_token") pushToken: String,
    ): SignIn

    /**
     * 登入
     * @param uuid >> 唯一id（下載量 & 登入數量）
     * @param account >> 帳號（驗證用）
     * @param password >> 密碼（驗證用）
     * @param pushToken >> firebase cloud messaging token（驗證成功需綁定帳號）
     * */
    @POST
    suspend fun signIn(
        @Field("uuid") uuid: String,
        @Field("account") account: String,
        @Field("password") password: String,
        @Field("push_token") pushToken: String
    ): SignIn

    /**
     * 登出
     * @param uuid >> 唯一id（解除綁定）
     * @param account >> 帳號（指定登出號）
     * */
    @POST
    suspend fun signOut(
        @Field("uuid") uuid: String,
        @Field("account") account: String,
    ): SignIn

//    /**
//     * 刷新token
//     * */
//    @GET
//    suspend fun refreshToken(
////        @Query("account") account: String,
////        @Query("access_token") accessToken: String,
//        @Query("refresh_token") refreshToken: String
//    ): TokenInfo
//
//    /**
//     * 獲取老師列表
//     * @param gradeId >> 學級id
//     * @param subjectId >> 科目id
//     * @param chapterId >> 章節id
//     * */
//    @GET
//    suspend fun getLoadTeacher(
//        @Query("grade_id") gradeId: Long? = null,
//        @Query("subject_id") subjectId: Long? = null,
//        @Query("chapter_id") chapterId: Long? = null
//    ): List<UserInfo>
//

    /**
     * 取得教育
     *
     * & 年級 & 科目 & 單元可一起撈取，但為雙平台邏輯相同故在server端處理
     * */
    @GET
    suspend fun getEducationList(): List<Education>

    /**
     * 取得年級
     *
     * 0 >> 預設值(全撈取)
     *
     * @param educationId >> 教育id
     * */
    @GET
    suspend fun getGradeList(
        @Query("education_id") educationId: Long? = null
    ): List<Grade>

    /**
     * 取得科目
     *
     * 0 >> 預設值(全撈取)
     *
     * @param educationId >> 教育id
     * @param gradeId >> 年級id
     * */
    @GET
    suspend fun getSubjectList(
        @Query("education_id") educationId: Long? = null,
        @Query("grade_id") gradeId: Long? = null
    ): List<Subject>

    /**
     * 取得單元
     *
     * 0 >> 預設值(全撈取)
     *
     * @param educationId >> 教育id
     * @param gradeId >> 年級id
     * @param subjectId >> 科目id
     * */

    @GET
    suspend fun getUnitList(
        @Query("education_id") educationId: Long? = null,
        @Query("grade_id") gradeId: Long? = null,
        @Query("subject_id") subjectId: Long? = null
    ): List<tw.north27.coachingapp.model.Unit>
//
//    //
//    //
//    //
//    //
//    //
//    //
//    //
//    //
//
//
//    //////////////////////////////////////////////////////////////////////////////////////////////
//    //////////////////////////////////////////////////////////////////////////////////////////////
//    //////////////////////////////////////////////////////////////////////////////////////////////
//
//
////    /**
////     * 檢查登入 & 登入
////     * */
////    @FormUrlEncoded
////    @POST("ct/api.php")
////    suspend fun getVersion(
////        @Field("cmd") cmd: String = "get_version_android",
////    ): Response<AppResult>
//
//
//    //Chat
//    /**
//     * 獲取聊天列表
//     * @param
//     * account
//     * */
//    @GET
//    suspend fun getLoadChat(): List<ChatInfo>
//
//    /**
//     * 聊天聲音開關
//     * @param
//     * chat.id / chat.isSound
//     * @return
//     * true - execute success
//     * false - execute failed
//     * */
//    @POST
//    suspend fun postSwitchChatSound(
//        @Field("chat") chat: ChatInfo
//    ): Boolean
//
//    /**
//     * 刪除聊天室
//     * @param
//     * chat.id
//     * @return
//     * true - execute success
//     * false - execute failed
//     * */
//    @DELETE
//    suspend fun deleteChatRoom(
//        @Field("chat") chat: ChatInfo
//    ): Boolean
//
//
//    /**
//     * 獲取指定聊天數據
//     * */
//    @GET
//    suspend fun getChatList(
//        @Field("chat") chat: ChatInfo
//    ): List<ChatInfo>
//
//
//    //Notify
//    /**
//     * 獲取通知
//     * */
//    @GET
//    suspend fun getLoadNotify(
//        @Query("page") page: Int
//    ): List<NotifyInfo>
//
//    /**
//     * 總通知開關
//     * response:
//     * true - execute success
//     * false - execute failed
//     * */
//    @FormUrlEncoded
//    @POST
//    suspend fun postSwitchNotify(
//        @Field("isOpen") isOpen: Boolean
//    ): Boolean
//
//    /**
//     * 通知全部讀取
//     * response:
//     * true - execute success
//     * false - execute failed
//     * */
//    @FormUrlEncoded
//    @POST
//    suspend fun postReadAllNotify(): Boolean
//
//    /**
//     * 刪除通知
//     * request:
//     * notify.id
//     * response:
//     * true - execute success
//     * false - execute failed
//     * */
//    @FormUrlEncoded
//    @DELETE
//    suspend fun deleteNotify(notify: NotifyInfo): Boolean


}