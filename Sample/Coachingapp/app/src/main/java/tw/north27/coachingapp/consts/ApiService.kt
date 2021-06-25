package tw.north27.coachingapp.consts

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yujie.utilmodule.util.logD
import kotlinx.coroutines.delay
import retrofit2.http.Body
import retrofit2.http.Query
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.request.*
import tw.north27.coachingapp.model.response.PublicDataResponse
import tw.north27.coachingapp.model.response.ReflectResponse
import java.util.*

class ApiService(val cxt: Context) : IApiService {

    override suspend fun fetchEducationData(): EducationData {
        delay(500)
        return EducationData(
            educationList = educationListTest,
            gradeList = gradeListTest,
            subjectList = subjectListTest,
            unitList = unitListTest
        )
    }

    override suspend fun fetchAppConfig(): AppConfig {
        delay(1500)
        return AppConfig(
            appCode = AppCode.RUN.code,
            runInfo = RunInfo(
                versionName = "1.0.0",
                url = "https://play.google.com/store/apps/details?id=ojisan.Droid&hl=zh_TW",
                content = "1. 資料顯示錯誤\n" +
                        "2. 通知推送不即時\n" +
                        "3. 部分機型閃退\n" +
                        "4. 更新通知描述1\n" +
                        "5. 更新通知描述2\n" +
                        "5. 更新通知描述3",
                size = "5.7M",
                isCompulsory = false
            )
        )
//        return AppConfig(
//            appCode = AppCode.MAINTAIN.code,
//            maintainInfo = MaintainInfo(
//                content = "1. 伺服器遭受攻擊。\n" +
//                        "2. 增加監控、效能分析、執行網路維護。\n" +
//                        "3. 描述統一規範化。\n" +
//                        "4. 維護通知描述1\n" +
//                        "5. 維護通知描述2\n" +
//                        "6. 維護通知描述3",
//                time = SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-12-21 13:00"),
//            )
//        )
    }

    override suspend fun checkSignIn(): SignIn {
        delay(1500)
        return if (accessTokenTest == accessTokenTest) {
            SignIn(
                signInCode = SignInCode.SIGN_IN_SUC.code,
                signInInfo = SignInInfo(
                    userInfo = userInfoTest,
                    expireTime = expireTimeTest,
                    accessToken = accessTokenTest,
                    refreshToken = refreshTokenTest,
                    isFirst = isFirstTest,
                    pushToken = pushTokenTest,
                    msg = "即將登入..."
                )
            )
        } else {
            /**
             * 被封鎖
             * 其他端登入
             * */
            SignIn(
                signInCode = SignInCode.SIGN_IN_FAIL.code,
                signInInfo = SignInInfo(
                    msg = "密碼已被修改，請重新登入!"
                )
            )
        }
    }

    override suspend fun signIn(@Body signInRequest: SignInRequest): SignIn {
        delay(1500)
        val uuid = signInRequest.uuid
        val account = signInRequest.account
        val password = signInRequest.password
        val pushToken = signInRequest.pushToken
        logD(
            "uuid = $uuid\n" +
                    "account = $account\n" +
                    "password = $password\n" +
                    "pushToken = $pushToken"
        )
        if (account == accountTest && password == passwordTest) {
            uuidTest = uuid
            pushTokenTest = pushToken
            return SignIn(
                signInCode = SignInCode.SIGN_IN_SUC.code,
                signInInfo = SignInInfo(
                    userInfo = userInfoTest,
                    expireTime = expireTimeTest,
                    accessToken = accessTokenTest,
                    refreshToken = refreshTokenTest,
                    isFirst = isFirstTest,
                    pushToken = pushTokenTest,
                    msg = "即將登入..."
                )
            ).also {
                isFirstTest = false
            }
        } else {
            return SignIn(
                signInCode = SignInCode.SIGN_OUT_FAILED.code,
                signInInfo = SignInInfo(
                    msg = "帳號、密碼錯誤，請再試一次!"
                )
            )
        }
    }

    override suspend fun signOut(@Body signOutRequest: SignOutRequest): SignIn {
        delay(1500)
        if (uuidTest == signOutRequest.uuid) uuidTest = ""
        return if (signOutRequest.account == accountTest)
            SignIn(
                signInCode = SignInCode.SIGN_OUT_SUCCESS.code,
                signOutInfo = SignOutInfo(
                    msg = "成功登出!"
                )
            )
        else
            SignIn(
                signInCode = SignInCode.SIGN_OUT_FAILED.code,
                signOutInfo = SignOutInfo(
                    msg = "登出失敗，請刪除APP並重新下載!"
                )
            )
    }

