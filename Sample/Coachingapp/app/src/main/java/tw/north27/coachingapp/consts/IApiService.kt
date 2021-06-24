package tw.north27.coachingapp.consts

//https://www.jianshu.com/p/62ab11ddacc8
//https://www.huaweicloud.com/articles/138c673c96294a6661b16960ff4db613.html

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.request.CommentRequest
import tw.north27.coachingapp.model.request.SignInRequest
import tw.north27.coachingapp.model.request.SignOutRequest
import tw.north27.coachingapp.model.request.UpdateUserRequest

interface IApiService {

    /**
     * 取得教育數據
     * */
    @GET
    suspend fun getEducationData(): EducationData

    /**
     * 取得App設定資訊
     * */
    @GET
    suspend fun getAppConfig(): AppConfig

    /**
     * 檢查登入
     * @header accessToken
     * */
    @POST
    suspend fun checkSignIn(): SignIn

    /**
     * 登入
     * @param signInRequest >> SignInRequest::class.java
     * */
    @POST
    suspend fun signIn(@Body signInRequest: SignInRequest): SignIn

    /**
     * 登出
     * @header accessToken
     * @param signOutRequest >> SignOutRequest::class.java
     * */
    @POST
    suspend fun signOut(@Body signOutRequest: SignOutRequest): SignIn

    /**
     * 取得用戶資訊
     * @header accessToken
     * @param json >> account >> 帳號
     * */
    @POST
    suspend fun getUser(@Body json: String): UserInfo

    /**
     * 更新用戶資訊
     * @header accessToken
     * @param updateUserRequest >> UpdateUserRequest::class.java
     * */
    @POST
    suspend fun updateUser(@Body updateUserRequest: UpdateUserRequest): Boolean

    /**
     * 取得評論列表 - 預設無參數，依據index、num回傳數據
     * @header accessToken
     * @param commentRequest >> CommentBody::class.java
     * */
    @POST
    suspend fun getCommentList(@Body commentRequest: CommentRequest): List<CommentInfo>


    //
    //
    //
    //
    //
    //
//    /**
//     * 取得教育列表（回傳全部）
//     * */
//    @GET
//    suspend fun getEducationList(): List<Education>
//
//    /**
//     * 取得年級列表 - 預設無參數（回傳全部）
//     * @param educationId? >> 教育id
//     * */
//    @GET
//    suspend fun getGradeList(
//        @Query("education_id") educationId: Long? = null
//    ): List<Grade>
//
//    /**
//     * 取得科目列表 - 預設無參數（回傳全部）
//     * @param educationId? >> 教育id
//     * @param gradeId? >> 年級id
//     * */
//    @GET
//    suspend fun getSubjectList(
//        @Query("education_id") educationId: Long? = null,
//        @Query("grade_id") gradeId: Long? = null
//    ): List<Subject>
//
//    /**
//     * 取得單元列表 - 預設無參數（回傳全部）
//     * @param educationId? >> 教育id
//     * @param gradeId? >> 年級id
//     * @param subjectId? >> 科目id
//     * */
//    @GET
//    suspend fun getUnitList(
//        @Query("education_id") educationId: Long? = null,
//        @Query("grade_id") gradeId: Long? = null,
//        @Query("subject_id") subjectId: Long? = null
//    ): List<tw.north27.coachingapp.model.Units>

//    /**
//     * 刷新token
//     * */
//    @GET
//    suspend fun refreshToken(
////        @Query("account") account: String,
////        @Query("access_token") accessToken: String,
//        @Query("refresh_token") refreshToken: String
//    ): TokenInfo

    /**
     * 取得老師列表
     * @param educationId >> 教育id
     * @param gradeId >> 年級id
     * @param subjectId >> 科目id
     * @param unitId >> 單元id
     * */
    @POST
    suspend fun getTeacherList(
        @Query("education_id") educationId: Long? = null,
        @Query("grade_id") gradeId: Long? = null,
        @Query("subject_id") subjectId: Long? = null,
        @Query("unit_id") unitId: Long? = null
    ): List<UserInfo>


//    /**
//     * 取得未回覆列表(未加載) - 依據時間近至遠撈回，未指定則撈回全部
//     *
//     * @param account >> 帳號
//    //     * @param score >> 評分
//     * @param education_id >> 教育id
//     * @param grade_id >> 年級id
//     * @param subject_id >> 科目id
//     * @param unit_id >> 單元id
//     * @param index >> 索引
//     * @param num >> 筆數
//     * */
//    @POST
//    suspend fun getReplyList(
//        @Field("account") account: String,
////        @Field("score") score: Double,
//        @Field("education_id") educationId: Long? = null,
//        @Field("grade_id") gradeId: Long? = null,
//        @Field("subject_id") subjectId: Long? = null,
//        @Field("unit_id") unitId: Long? = null,
//        @Field("index") index: Int,
//        @Field("num") num: Int
//    ): List<CommentInfo>

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