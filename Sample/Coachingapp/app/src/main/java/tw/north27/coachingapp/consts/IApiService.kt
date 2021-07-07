package tw.north27.coachingapp.consts

//https://www.jianshu.com/p/62ab11ddacc8
//https://www.huaweicloud.com/articles/138c673c96294a6661b16960ff4db613.html

import com.yujie.utilmodule.http.RefreshTokenResponse
import retrofit2.http.*
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.request.*
import tw.north27.coachingapp.model.response.*

interface IApiService {

    /**
     * 刷新token
     * */
    @POST
    @FormUrlEncoded
    fun refreshToken(@Field("refresh_token") refreshToken: String): RefreshTokenResponse

    /**
     * 獲取App初始設定
     *  @param deviceType >> 設備類型(android)
     * */
    @GET
    @FormUrlEncoded
    suspend fun fetchAppConfig(@Field("device_type") deviceType: String): AppConfig

    /**
     * 檢查登入
     * @header Bearer accessToken
     * */
    @POST
    @FormUrlEncoded
    suspend fun checkSignIn(@Field("account") account: String): SignIn

    /**
     * 登入
     * @param signInRequest >> SignInRequest::class.java
     * */
    @POST
    suspend fun signIn(@Body signInRequest: SignInRequest): SignIn

    /**
     * 獲取教育參數
     * @header Bearer accessToken
     * */
    @GET
    suspend fun fetchEducation(): Education

    /**
     * 獲取提問室列表
     * @header Bearer accessToken
     * @param askRoomRequest >> AskRoomRequest::class.java
     * */
    @POST
    suspend fun fetchAskRoomList(@Body askRoomRequest: AskRoomRequest): List<AskRoom>

    /**
     * 更新提問室推播開關
     * @header Bearer accessToken
     * @param pushRequest >> PushRequest::class.java
     * */
    @PUT
    suspend fun updateAskRoomPush(@Body pushRequest: PushRequest): PushResponse

    /**
     * 更新提問室推播聲音開關
     * @header Bearer accessToken
     * @param pushRequest >> PushRequest::class.java
     * */
    @PUT
    suspend fun updateAskRoomSound(@Body soundRequest: SoundRequest): SoundResponse

    /**
     * 獲取房間提問列表
     * @header Bearer accessToken
     * @param askInfoRequest >> AskRoomInfoRequest::class.java
     * */
    @POST
    suspend fun fetchAskInfoList(@Body askInfoRequest: AskInfoRequest): List<AskInfo>
    //

    /**
     * 獲取用戶資訊
     * @header Bearer accessToken
     * @param account >> 帳號
     * */
    @POST
    suspend fun fetchUser(@Field("account") account: String): UserInfo

    /**
     * 登出
     * @header accessToken
     * @param signOutRequest >> SignOutRequest::class.java
     * */
    @POST
    suspend fun signOut(@Body signOutRequest: SignOutRequest): SignIn

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
    suspend fun fetchCommentList(@Body commentRequest: CommentRequest): List<CommentInfo>

    /**
     * 取得公有數據
     * */
    @GET
    suspend fun fetchPublicData(): PublicDataResponse

    /**
     * 發送反映
     * @header accessToken
     * @param reflectRequest >> ReflectRequest::class.java
     * */
    @POST
    suspend fun insertReflect(@Body reflectRequest: ReflectRequest): ReflectResponse


    /**
     * 取得老師列表
     * @param teacherRequest >> TeacherRequest::class.java
     * */
    @POST
    suspend fun fetchTeacherList(@Body teacherRequest: TeacherRequest): List<UserInfo>

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
//
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