    override suspend fun fetchUser(@Body json: String): UserInfo {
        delay(1500)
        val map = Gson().fromJson<HashMap<String, String>>(json, object : TypeToken<HashMap<String, String>>() {}.type)
        val account = map["account"]
        return if (account == accountTest)
            userInfoTest
        else {
            logD("Not find user")
            userInfoTest
        }
    }


    //
    //
    //
    //
    //
    //
    //
    //
//    override suspend fun refreshToken(@Query(value = "account") account: String, @Query(value = "access_token") accessToken: String, @Query(value = "refresh_token") refreshToken: String): TokenInfo {
//        delay(500)
//        return TokenInfo(
//            accessToken = "accessToken002",
//            refreshToken = "refreshToken002"
//        )
//    }
    //
    //
    //
    //
    //
    //

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

//
//
//        )

//
//
//    var deleteNotify: NotifyInfo? = null
//    var isReadAllNotify: Boolean? = null
//

    override suspend fun getTeacherList(
        @Query("education_id") educationId: Long?,
        @Query("grade_id") gradeId: Long?,
        @Query("subject_id") subjectId: Long?,
        @Query("unit_id") unitId: Long?
    ): List<UserInfo> {
        delay(1500)
        return if (educationId == null && gradeId == null && subjectId == null && unitId == null)
            teacherInfoListTest
        else if (educationId == null && gradeId == null && subjectId == null)
            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.id == unitId }?.size ?: 0 > 0 }
        else if (educationId == null && gradeId == null && unitId == null)
            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.subjectId == subjectId }?.size ?: 0 > 0 }
//        else if (educationId == null && subjectId == null && unitId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.gradeId == gradeId }?.size ?: 0 > 0 }
//        else if (gradeId == null && subjectId == null && unitId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.educationId == educationId }?.size ?: 0 > 0 }
//        //
//        else if (educationId == null && gradeId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.subjectId == subjectId && it.id == unitId }?.size ?: 0 > 0 }
//        else if (educationId == null && subjectId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.gradeId == gradeId && it.id == unitId }?.size ?: 0 > 0 }
//        else if (educationId == null && unitId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.gradeId == gradeId && it.subjectId == subjectId }?.size ?: 0 > 0 }
//        else if (gradeId == null && subjectId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.educationId == educationId && it.id == unitId }?.size ?: 0 > 0 }
//        else if (gradeId == null && unitId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.educationId == educationId && it.subjectId == subjectId }?.size ?: 0 > 0 }
//        else if (subjectId == null && unitId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.educationId == educationId && it.gradeId == gradeId }?.size ?: 0 > 0 }
//        else if (educationId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.gradeId == gradeId && it.subjectId == subjectId && it.id == unitId }?.size ?: 0 > 0 }
//        else if (gradeId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.educationId == educationId && it.subjectId == subjectId && it.id == unitId }?.size ?: 0 > 0 }
//        else if (subjectId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.educationId == educationId && it.gradeId == gradeId && it.id == unitId }?.size ?: 0 > 0 }
//        else if (unitId == null)
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.educationId == educationId && it.gradeId == gradeId && it.subjectId == subjectId }?.size ?: 0 > 0 }
        else
