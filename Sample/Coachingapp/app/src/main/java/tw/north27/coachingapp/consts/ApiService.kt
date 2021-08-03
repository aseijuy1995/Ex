package tw.north27.coachingapp.consts

import android.content.Context
import com.google.gson.Gson
import com.yujie.core_lib.http.okhttp.TokenInfo
import com.yujie.core_lib.util.UpdateApp
import com.yujie.core_lib.util.logD
import com.yujie.core_lib.util.logI
import kotlinx.coroutines.delay
import retrofit2.http.Body
import tw.north27.coachingapp.R
import tw.north27.coachingapp.consts.simulation.*
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.AskRoomInfo
import tw.north27.coachingapp.model.request.*
import tw.north27.coachingapp.model.response.*
import java.lang.Thread.sleep

class ApiService(val cxt: Context) : IApiService {

    override fun refreshToken(@Body tokenRequest: TokenRequest): TokenInfo {
        sleep(1500)
        val clientId = tokenRequest.clientId
        val refreshToken = tokenRequest.refreshToken
        val tokenInfo = tokenInfo_Test
        logI("fetchAppConfig = ${Gson().toJson(tokenInfo)}")
        return tokenInfo
    }

    override suspend fun fetchAppConfig(@Body appConfigRequest: AppConfigRequest): AppConfig {
        delay(1500)
        logI("appConfigRequest = ${Gson().toJson(appConfigRequest)}")
        val deviceType = appConfigRequest.deviceType
//        deviceType == "android"
        val appConfig: AppConfig
        appConfig = AppConfig(
            appCode = AppCode.MOTION.code,
            motionInfo = MotionInfo(
                bgUrl = "https://images.unsplash.com/photo-1604754742629-3e5728249d73?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
                title = cxt.getString(R.string.update_title),
                versionNameMode = UpdateApp.VersionNameMode.DEFAULT,
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
//        appConfig = AppConfig(
//            appCode = AppCode.DEFEND.code,
//            defendInfo = DefendInfo(
//                bgUrl = "https://images.unsplash.com/photo-1595330720945-992007315e47?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
//                title = cxt.getString(R.string.defend_title),
//                content = "1. 伺服器遭受攻擊。\n" +
//                        "2. 增加監控、效能分析、執行網路維護。\n" +
//                        "3. 描述統一規範化。\n" +
//                        "4. 維護通知描述1\n" +
//                        "5. 維護通知描述2\n" +
//                        "6. 維護通知描述3",
//                time = Date("2021/01/01 15:30")
//            )
//        )
        logI("fetchAppConfig = ${Gson().toJson(appConfig)}")
        return appConfig
    }

    override suspend fun checkSign(@Body signRequest: SignRequest): SignInfo {
        delay(1500)
        logI("signRequest = ${Gson().toJson(signRequest)}")
        val signInfo: SignInfo
        signInfo = signSuc_Test
//        signInfo = signFail_Test
        logI("checkSign = ${Gson().toJson(signInfo)}")
        return signInfo
    }

    override suspend fun signIn(@Body signInRequest: SignInRequest): SignInfo {
        delay(1500)
        logI("signInRequest = ${Gson().toJson(signInRequest)}")
        val account = signInRequest.account
        val password = signInRequest.password
        val pushToken = signInRequest.pushToken
        val signIn = if (account == account_Test && password == password_Test) {
            pushToken_Test = pushToken
            signSuc_Test
        } else {
            signFail_Test
        }
        logI("signIn = ${Gson().toJson(signIn)}")
        return signIn
    }

    override suspend fun fetchEducation(): Education {
        delay(1500)
        val education = Education(
            educationLevelList = educationList_Test,
            gradeList = gradeList_Test,
            subjectList = subjectList_Test,
            unitTypeList = unitList_Test
        )
        logI("fetchEducation = ${Gson().toJson(education)}")
        return education
    }

    override suspend fun fetchTeacherList(@Body teacherRequest: TeacherRequest): List<ClientInfo> {
        delay(1500)
        val educationLevelId = teacherRequest.educationLevelId
        val gradeId = teacherRequest.gradeId
        val subjectId = teacherRequest.subjectId
        val unitId = teacherRequest.unitTypeId
        val index = teacherRequest.index
        val num = teacherRequest.num
        var list =
            if (educationLevelId == null && gradeId == null && subjectId == null && unitId == null)
                teacherInfoList_Test
            else if (educationLevelId == null && gradeId == null && subjectId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.id == unitId } ?: false }
            else if (educationLevelId == null && gradeId == null && unitId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.subjectId == subjectId } ?: false }
            else if (educationLevelId == null && subjectId == null && unitId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.gradeId == gradeId } ?: false }
            else if (gradeId == null && subjectId == null && unitId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.educationLevelId == educationLevelId } ?: false }
            else if (educationLevelId == null && gradeId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.subjectId == subjectId || it.id == unitId } ?: false }
            else if (educationLevelId == null && subjectId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.gradeId == gradeId || it.id == unitId } ?: false }
            else if (educationLevelId == null && unitId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.gradeId == gradeId || it.subjectId == subjectId } ?: false }
            else if (gradeId == null && subjectId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.educationLevelId == educationLevelId || it.id == unitId } ?: false }
            else if (gradeId == null && unitId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.educationLevelId == educationLevelId || it.subjectId == subjectId } ?: false }
            else if (subjectId == null && unitId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.educationLevelId == educationLevelId || it.gradeId == gradeId } ?: false }
            else if (educationLevelId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.gradeId == gradeId || it.subjectId == subjectId || it.id == unitId } ?: false }
            else if (gradeId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.educationLevelId == educationLevelId || it.subjectId == subjectId || it.id == unitId } ?: false }
            else if (subjectId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.educationLevelId == educationLevelId || it.gradeId == gradeId || it.id == unitId } ?: false }
            else if (unitId == null)
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.educationLevelId == educationLevelId || it.gradeId == gradeId || it.subjectId == subjectId } ?: false }
            else
                teacherInfoList_Test.filter { it.teacherInfo?.unitTypeList?.any { it.educationLevelId == educationLevelId || it.gradeId == gradeId || it.subjectId == subjectId || it.id == unitId } ?: false }

        var last = index + num
        if (last > list.size) last = list.size
        list = list.subList(index, last)
        logI("fetchTeacherList = ${Gson().toJson(list)}")
        return list
    }

    override suspend fun fetchAskRoomList(@Body askRoomRequest: AskRoomRequest): List<AskRoom> {
        delay(1500)
        val clientId = askRoomRequest.clientId
        val askId = askRoomRequest.askId
        while (askRoomList_Test.first().askRoomInfo?.id == askId) delay(3000)
        val askRoomList: List<AskRoom> = askRoomList_Test
        logI("fetchAskRoomList = ${Gson().toJson(askRoomList)}")
        return askRoomList
    }

    override suspend fun updateAskRoomPush(pushRequest: PushRequest): PushResponse {
        delay(1500)
        val clientId = pushRequest.clientId
        val pushResponse = PushResponse(
            isSuccess = true,
            msg = "更新成功"
        )
        logI("updateAskRoomPush = ${Gson().toJson(pushResponse)}")
        return pushResponse
    }

    override suspend fun updateAskRoomSound(soundRequest: SoundRequest): SoundResponse {
        delay(1500)
        val clientId = soundRequest.clientId
        val soundResponse = SoundResponse(
            isSuccess = true,
            msg = "更新成功"
        )
        logI("updateAskRoomSound = ${Gson().toJson(soundResponse)}")
        return soundResponse
    }

    override suspend fun fetchTeacherPair(pairRequest: PairRequest): ClientInfo? {
        delay(1500)
        val clientInfo = teacherInfoList_Test.find {
            it.teacherInfo?.unitTypeList?.any {
                it.id == pairRequest.unitId
            } ?: false
        }
        logI("fetchTeacherPair = ${Gson().toJson(clientInfo)}")
        return clientInfo
    }

    override suspend fun findAskRoom(setupAskRoomRequest: SetupAskRoomRequest): AskRoomResponse {
        delay(1500)
        val clientId = setupAskRoomRequest.selfClientId
        val otherClientId = setupAskRoomRequest.otherClientId
        val unitId = setupAskRoomRequest.unitId
        val askRoomResponse: AskRoomResponse
        askRoomResponse = AskRoomResponse(
            isExist = true,
            msg = "相同提問室已存在，你確定要另開一間嗎?"
        )
//        askRoomResponse = AskRoomResponse(
//            isExist = false,
//            askRoom = askRoomList_Test[0],
//            msg = ""
//        )
        logI("findAskRoom = ${Gson().toJson(askRoomResponse)}")
        return askRoomResponse
    }

    override suspend fun setupAskRoom(setupAskRoomRequest: SetupAskRoomRequest): AskRoom {
        delay(1500)
        val clientId = setupAskRoomRequest.selfClientId
        val otherClientId = setupAskRoomRequest.otherClientId
        val unitId = setupAskRoomRequest.unitId
        val askRoom = askRoomList_Test[0]
        logI("setupAskRoom = ${Gson().toJson(askRoom)}")
        return askRoom
    }

    override suspend fun fetchAskRoomInfoList(@Body askRoomInfoRequest: AskRoomInfoRequest): List<AskRoomInfo> {
        delay(1500L)
        val roomId = askRoomInfoRequest.roomId
        val askRoomInfoList = askRoomInfoList_Test(roomId = roomId)
        logI("fetchAskRoomInfoList = ${Gson().toJson(askRoomInfoList)}")
        return askRoomInfoList
    }

    override suspend fun fetchClient(@Body clientRequest: ClientRequest): ClientInfo {
        delay(1500)
        val userList = teacherInfoList_Test.toMutableList().apply { add(clientInfo_Test) }
        val client = userList.find { it.id == clientRequest.clientId }!!
        logI("fetchClient = ${Gson().toJson(client)}")
        return client
    }

    override suspend fun updateClient(@Body updateClientRequest: UpdateClientRequest): UpdateClientResponse {
        delay(1500)
        val clientId = updateClientRequest.clientId
        val bgUrl = updateClientRequest.bgUrl
        val avatarUrl = updateClientRequest.avatarUrl
        val name = updateClientRequest.name
        val gender = updateClientRequest.gender
        val intro = updateClientRequest.intro
        val birthday = updateClientRequest.birthday
        val cellPhone = updateClientRequest.cellPhone
        val homePhone = updateClientRequest.homePhone
        val email = updateClientRequest.email
        val school = updateClientRequest.school
        val gradeId = updateClientRequest.gradeId
        if (clientId == clientId_Test) {
            bgUrl?.let { bgUrl_Test = it }
            avatarUrl?.let { avatarUrl_Test = it }
            name_Test = name
            gender_Test = gender
            intro_Test = intro
            birthday?.let { birthday_Test = it }
            cellPhone_Test = cellPhone
            homePhone_Test = homePhone
            email_Test = email
            school?.let { school_Test = it }
            gradeId?.let { gradeId_Test = it }
            return UpdateClientResponse(
                isSuccess = true,
                msg = "修改成功"
            )
        }
        return UpdateClientResponse(
            isSuccess = false,
            msg = "修改失敗"
        )
    }

    override suspend fun fetchCommentList(@Body commentRequest: CommentRequest): List<CommentInfo> {
        delay(1500)
        val commentListTest = commentListTest.filter { it.receiveId == clientId_Test }
        val score = commentRequest.score
        val educationId = commentRequest.educationLevelId
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
            isSuccess = true,
            msg = "感謝您的來信，您的建議是我們前進的動力！"
        )
//        return ReflectResponse(
//            isState = false,
//            msg = "發送反映事項失敗！"
//        )
    }

//        )

//
//
//    var deleteNotify: NotifyInfo? = null
//    var isReadAllNotify: Boolean? = null
//
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