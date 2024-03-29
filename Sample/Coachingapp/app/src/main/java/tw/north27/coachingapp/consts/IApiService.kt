package tw.north27.coachingapp.consts

//https://www.jianshu.com/p/62ab11ddacc8
//https://www.huaweicloud.com/articles/138c673c96294a6661b16960ff4db613.html


import com.yujie.core_lib.http.okhttp.TokenInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.AskRoomInfo
import tw.north27.coachingapp.model.request.*
import tw.north27.coachingapp.model.response.*

interface IApiService {

    //LaunchActivity
    /**
     * 刷新token
     * @param tokenRequest >> TokenRequest::class.java
     * */
    @POST
    fun refreshToken(@Body tokenRequest: TokenRequest): TokenInfo

    /**
     * 獲取App初始設定
     * @param appConfigRequest >> AppConfigRequest::class.java
     * */
    @POST
    suspend fun fetchAppConfig(@Body appConfigRequest: AppConfigRequest): AppConfig

    /**
     * 檢查登入
     * @header Bearer accessToken
     * @param signRequest >> SignRequest::class.java
     * */
    @POST
    suspend fun checkSign(@Body signRequest: SignRequest): SignInfo

    /**
     * 登入
     * @param signInRequest >> SignInRequest::class.java
     * */
    @POST
    suspend fun signIn(@Body signInRequest: SignInRequest): SignInfo

    //Launch2Activity
    /**
     * 獲取教育參數
     * @header Bearer accessToken
     * */
    @GET
    suspend fun fetchEducation(): Education

    //CoachingFragment
    /**
     * 獲取老師列表
     * @param teacherRequest >> TeacherRequest::class.java
     * */
    @POST
    suspend fun fetchTeacherList(@Body teacherRequest: TeacherRequest): List<ClientInfo>

    //AskFragment
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
     * @param soundRequest >> SoundRequest::class.java
     * */
    @PUT
    suspend fun updateAskRoomSound(@Body soundRequest: SoundRequest): SoundResponse

    /**
     * 快速篩選可配合的老師
     * @header Bearer accessToken
     * @param pairRequest >> PairRequest::class.java
     * */
    @POST
    suspend fun fetchTeacherPair(@Body pairRequest: PairRequest): ClientInfo?

    /**
     * 取得提問室資訊
     * @header Bearer accessToken
     * @param setupAskRoomRequest >> SetUpAskRoomRequest::class.java
     * */
    @POST
    suspend fun findAskRoom(@Body setupAskRoomRequest: SetupAskRoomRequest): AskRoomResponse

    /**
     * 建立提問室資訊
     * @header Bearer accessToken
     * @param setupAskRoomRequest >> SetUpAskRoomRequest::class.java
     * */
    @POST
    suspend fun setupAskRoom(@Body setupAskRoomRequest: SetupAskRoomRequest): AskRoom

    /**
     * 獲取提問室的列表詳細資訊
     * @header Bearer accessToken
     * @param askRoomInfoRequest >> AskRoomInfoRequest::class.java
     * */
    @POST
    suspend fun fetchAskRoomInfoList(@Body askRoomInfoRequest: AskRoomInfoRequest): List<AskRoomInfo>
    //

    /**
     * 獲取用戶資訊
     * @header Bearer accessToken
     * @param clientRequest >> ClientRequest::class.java
     * */
    @POST
    suspend fun fetchClient(@Body clientRequest: ClientRequest): ClientInfo

    /**
     * 更新用戶資訊
     * @header Bearer accessToken
     * @param updateClientRequest >> UpdateClientRequest::class.java
     * */
    @PUT
    suspend fun updateClient(@Body updateClientRequest: UpdateClientRequest): UpdateClientResponse

    /**
     * 取得評論列表 - 預設無參數，依據index、num回傳數據
     * @header Bearer accessToken
     * @param commentRequest >> CommentRequest::class.java
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