//            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.educationId == educationId && it.gradeId == gradeId && it.subjectId == subjectId && it.id == unitId }?.size ?: 0 > 0 }
            teacherInfoListTest.filter { it.teacherInfo?.unitsList?.filter { it.subjectId == subjectId && it.id == unitId }?.size ?: 0 > 0 }
    }

    override suspend fun updateUser(@Body updateUserRequest: UpdateUserRequest): Boolean {
        delay(1500)
        val account = updateUserRequest.account
        val bgUrl = updateUserRequest.bgUrl
        val avatarUrl = updateUserRequest.avatarUrl
        val name = updateUserRequest.name
        val gender = updateUserRequest.gender
        val intro = updateUserRequest.intro
        val birthday = updateUserRequest.birthday
        val cellPhone = updateUserRequest.cellPhone
        val homePhone = updateUserRequest.homePhone
        val email = updateUserRequest.email
        val school = updateUserRequest.school
        val gradeId = updateUserRequest.gradeId
        if (account == accountTest) {
            bgUrl?.let { bgUrlTest = it }
            avatarUrl?.let { avatarUrlTest = it }
            nameTest = name
            genderTest = gender
            introTest = intro
            birthday?.let { birthdayTest = it }
            cellPhoneTest = cellPhone
            homePhoneTest = homePhone
            emailTest = email
            school?.let { schoolTest = it }
            gradeId?.let { gradeIdTest = it }
            return true
        }
        return false
    }

    override suspend fun fetchCommentList(@Body commentRequest: CommentRequest): List<CommentInfo> {
        delay(1500)
        val commentListTest = commentListTest.filter { it.receiveAccount == accountTest }
        val score = commentRequest.score
        val educationId = commentRequest.educationId
        val gradeId = commentRequest.gradeId
        val subjectId = commentRequest.subjectId
        val unitId = commentRequest.unitId
        val index = commentRequest.index
        val num = commentRequest.num
        //account判斷取得哪個帳號的評論
        logD(
            "score = $score\n" +
                    "educationId = $educationId\n" +
                    "gradeId = $gradeId\n" +
                    "subjectId = $subjectId\n" +
                    "unitId = $unitId"
        )
        var list = if (score == null && educationId == null && gradeId == null && subjectId == null && unitId == null)
            commentListTest
        else if (score == null && educationId == null && gradeId == null && subjectId == null)
            commentListTest.filter { it.unitId == unitId }
        else if (score == null && educationId == null && gradeId == null && unitId == null)
            commentListTest.filter { it.subjectId == subjectId }
        else if (score == null && educationId == null && subjectId == null && unitId == null)
            commentListTest.filter { it.gradeId == gradeId }
        else if (score == null && gradeId == null && subjectId == null && unitId == null)
            commentListTest.filter { it.educationId == educationId }
        else if (educationId == null && gradeId == null && subjectId == null && unitId == null)
            commentListTest.filter { it.score == score }
        else if (score == null && educationId == null && gradeId == null)
            commentListTest.filter { it.subjectId == subjectId && it.id == unitId }
        else if (score == null && educationId == null && subjectId == null)
            commentListTest.filter { it.gradeId == gradeId && it.id == unitId }
        else if (score == null && educationId == null && unitId == null)
            commentListTest.filter { it.gradeId == gradeId && it.subjectId == subjectId }
        else if (score == null && gradeId == null && subjectId == null)
            commentListTest.filter { it.educationId == subjectId && it.id == unitId }
        else if (score == null && gradeId == null && unitId == null)
            commentListTest.filter { it.educationId == gradeId && it.subjectId == subjectId }
        else if (score == null && subjectId == null && unitId == null)
            commentListTest.filter { it.educationId == gradeId && it.gradeId == gradeId }
        else if (educationId == null && gradeId == null && subjectId == null)
            commentListTest.filter { it.score == score && it.id == unitId }
        else if (educationId == null && gradeId == null && unitId == null)
            commentListTest.filter { it.score == score && it.subjectId == subjectId }
        else if (educationId == null && subjectId == null && unitId == null)
            commentListTest.filter { it.score == score && it.gradeId == gradeId }
        else if (score == null && educationId == null)
            commentListTest.filter { it.gradeId == gradeId && it.subjectId == subjectId && it.unitId == unitId }
        else if (score == null && gradeId == null)
            commentListTest.filter { it.educationId == educationId && it.subjectId == subjectId && it.unitId == unitId }
        else if (score == null && subjectId == null)
            commentListTest.filter { it.educationId == educationId && it.gradeId == gradeId && it.unitId == unitId }
        else if (score == null && unitId == null)
            commentListTest.filter { it.educationId == educationId && it.gradeId == gradeId && it.subjectId == subjectId }
        else if (educationId == null && gradeId == null)
            commentListTest.filter { it.score == score && it.subjectId == subjectId && it.unitId == unitId }
        else if (educationId == null && subjectId == null)
            commentListTest.filter { it.score == score && it.gradeId == gradeId && it.unitId == unitId }
        else if (educationId == null && unitId == null)
            commentListTest.filter { it.score == score && it.gradeId == gradeId && it.subjectId == subjectId }
        else if (gradeId == null && subjectId == null)
            commentListTest.filter { it.score == score && it.educationId == educationId && it.unitId == unitId }
        else if (gradeId == null && unitId == null)
            commentListTest.filter { it.score == score && it.educationId == educationId && it.subjectId == subjectId }
        else if (subjectId == null && unitId == null)
            commentListTest.filter { it.score == score && it.educationId == educationId && it.gradeId == gradeId }
        else if (score == null)
            commentListTest.filter { it.educationId == educationId && it.gradeId == gradeId && it.subjectId == subjectId && it.id == unitId }
        else if (educationId == null)
            commentListTest.filter { it.score == score && it.gradeId == gradeId && it.subjectId == subjectId && it.id == unitId }
        else if (gradeId == null)
            commentListTest.filter { it.score == score && it.educationId == educationId && it.subjectId == subjectId && it.id == unitId }
        else if (subjectId == null)
            commentListTest.filter { it.score == score && it.educationId == educationId && it.gradeId == gradeId && it.id == unitId }
        else if (unitId == null)
            commentListTest.filter { it.score == score && it.educationId == educationId && it.gradeId == gradeId && it.subjectId == subjectId }
        else
            commentListTest.filter { it.score == score && it.educationId == educationId && it.gradeId == gradeId && it.subjectId == subjectId && it.id == unitId }
        var last = index + num
        if (last > list.size) last = list.size
        return list.subList(index, last)
    }

    override suspend fun fetchPublicData(): PublicDataResponse {
        delay(1500)
        return PublicDataResponse(
            shareLink = shareLinkTest,
            aboutCoaching = aboutCoachingTest,
            commonProblemList = commonProblemListTest,
            privacyPolicy = privacyPolicyTest,
            contactUs = contactUsTest,
            reflectList = reflectListTest
        )
    }

    override suspend fun insertReflect(reflectRequest: ReflectRequest): ReflectResponse {
        delay(1500)
        return ReflectResponse(
            isState = true,
            msg = "感謝您的來信，您的建議是我們前進的動力！"
        )
//        return ReflectResponse(
//            isState = false,
//            msg = "發送反映事項失敗！"
//        )
    }


    //    override suspend fun refreshToken(@Query(value = "refresh_token") refreshToken: String): TokenInfo {
