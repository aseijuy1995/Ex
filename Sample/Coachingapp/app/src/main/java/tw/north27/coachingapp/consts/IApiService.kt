package tw.north27.coachingapp.consts

//https://www.jianshu.com/p/62ab11ddacc8
//https://www.huaweicloud.com/articles/138c673c96294a6661b16960ff4db613.html

import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import tw.north27.coachingapp.model.*
import java.util.*

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

    /**
     * 取得教育列表
     * */
    @GET
    suspend fun getEducationList(): List<Education>

    /**
     * 取得年級列表
     * @param educationId >> 教育id
     * */
    @GET
    suspend fun getGradeList(
        @Query("education_id") educationId: Long? = null
    ): List<Grade>

    /**
     * 取得科目列表
     * @param educationId >> 教育id
     * @param gradeId >> 年級id
     * */
    @GET
    suspend fun getSubjectList(
        @Query("education_id") educationId: Long? = null,
        @Query("grade_id") gradeId: Long? = null
    ): List<Subject>

    /**
     * 取得單元列表
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

    /**
     * 取得用戶資訊
     * @param account >> 帳號
     * */
    @POST
    suspend fun getUser(
        @Field("account") account: String
    ): UserInfo

    /**
     * 更新用戶資訊
     * @param account >> 帳號
     * @param bgPath >> 背景
     * @param avatarPath >> 頭貼
     * @param name >> 名稱
     * @param gender >> 性別
     * @param intro >> 簡介
     * @param birthday >> 生日
     * @param cellPhone >> 電話
     * @param homePhone >> 家電
     * @param email >> Email
     * @param school >> 學校
     * @param gradeId >> 年級Id
     * */
    @POST
    suspend fun updateUser(
        @Field("account") account: String,
        @Field("bg_path") bgPath: String,
        @Field("avatar_path") avatarPath: String,
        @Field("name") name: String,
        @Field("gender") gender: Gender,
        @Field("intro") intro: String,
        @Field("birthday") birthday: Date? = null,
        @Field("cell_phone") cellPhone: String,
        @Field("home_phone") homePhone: String,
        @Field("email") email: String,
        @Field("school") school: String? = null,
        @Field("grade_id") gradeId: Long? = null
    ): Boolean

    /**
     * 取得評論列表(未加載) - 依據時間近至遠撈回，未指定則撈回全部，有指定依據指定的education_id、grade_id、subject_id、unit_id取出數據
     *
     * @param account >> 帳號
    //     * @param score >> 評分
     * @param education_id >> 教育id
     * @param grade_id >> 年級id
     * @param subject_id >> 科目id
     * @param unit_id >> 單元id
     * @param index >> 索引
     * @param num >> 筆數
     * */
    @POST
    suspend fun getCommentList(
        @Field("account") account: String,
//        @Field("score") score: Double,
        @Field("education_id") educationId: Long? = null,
        @Field("grade_id") gradeId: Long? = null,
        @Field("subject_id") subjectId: Long? = null,
        @Field("unit_id") unitId: Long? = null,
        @Field("index") index: Int,
        @Field("num") num: Int
    ): List<CommentInfo>

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