//        delay(500)
//        return TokenInfo(
//            accessToken = "accessToken002",
//            refreshToken = "refreshToken002"
//        )
//    }
//
//

//
//    //
//    //
//    //
//    //
//
//    override suspend fun getLoadChat(): List<ChatInfo> {
//        delay(1500)
////        return listOf()
//        return listOf(
//            ChatInfo(
//                id = 0,
//                sender = UserInfo(
//                    id = 0,
//                    account = "jason.89",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://cf.shopee.tw/file/7da238c4f922ccc81be94692d9449eec",
//                    name = "jason.89"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2/19",
//                chatType = ChatType.TEXT,
//                text = "不會哦",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0,
//                isSound = true
//            ),
//            ChatInfo(
//                id = 1,
//                sender = UserInfo(
//                    id = 1,
//                    account = "yc86209",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://cf.shopee.tw/file/1a765f40612e142a1e308bd4c3cb07b9",
//                    name = "yc86209"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "1/7",
//                chatType = ChatType.TEXT,
//                text = "好哦，感謝",
//                read = ChatRead.UN_READ,
//                unReadCount = 2,
//                isSound = true
//            ),
//            ChatInfo(
//                id = 2,
//                sender = UserInfo(
//                    id = 2,
//                    account = "turboted",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://www.actphoto.org.tw/themes/zh-tw/assets/images/default_member.jpg",
//                    name = "turboted"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "1/3",
//                chatType = ChatType.TEXT,
//                text = "已下單有時間再看一下感恩",
//                read = ChatRead.UN_READ,
//                unReadCount = 5,
//                isSound = false
//            ),
//            ChatInfo(
//                id = 3,
//                sender = UserInfo(
//                    id = 3,
//                    account = "ghr7v2c41o",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://cf.shopee.tw/file/da790bc3a58c6099ec5b759af0bda797",
//                    name = "ghr7v2c41o"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2020/12/28",
//                chatType = ChatType.TEXT,
//                text = "您的這個寄件編號一直產生不了 已經三天時間 蝦皮也沒辦法解決 您是否可以重新下訂試試",
//                read = ChatRead.UN_READ,
//                unReadCount = 3,
//                isSound = true
//            ),
//            ChatInfo(
//                id = 4,
//                sender = UserInfo(
//                    id = 4,
//                    account = "iuea654321",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://www.actphoto.org.tw/themes/zh-tw/assets/images/default_member.jpg",
//                    name = "iuea654321"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2020/12/17",
//                chatType = ChatType.TEXT,
//                text = "好哦感謝 待收到領貨 謝謝",
//                read = ChatRead.UN_READ,
//                unReadCount = 14,
//                isSound = true
//            ),
//            ChatInfo(
//                id = 5,
//                sender = UserInfo(
//                    id = 5,
//                    account = "lbj7871",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://cf.shopee.tw/file/66f6a55ddd243f22b78c99847406b516",
//                    name = "lbj7871"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2020/12/17",
//                chatType = ChatType.TEXT,
//                text = "好",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0,
//                isSound = true
//            ),
//            ChatInfo(
//                id = 6,
//                sender = UserInfo(
//                    id = 6,
//                    account = "jimmy_jbj",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://cf.shopee.tw/file/77f6988c95356ae95bf22ff65682c92c",
//                    name = "jimmy_jbj"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2020/12/16",
//                chatType = ChatType.TEXT,
//                text = "[貼圖]",
//                read = ChatRead.UN_READ,
//                unReadCount = 12,
//                isSound = false
//            ),
//            ChatInfo(
//                id = 7,
//                sender = UserInfo(
//                    id = 7,
//                    account = "cg71124",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://www.actphoto.org.tw/themes/zh-tw/assets/images/default_member.jpg",
//                    name = "cg71124"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2020/11/28",
//                chatType = ChatType.TEXT,
//                text = "你好對哦  一頭是usb 一頭是lighting",
//                read = ChatRead.UN_READ,
//                unReadCount = 5,
//                isSound = false
//            ),
//            ChatInfo(
//                id = 8,
//                sender = UserInfo(
//                    id = 8,
//                    account = "shopee24h",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://cf.shopee.tw/file/589630be1b03af44d5c8ce0ddc73b4e4",
//                    name = "shopee24h"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2020/11/10",
//                chatType = ChatType.TEXT,
//                text = "\uD83D\uDCE3\uD83D\uDCE3\uD83D\uDCE3\uD83D\uDCE3嗨，11.11 最強購物節就是明天！為您送上全平台折扣券優惠~一起剁手吧！\uD83D\uDCE3\uD83D\uDCE3\uD83D\uDCE3\uD83D\uDCE3\n" +
//                        "\n" +
//                        "\uD83C\uDF81\uD83C\uDF81\uD83C\uDF81",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0,
//                isSound = true
//            ),
//            ChatInfo(
//                id = 9,
//                sender = UserInfo(
//                    id = 9,
//                    account = "itbook168",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://www.actphoto.org.tw/themes/zh-tw/assets/images/default_member.jpg",
//                    name = "itbook168"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2020/8/19",
//                chatType = ChatType.TEXT,
//                text = "[貼圖]",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0,
//                isSound = true
//            ),
//            ChatInfo(
//                id = 10,
//                sender = UserInfo(
//                    id = 10,
//                    account = "e4dcomic",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://cf.shopee.tw/file/60f61225f97535ce4a86695743e4d26c",
//                    name = "e4dcomic"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2020/5/8",
//                chatType = ChatType.TEXT,
//                text = "放到購物車會自動結算",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0,
//                isSound = true
//            ),
//            ChatInfo(
//                id = 11,
//                sender = UserInfo(
//                    id = 11,
//                    account = "l91113001",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://cf.shopee.tw/file/8813359970cc66bca769e27b69701bd7",
//                    name = "l91113001"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2020/4/8",
//                chatType = ChatType.TEXT,
//                text = "[貼圖]",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0,
//                isSound = true
//            ),
//            ChatInfo(
//                id = 12,
//                sender = UserInfo(
//                    id = 12,
//                    account = "zeloves.hz",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://www.actphoto.org.tw/themes/zh-tw/assets/images/default_member.jpg",
//                    name = "zeloves.hz"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2020/3/22",
//                chatType = ChatType.TEXT,
//                text = "拿到了謝謝",
//                read = ChatRead.UN_READ,
//                unReadCount = 99,
//                isSound = true
//            ),
//            ChatInfo(
//                id = 13,
//                sender = UserInfo(
//                    id = 13,
//                    account = "xhkjdzsf",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://cf.shopee.tw/file/106f2613e695547c9be9a6d7edf21560",
//                    name = "xhkjdzsf"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2020/1/1",
//                chatType = ChatType.TEXT,
//                text = "[貼圖]",
//                read = ChatRead.UN_READ,
//                unReadCount = 50,
//                isSound = true
//            ),
//            ChatInfo(
//                id = 14,
//                sender = UserInfo(
//                    id = 14,
//                    account = "dmf666",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://cf.shopee.tw/file/56022c505d88d5f690e9ab833e1c7593",
//                    name = "dmf666"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "2019/11/17",
//                chatType = ChatType.TEXT,
//                text = "好哦 謝謝",
//                read = ChatRead.UN_READ,
//                unReadCount = 100,
//                isSound = true
//            )
//        )
//    }
//
//    override suspend fun postSwitchChatSound(chat: ChatInfo): Boolean {
//        delay(500)
//        return true
//    }
//
//    override suspend fun deleteChatRoom(chat: ChatInfo): Boolean {
//        delay(500)
//        return true
//    }
//
//    override suspend fun getChatList(@Field(value = "chat") chat: ChatInfo): List<ChatInfo> {
//        delay(1500)
//        return listOf(
//            ChatInfo(
//                id = 0,
//                sender = UserInfo(
//                    id = 100,
//                    account = "jie110",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
//                    name = "阿吉 - 測試號"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "15:23",
//                chatType = ChatType.TEXT,
//                text = "我曾經養了一隻可愛的兔子\n" +
//                        "在夜市買的，一隻150，米白色，超可愛\uD83D\uDE03\n" +
//                        "\n" +
//                        "\n" +
//                        "而某天，我在花園裡放兔子曬太陽\n" +
//                        "\n" +
//                        "我媽打開花園的門想要去倒垃圾的瞬間\n" +
//                        "兔子蹦蹦跳跳，逃到外面的茶園\n" +
//                        "開始享受自由\uD83D\uDE11",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0
//            ),
//            ChatInfo(
//                id = 0,
//                sender = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉",
//                ),
//                recipient = UserInfo(
//                    id = 100,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
//                    name = "阿吉 - 測試號",
//                ),
//                sendTime = "15:44",
//                chatType = ChatType.TEXT,
//                text = "當時的我開始緊張的追著跑，\n" +
//                        "兔子開心的在這茶園裡跳來跳去\uD83D\uDE11\n" +
//                        "\n" +
//                        "追不到的我，在心急的狀態下\n" +
//                        "開始邊跑邊哭邊喘\uD83D\uDE11\n" +
//                        "\n" +
//                        "「啊啊嗚嗚嗚，不要再跑了～～～」\n" +
//                        "「嗚嗚嗚媽媽你幹嘛開門啦～～～」\n" +
//                        "「嗚嗚嗚嗚，你們快點來抓啦～～～」\n" +
//                        "\n" +
//                        "我大姐打開二樓窗戶，指揮著我\n" +
//                        "「在左邊！⋯往右！對！快追！！」\n" +
//                        "\n" +
//                        "我二姐打開另一個二樓窗戶，吃著吐司\n" +
//                        "「哈哈哈哈哈哈哈哈！！」\n" +
//                        "\n" +
//                        "⋯幹，去死\uD83D\uDE11",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0
//            ),
//            ChatInfo(
//                id = 0,
//                sender = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                recipient = UserInfo(
//                    id = 100,
//                    account = "jie110",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
//                    name = "阿吉 - 測試號"
//                ),
//                sendTime = "15:46",
//                chatType = ChatType.TEXT,
//                text = "我依舊邊大哭邊追\n" +
//                        "真的是一直跑一直掉眼淚地大喊\n" +
//                        "\n" +
//                        "「不要再跑了！蛇會吃掉你啦！！」\n" +
//                        "\n" +
//                        "\n" +
//                        "嗓門大的好處，在這時完美體現了\n" +
//                        "我嚎啕大哭到鄰居們紛紛前來抓兔子\n" +
//                        "至少有快10個人跟我一起在茶園奔跑\n" +
//                        "只為了抓到那隻該死的兔子",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0
//            ),
//            ChatInfo(
//                id = 0,
//                sender = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                recipient = UserInfo(
//                    id = 100,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
//                    name = "阿吉 - 測試號"
//                ),
//                sendTime = "15:46",
//                chatType = ChatType.TEXT,
//                text = "⋯你們能想像那畫面有多好笑嗎\uD83D\uDE03",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0
//            ),
//            ChatInfo(
//                id = 0,
//                sender = UserInfo(
//                    id = 100,
//                    account = "jie110",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
//                    name = "阿吉 - 測試號"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "16:01",
//                chatType = ChatType.TEXT,
//                text = "我爸拿著麻布袋跑來跑去\n" +
//                        "我媽一直尖叫的用手指在導航著我們\n" +
//                        "其它鄰居還拿掃把、耙子、捕鼠籠（？）\n" +
//                        "一起在茶園裡抓一隻兔子",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0
//            ),
//            ChatInfo(
//                id = 0,
//                sender = UserInfo(
//                    id = 100,
//                    account = "jie110",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
//                    name = "阿吉 - 測試號"
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉"
//                ),
//                sendTime = "16:19",
//                chatType = ChatType.TEXT,
//                text = "最終，花了很久的時間\n" +
//                        "終於把兔子逼到角落\n" +
//                        "我跑過去抱起兔子邊哭邊罵三字經\n" +
//                        "\n" +
//                        "但兔子在我懷裡，比我還喘⋯\uD83D\uDE11",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0
//            ),
//            ChatInfo(
//                id = 0,
//                sender = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉",
//                ),
//                recipient = UserInfo(
//                    id = 100,
//                    account = "jie110",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
//                    name = "阿吉 - 測試號",
//                ),
//                sendTime = "16:20",
//                chatType = ChatType.TEXT,
//                text = "而最後我和爸媽一起跟鄰居們道謝\n" +
//                        "還摘了一些自己種的木瓜當作小禮物送給鄰居\n" +
//                        "\n" +
//                        "這件事情才落幕。",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0
//            ),
//            ChatInfo(
//                id = 0,
//                sender = UserInfo(
//                    id = 100,
//                    account = "jie110",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
//                    name = "阿吉 - 測試號",
//                ),
//                recipient = UserInfo(
//                    id = -1,
//                    account = "north27",
//                    auth = UserPref.Authority.STUDENT,
//                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                    name = "阿吉",
//                ),
//                sendTime = "18:30",
//                chatType = ChatType.TEXT,
//                text = "阿為什麼現在又想起這件事情？\n" +
//                        "因為鄰居其中一個就是我同學\n" +
//                        "剛剛聊天聊到這個\uD83E\uDD72\n" +
//                        "事隔快10年了，還在嘲笑我當時哭很大聲\uD83E\uDD72\n" +
//                        "\n" +
//                        "\n" +
//                        "所以真的不能有把柄在朋友手上，\n" +
//                        "會被笑到過世為止\uD83E\uDD72\n" +
//                        "\n" +
//                        "可惡。",
//                read = ChatRead.HAVE_READ,
//                unReadCount = 0
//            )
//
//        )
//
//    }
//
//    override suspend fun getLoadNotify(@Field(value = "page") page: Int): List<NotifyInfo> {
//        var notifyList = mutableListOf<Pair<Int, MutableList<NotifyInfo>>>()
//        var page = page
//        notifyList.add(
//            1 to mutableListOf(
//                NotifyInfo(
//                    id = 0,
//                    imgUrl = "https://cf.shopee.tw/file/b7b28075865ae8a751109478b6c59f2b_tn",
//                    title = "噓！偷偷告訴你促銷期間輕鬆提升流量秘笈 v1.0.0",
//                    desc = "大促期間流量&轉單大幅成長，商品下折扣卻沒有曝光嗎✨使用蝦皮關鍵字廣告讓你輕鬆獲得賣場流量，要曝光和業績就趁現在！",
//                    time = "2021-02-26 16:31",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 1,
//                    imgUrl = "https://cf.shopee.tw/file/b3275b57f030fac1288e08a75f364017_tn",
//                    title = "【重要】3/3(三)自動提款順延至3/10(三)執行 v1.0.1",
//                    desc = "親愛的蝦皮用戶您好，由於內部作業系統維護，原定3/3(三)執行的自動提款將順延至3/10(三)執行，後續自動提款作業時間仍維持於3/17、3/31...以此類推，造成您的不便還請見諒\uD83D\uDE47未執行自動提領當週，仍有乙次免費提領機會，還請多加利用\uD83D\uDE4F",
//                    time = "2021-02-26 14:33",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 2,
//                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
//                    title = "賣場佈置新組件上線！ v1.0.2",
//                    desc = "賣場佈置全新組件讓您展示熱銷商品、新品以及促銷折扣，幫助您打造吸睛的賣場首頁，提升下單轉換率，點入了解更多\uD83D\uDC49",
//                    time = "2021-02-23 14:49",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 3,
//                    imgUrl = "https://cf.shopee.tw/file/bcf02170cead12ea5d9319f8d4611823_tn",
//                    title = "蝦皮動態模板懶人包送給你 v1.0.3",
//                    desc = "【直播課程最便利－貼文牆引流量】在家就可以看\uD83D\uDD25到底怎麼經營粉絲，貼文牆怎麼玩？下半年最強功能之一你不能不會！貼文牆各種秘笈我們來教你\uD83D\uDE4C",
//                    time = "2021-02-18 20:10",
//                    isRead = true,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 4,
//                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
//                    title = "新推出手機版【我的主題活動】 v1.0.4",
//                    desc = "為了方便報名主題活動，您將可透過蝦皮APP來管理【我的主題活動】，同時也可即時查詢已報名成功的商品。更多相關說明請參考連結\uD83D\uDC49",
//                    time = "2021-02-02 17:01",
//                    isRead = true,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 5,
//                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
//                    title = "防詐騙提醒 v1.0.5",
//                    desc = "【重要提醒】蝦皮購物貼心提醒您，千萬不要透過LINE或其他通訊軟體約定交易、私下匯款賣家，也不要在商品未確認收到前聽信賣家指示提前點選〔完成訂單〕千萬注意，以免受騙上當哦！",
//                    time = "2021-01-28 15:33",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 6,
//                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
//                    title = "賣家中心新功能上線：商品優化工具 v1.0.6",
//                    desc = "親愛的賣家您好，賣家中心內的數據中心全新上線商品優化工具，透過商品優化工具可以幫助您找出需要優化的商品並提供調整方向與建議，確保賣場內所有商品內容都是高品質，吸引買家關注！立即點入了解更多\uD83D\uDC49",
//                    time = "2021-01-27 17:00",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 7,
//                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
//                    title = "【重要提醒！強化帳戶安全性指南】 v1.0.7",
//                    desc = "蝦皮絕不會要求您提供個人密碼、驗證碼，當您有以下狀況：無法登入帳號、有不明提款動作、發現非本人下單的訂單時，請盡速聯繫蝦皮客服團隊。點擊確認了解如何設定高強度密碼／帳戶有安全疑慮該怎麼辦\uD83D\uDC49",
//                    time = "2021-01-25 11:03",
//                    isRead = true,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 8,
//                    imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
//                    title = "邀請新朋友來蝦皮 v1.0.8",
//                    desc = "新朋友註冊蝦皮後，在蝦皮購物App完成第1筆訂單，可享訂單金額 ",
//                    time = "2021-01-11 18:56",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 9,
//                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
//                    title = "您的訂單已被取消！ v1.0.9",
//                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
//                    time = "2021-01-01 12:00",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                )
//            )
//        )
//        notifyList.add(
//            2 to mutableListOf(
//                NotifyInfo(
//                    id = 10,
//                    imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
//                    title = "邀請新朋友來蝦皮 v1.1.0",
//                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
//                    time = "2020-12-21 19:42",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 11,
//                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
//                    title = "您的訂單已被取消！ v1.1.1",
//                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
//                    time = "2020-12-21 12:00",
//                    isRead = true,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 12,
//                    imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
//                    title = "邀請新朋友來蝦皮 v1.1.2",
//                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
//                    time = "2020-12-20 20:38",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 13,
//                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
//                    title = "您的訂單已被取消！ v1.1.3",
//                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
//                    time = "2020-12-18 12:00",
//                    isRead = true,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 14,
//                    imgUrl = "https://cf.shopee.tw/file/683d408bda45da45ca2f9414e19a16a0_tn",
//                    title = "恭喜! 您已完成跨境實名認證 v1.1.4",
//                    desc = "您已經成功地使用街口帳戶 +886972911675 完成了跨境商品實名認證流程。您不需再進行實名認證。請注意，您的跨境購物退款金額將被退回到這個街口帳戶。",
//                    time = "2020-11-28 22:57",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 15,
//                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
//                    title = "您的訂單已被取消！ v1.1.5",
//                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
//                    time = "2020-11-25 12:00",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 16,
//                    imgUrl = "https://cf.shopee.tw/file/79f99f9f49370fef139475db254e8c76_tn",
//                    title = "別忘記你購物車內的商品！ v1.1.6",
//                    desc = "Android TDD 測試驅動開發：從 UnitTest、TD... 還在你的購物車內，在商品完售前趕快購買！",
//                    time = "2020-11-21 20:01",
//                    isRead = true,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 17,
//                    imgUrl = "https://cf.shopee.tw/file/9f70ed5d9a2f1105b5916c8c1309a721_tn",
//                    title = "提醒您有蝦幣即將到期啦！ v1.1.7",
//                    desc = "嗨，你的蝦幣即將在9/30, 23:59過期囉！趕緊進蝦皮，折抵蝦幣立即逛\uD83D\uDC49",
//                    time = "2020-09-19 09:00",
//                    isRead = true,
//                    notifyType = NotifyType.NORMAL
//                ),
//                NotifyInfo(
//                    id = 18,
//                    imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
//                    title = "邀請新朋友來蝦皮 v1.1.8",
//                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
//                    time = "2020-07-04 20:21",
//                    isRead = false,
//                    notifyType = NotifyType.NORMAL
//                )
//            )
//        )
//        isReadAllNotify?.let {
//            if (it) {
//                notifyList = notifyList.map { it.copy() }.toMutableList()
//                notifyList.forEach {
//                    it.second.forEach { it.isRead = true }
//                }
//                Timber.d("isReadAllNotify")
//            }
//            isReadAllNotify = null
//        }
//        deleteNotify?.let {
//            Timber.d("deleteNotify = $it")
//            notifyList = notifyList.map { it.copy() }.toMutableList()
//            notifyList.forEach {
//                val isRemove = it.second.removeAll { it.id == deleteNotify!!.id }
//                Timber.d("deleteNotify = isRemove = $isRemove")
//            }
//            deleteNotify = null
//        }
//        delay(1500)
//        return when {
//            page > notifyList.last().first -> emptyList()
//            else -> notifyList.first { page == it.first }.second
//        }
//    }
//
//    override suspend fun postSwitchNotify(isOpen: Boolean): Boolean {
//        delay(500)
//        return true
//    }
//
//    override suspend fun postReadAllNotify(): Boolean {
//        isReadAllNotify = true
//        delay(1000)
//        return true
//    }
//
//    override suspend fun deleteNotify(notify: NotifyInfo): Boolean {
//        deleteNotify = notify
//        delay(500)
//        return true
//    }

}