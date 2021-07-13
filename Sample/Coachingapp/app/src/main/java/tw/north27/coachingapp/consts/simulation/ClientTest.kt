package tw.north27.coachingapp.consts.simulation

import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.http.TokenInfo
import tw.north27.coachingapp.model.*

val clientId_Test = "north27_id"
val account_Test = "north27"
val password_Test = "north27"
var tokenType_Test = UserPref.TokenType.Bearer
var accessToken_Test = "accessTokenTest"
var refreshToken_Test = "refreshTokenTest"
var expiresIn_Test = 900L
var isFirst_Test = true
var pushToken_Test = "pushTokenTest"
val authorityStudent_Test = UserPref.Authority.STUDENT
val authorityTeacher_Test = UserPref.Authority.TEACHER

val tokenInfo_Test = TokenInfo(
    tokenType = tokenType_Test,
    accessToken = accessToken_Test,
    refreshToken = refreshToken_Test,
    expiresIn = expiresIn_Test
)

val signSuc_Test = SignInfo(
    signCode = SignCode.SIGN_IN_SUC.code,
    signInInfo = signInInfo_Test,
    msg = "即將登入..."
)

val signInInfo_Test: SignInInfo
    get() = SignInInfo(
        clientInfo = ClientInfo(
            id = clientId_Test,
            auth = authorityStudent_Test,
//            auth = authorityTeacher_Test,
        ),
        tokenInfo = tokenInfo_Test,
        isFirst = isFirst_Test,
        pushToken = pushToken_Test,
    )

val signFail_Test = SignInfo(
    signCode = SignCode.SIGN_IN_FAIL.code,
    msg = "帳號已登入其他裝置，請重新登入..."
)

var bgUrl_Test = "https://images.unsplash.com/photo-1499951360447-b19be8fe80f5?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"
var avatarUrl_Test = "http://static.104.com.tw/b_profile/cust_picture/8063/130000000158063/logo.png?v=20210220092939"
var name_Test = "北緯科技"
var gender_Test = Gender.MALE
var intro_Test = "這是簡單的自我介紹！這是簡單的自我介紹2！這是簡單的自我介紹3！這是簡單的自我介紹4！這是簡單的自我介紹5！"
var birthday_Test = stringToDate("2018/12/21")
var cellPhone_Test = "0912-345-678"
var homePhone_Test = "02-3456-7890"
var email_Test = "north27@north27.tw"
var school_Test = "新北市板橋區板橋國小"
var gradeId_Test = 8L
var replyNotice_Test = false
var msgNotice_Test = true

/**
 * 用戶資訊
 * */
val clientInfo_Test = ClientInfo(
    id = clientId_Test,
    auth = authorityStudent_Test,
//    auth = authorityTeacher_Test,
    //
    bgUrl = bgUrl_Test,
    avatarUrl = avatarUrl_Test,
    name = name_Test,
    gender = gender_Test,
    intro = intro_Test,
    birthday = birthday_Test,
    cellPhone = cellPhone_Test,
    homePhone = homePhone_Test,
    email = email_Test,
    studentInfo = StudentInfo(
        school = school_Test,
        gradeId = gradeId_Test,
    ),
    teacherInfo = TeacherInfo(
        commentScoreAvg = getCommentScoreAvg(),
        commentScoreCountList = getCommentScoreCountList(),
        replyRate = getReplyRate(),
        replyCountList = replyCountListTest,
        unitsList = unitList_Test.filter { it.subjectId == 1L }
    ),
    userConfig = UserConfig(
        replyNotice = replyNotice_Test,
        msgNotice = msgNotice_Test,
    ),
)

val teacherInfoListTest = listOf<ClientInfo>(
    ClientInfo(
        id = "rebeccaId",
        auth = UserPref.Authority.TEACHER,
        //
        bgUrl = "https://images.unsplash.com/photo-1622495807835-858ac1da986d?ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=967&q=80",
        //
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/732/757/normal/nlpknz.png?1623138408",
        name = "Rebecca",
        //
        gender = Gender.FEMALE,
        intro = "關於Rebecca\n\n" +
                "專攻\n\n" +
                "國小 / 一年級 / 國語" +
                "單元/類型\n\n" +
                "\t\t手拉手\n" +
                "\t\t排一排\n\n" +
                "\uD83C\uDFC5補教老師，能敏銳掌握學生學習上的盲點，給予最合適的指導。\n" +
                "\uD83C\uDFC5法律系畢業、中文輔系學程、高中學測國文滿級分、國語文競試-字音字形組第一、作文組第一。\n" +
                "\uD83C\uDFC5三不政策：不死背、不填鴨、不責備。\n" +
                "\n" +
                "\uD83D\uDFE1我的課程適合誰？\n" +
                "✔️國小生、學測、統測生\n" +
                "\uD83D\uDFE1上完課程您能獲得這些改變⋯\n" +
                "✔️國小學生：從小打好基礎，從此不怕作文！提升閱讀理解能力，各科學習更容易！\n" +
                "✔️學測、統測生：系統化重點複習，讓國文成績不再扯您後腿，反倒助您一臂之力！\n" +
                "\uD83D\uDFE1我教什麼？怎麼教？\n" +
                "✔️課程內容：國小作文、學測國文、統測國文\n" +
                "✔️課程特色：\n" +
                "1.國小作文：情境式教學、引導式教學、字詞句基本功、觀察力訓練、寫作技巧指導。\n" +
                "2.學測統測：條理化、系統化、重點教學、國文基本能力訓練、國寫指導。\n" +
                "\n" +
                "\n" +
                "\uD83D\uDFE1教育理念：\n" +
                "韓愈說：「世有伯樂，然後有千里馬。千里馬常有，而伯樂不常有。」相信每位學生都能是千里馬，期待能成為伯樂。識才，而後因材施教。\n" +
                "\n" +
                "\uD83D\uDFE1教學方式：\n" +
                "秉持著信任、尊重、專業精神，和學生一起擁抱挑戰。\n" +
                "✔️為您找出盲點、對症下藥\n" +
                "✔️幫您量身設計課程、講義\n" +
                "✔️讓您在課程中充分訓練理解及分析能力\n" +
                "✔️與您按部就班的享受在學習國文中\n" +
                "\n" +
                "\uD83D\uDFE1上課禮儀：\n" +
                "1.購買正式課程前，歡迎您先上一堂體驗課程。\n" +
                "2.如要更改已預約之課堂，請於該課堂前12小時取消。\n" +
                "3.如學生臨時無法上課，請於該課堂開始前15分鐘向老師告知。\n" +
                "4.請務必準時上線，如課堂開始後10分鐘仍未上線，視同曠課，該預約課堂不退款、不補課。\n" +
                "5.學生如有任何問題，歡迎透過此平台訊息功能與老師聯繫。",
        birthday = stringToDate("1990/02/05"),
        cellPhone = "0911-222-333",
        homePhone = "02-111-2222",
        email = "rebecca@gmail.com",
        teacherInfo = TeacherInfo(
            unitsList = unitList_Test.filter { it.educationLevelId == 1L && it.gradeId == 1L && it.subjectId == 1L },
            //
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
        ),
    ),
    ClientInfo(
        id = "peiYuId",
        auth = UserPref.Authority.TEACHER,
        //
        bgUrl = "https://images.unsplash.com/photo-1621570070821-2e2b1358fae3?ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80",
        //
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/425/315/normal/acwdjm.png?1605207454",
        name = "裴育",
        //
        gender = Gender.MALE,
        intro = "關於裴育\n\n" +
                "專攻\n\n" +
                "國小 / 一年級 / 二年級 / 數學" +
                "單元/類型\n\n" +
                "\t\t加減\n" +
                "\t\t乘除\n" +
                "\t\t數與數線\n" +
                "\t\t公因數&公倍數\n\n" +
                "自己在百世資優數學任教，曾帶過前三志願、延平、薇閣班等私中小組班，也帶過學測班，熟悉學生對大考需求。\n" +
                "高中（含）以下補習班經驗邁入第五學年，於2020年開始接觸線上教學授課，也在「閎數學」\n" +
                "＜https://www.facebook.com/oicmath/＞幫忙錄製影片。\n" +
                "\n" +
                "能自編教材(常用軟體為MathType)，\n" +
                "所用字型、所挑選題目皆無侵權問題，\n" +
                "能轉化成PDF供Google Meet、Zoom線上教學使用，\n" +
                "並結合分享ipad螢幕(或鏡射)與apple pencil搭配上筆記相關app，\n" +
                "若學生端也有觸控螢幕設備也能即時互動，\n" +
                "結合Google Classroom管理小考成績、作業繳交，\n" +
                "也能透過截圖、錄影等方式記錄課程，方便學生複習、補課。\n" +
                "可參考：https://imgur.com/ZspVbhs\n" +
                "\n" +
                "傳統黑、白板、電子白板、3C平板、觸控式螢幕皆能授課，\n" +
                "可參考：https://imgur.com/a/5K0SSiC\n" +
                "\n" +
                "\n" +
                "★課綱內外觀念建立與連結☆\n" +
                "☆歷屆大考題目的深度剖析★\n" +
                "★解決數學有關的疑難雜症☆\n" +
                "☆培養學生獨立思考的能力★\n" +
                "★現行課綱為主舊課綱為輔☆\n" +
                "☆搭配聖經本教材因材施教★\n" +
                "★安排課程進度計畫並追蹤☆\n" +
                "☆作業練習訂正做好基本功★\n" +
                "\n" +
                "【教學風格】\n" +
                "●培養學生三大能力：「讀的能力、寫的能力與說的能力。」\n" +
                "●讀的能力：\n" +
                "　現在的學生與我們求學時那個年代不同，普遍英語能力皆有一定水準，\n" +
                "　但較少有家長會重視「國語文」，無論是現行的會考、學測還是指考，\n" +
                "　出題的語言皆是「中文」，連敘述都看不懂了，何來談了解數學？\n" +
                "　更不用說其背後的邏輯思考。老師會以圖像化、類比、較口語的方式深入淺出，\n" +
                "　即時接收學生的反應，進而調整上課節奏。\n" +
                "\n" +
                "●寫的能力：\n" +
                "　列式中以第一步驟最為重要，第一步驟的引導就能看出老師的功力，\n" +
                "　有第一式之後，多數學生皆能寫出之後的過程進而求出答案，\n" +
                "　國小的計算、算數是相當基礎的，務必熟甚至精。\n" +
                "　國中才會開始引入較深的代數、指數律、解方程式、函數，\n" +
                "　高中會較著重定義以及畫圖的能力，有時候圖畫出來，再搭配定義，答案就呼之欲出了。\n" +
                "　計算正確是老師的基本要求，而計算速度才是其次。\n" +
                "\n" +
                "●說的能力：\n" +
                "　為什麼要說？說什麼？說明學生自己讀題的想法、列式的意義之外，\n" +
                "　在學生說的同時，會訓練到自己的邏輯。其實這就是「內化」的過程，\n" +
                "　學生用自己熟悉的話語把想法傳達出來，這才是真正學生自己的知識，\n" +
                "　而且是帶得走的。老師在課堂上說著上課內容，即便一旁的學生都聽懂了，\n" +
                "　但那也只是「懂」，離「會」還有一段差距，而這差距因人而異。\n" +
                "　有的學生在課堂上練習一下就會了；有的學生在課後自己反思過幾次也會了；\n" +
                "　有的學生要歷經更長的時間去咀嚼才能體會。無論要花多久的時間，\n" +
                "　這都是學生在學習上必經的過程，而老師都會和學生一起去面對。",
        birthday = stringToDate("1977/09/02"),
        cellPhone = "0944-555-666",
        homePhone = "02-444-5555",
        email = "peiYu@gmail.com",
        teacherInfo = TeacherInfo(
            unitsList = unitList_Test.filter { it.educationLevelId == 1L && (it.gradeId == 1L) or (it.gradeId == 2L) && it.subjectId == 2L },
            //
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest

        )
    ),
    ClientInfo(
        id = "mimiId",
        auth = UserPref.Authority.TEACHER,
        bgUrl = "https://images.unsplash.com/photo-1624799993735-41a4ee092f7b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/142/217/normal/vovnbl.png?1621920493",
        name = "米米老師",
        gender = Gender.FEMALE,
        intro = "關於米米老師\n\n" +
                "專攻\n\n" +
                "國小 / 一年級 / 二年級 / 國語" +
                "單元/類型\n\n" +
                "\t\t手拉手\n" +
                "\t\t排一排\n" +
                "\t\t踩影子\n" +
                "\t\t再玩一次\n\n" +
                "\uD83D\uDD25疫情期間鼓勵停課不停學「享有特別專案」\uD83D\uDD25\n" +
                "\uD83D\uDE4C加入老師Line ID：chen-yu-yu（優先享課程優惠與講義試閱）\n" +
                "\uD83C\uDF1F任教於多所補習班、安親班、幼兒園\n" +
                "\uD83C\uDF1F中文教學經驗逾十年\uD83C\uDF1F\n" +
                "\uD83C\uDF1F活潑、有趣的上課方式\uD83C\uDF1F\n" +
                "\uD83C\uDF1F生活化教學，讓你不拘泥於課本\uD83C\uDF1F\n" +
                "\n" +
                "\uD83C\uDF35開設 25分／50分鐘課程\n" +
                "\uD83C\uDF35依學生程度，量身打造專屬學習課程\n" +
                "\uD83C\uDF35專業講義與自編講義搭配使用\n" +
                "\uD83C\uDF35配合小遊戲增加學習趣味\n" +
                "\uD83C\uDF35可彈性排課\n" +
                "\uD83C\uDF35生活化教學\n" +
                "\n" +
                "\n" +
                "\n" +
                "\uD83C\uDF1F開設課程\n" +
                "\uD83C\uDF3E幼兒銜接課程\n" +
                "\uD83C\uDF3E幼兒注音學習\n" +
                "\uD83C\uDF3E繪本故事\n" +
                "\uD83C\uDF3E私中國文\n" +
                "\uD83C\uDF3E作文\n" +
                "\uD83C\uDF3E國小國文\n" +
                "\n" +
                "\uD83C\uDF3E小一正音\n" +
                "     \uD83C\uDF31認讀注音符號\n" +
                "     \uD83C\uDF31拼讀練習\n" +
                "     \uD83C\uDF31閱讀文章\n" +
                "     \uD83C\uDF31歌唱教學\n" +
                "     \uD83C\uDF31基本造詞、造句\n" +
                "     \uD83C\uDF31搭配小一數學先修\n" +
                "\n" +
                "\uD83C\uDF3E作文班 \n" +
                "     \uD83C\uDF31動畫、故事中學習\n" +
                "     \uD83C\uDF31發現孩子的想像力與創造力\n" +
                "     \uD83C\uDF31引導式學習，不制式化書寫\n" +
                "     \uD83C\uDF31句子-->短篇-->長篇文章\n" +
                "      \n" +
                "\uD83C\uDF3E私中/國文(國小)\n" +
                "     \uD83C\uDF31分門別類系統性統整式學習\n" +
                "     \uD83C\uDF31精選考題演練\n" +
                "     \uD83C\uDF31形音義辨正\n" +
                "     \uD83C\uDF31精選閱讀名篇\n" +
                "     \uD83C\uDF31成語/韻文\n" +
                "\n" +
                "\uD83E\uDD17米米老師活潑有趣且生活化的教學深受孩子喜愛\n" +
                "\uD83E\uDD17連古文課程都可以讓學生感到有趣\n" +
                "\uD83E\uDD17從幼稚園到國中的的學生都喜歡\n" +
                "\uD83E\uDD17課程只要開課很快就預約額滿！\n" +
                "\uD83E\uDD17歡迎您先參與體驗課程享受上中文課的樂趣\n" +
                "\n" +
                "\uD83C\uDF1F客製25分／50分鐘課程\uD83C\uDF1F\n" +
                "\uD83C\uDF1F身打造個人學習進度\uD83C\uDF1F\n" +
                "\uD83C\uDF1F可彈性排課\uD83C\uDF1F\n" +
                "\uD83C\uDF1F生活化教學\uD83C\uDF1F\n" +
                "\uD83C\uDF1F專業講義與自編講義搭配使用\uD83C\uDF1F\n" +
                "\uD83C\uDF1F國語課也能有小遊戲\uD83C\uDF1F\n" +
                "\uD83C\uDF1F讀古文也可以很有趣\uD83C\uDF1F\n" +
                "\uD83C\uDF1F從幼稚園到國中的的學生都喜歡\uD83C\uDF1F",
        birthday = stringToDate("1987/07/23"),
        cellPhone = "0922-333-444",
        homePhone = "02-222-3333",
        email = "mimi@gmail.com",
        teacherInfo = TeacherInfo(
            unitsList = unitList_Test.filter { it.educationLevelId == 1L && (it.gradeId == 1L || it.gradeId == 2L) && it.subjectId == 1L },
            //
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
        )
    ),
    ClientInfo(
        id = "yujunId",
        auth = UserPref.Authority.TEACHER,
        bgUrl = "https://images.unsplash.com/photo-1584920956891-2fccb1c144ad?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/251/483/normal/vrvmsw.png?1624516244",
        name = "昱君",
        gender = Gender.FEMALE,
        intro = "關於昱君\n\n" +
                "專攻\n\n" +
                "國中 / 七年級 / 國文" +
                "單元/類型\n\n" +
                "\t\t夏夜 楊喚\n" +
                "\t\t論語選\n\n" +
                "\u200B\u200B✔️語文教育碩士、國文升學考補教多年經驗\n" +
                "\u200B\u200B✔️作文寫作專業教法，學生從200字短文進步成800字長文\n" +
                "\u200B\u200B✔️資深補教國小講師，讓國文奠定未來學習的基石\n" +
                "✔️\u200B\u200B協助多位學生克服會考，國文科目穩居各科第一\n" +
                "\u200B\u200B✔️國文教育啟蒙，成功讓學生重拾對於國文的興趣\n" +
                "✔️\u200B\u200B客製化課程，精準定位你的國文弱點\n" +
                "\n" +
                "\uD83D\uDD0525分鐘國文診療室，讓我一窺你的學習問題\n" +
                "\u200B\u200B\uD83D\uDD05讓昱君老師知道你現在的學習問題吧！\n" +
                "\u200B\u200B\n" +
                "1️⃣教材怎麼處理？免費教材，不需另購課本，客製化你的學習內容\n" +
                "\u200B\u200B2️⃣找出你的學習弱點，輕鬆克服考取理想分數，再也不用事倍功半、失去信心\n" +
                "\u200B\u200B3️⃣為你安排最合適的學習計畫，一起提升國文能力，好懂好記，橫掃各種考題\n" +
                "\u200B\u200B4️⃣國文一點都不難～無論是國小或國高中大考，幫你統整再活用，簡單掌握龐雜的國學常識\n" +
                "5️⃣國小篇章、修辭、長短句⋯都搞不懂，學習瀕臨崩潰，請放心，交給我處理\n" +
                "\n" +
                "\u200B\u200B⭕️國小語文素養培養班\n" +
                "－\u200B\u200B國小階段需要培養閱讀習慣以及語言的敏感度\n" +
                "\u200B\u200B－獨門技巧培養閱讀理解能力，讓國文成為學習的踏板\n" +
                "\u200B\u200B－對文字更加敏銳，能夠更『精準』的表達自己的想法\n" +
                "－\u200B\u200B從國文練習如何抓重點，讓你未來各科學習總是能夠事倍功半\n" +
                "\u200B\u200B\n" +
                "⭕️\u200B\u200B私中入學加強班\n" +
                "－私中提供更嚴謹、多元且全面的教學\n" +
                "－\u200B\u200B國學常識大補帖，教你如何在眾多的題型中找出解題的技巧\n" +
                "－\u200B\u200B作文寫作加強，從寫作結構、篇幅到詞彙的運用補充，教你寫出令人讚嘆不已的文章\n" +
                "\u200B\u200B\n" +
                "\u200B\u200B⭕️國中會考大補帖\n" +
                "－\u200B\u200B國文科目範圍非常廣，完整的統整是快速答題的關鍵\n" +
                "\u200B\u200B－模擬考的內容跳脫課內教材，補充你需要具備的文學素養\n" +
                "\u200B\u200B－專業國文考題解析，完整的國文記憶策略，輕鬆應對大小考試\n" +
                "\u200B\u200B\n" +
                "\u200B\u200B⭕️高中、學測指考衝刺班\n" +
                "－\u200B\u200B解題技巧祕笈，運用多年教學經驗，讓你不再對大考感到措手不及\n" +
                "－專業精簡整理國學常識，以及必考文章，文言文也能一把罩\n" +
                "\u200B\u200B－作文寫作大解析，帶你了解作文寫作篇幅的秘密，一起考取國文頂標\n" +
                "\n" +
                "\u200B\u200B\uD83D\uDCAF教學風格：輕鬆活潑，好懂易記，豐富充實，讓你收穫滿滿\n" +
                "\u200B\u200B\uD83D\uDCAF課程規則：\n" +
                "\u200B\u200B上課記得準時，老師最多會等你10分鐘哦\n" +
                "\u200B\u200B取消課程記得提前 12小時，若無故未出席會視為課程完成哦\n" +
                "\uD83D\uDCAF溫馨提醒：\n" +
                "\u200B\u200B要不斷練習才能進步，記得就算課餘時間也不能鬆懈，有任何問題歡迎隨時跟昱君老師聯絡",
        birthday = stringToDate("1988/05/14"),
        cellPhone = "0933-444-555",
        homePhone = "02-333-4444",
        email = "yujun@gmail.com",
        teacherInfo = TeacherInfo(
            unitsList = unitList_Test.filter { it.educationLevelId == 2L && it.gradeId == 3L && it.subjectId == 4L },
            //
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
        )
    ),
    ClientInfo(
        id = "gingId",
        auth = UserPref.Authority.TEACHER,
        bgUrl = "https://images.unsplash.com/photo-1593642532454-e138e28a63f4?ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/464/931/normal/xdmhqp.png?1609914398",
        name = "謝寶",
        gender = Gender.MALE,
        intro = "✅活化數學教學\uD83D\uDCA1模式\n" +
                "✅17年專業教學\uD83D\uDCAF.教過5000位以上學生資歷\n" +
                "✅1對7家教不同領域範疇精英教學3年多經驗\uD83E\uDD13\n" +
                "✅帶領小六學生考取國中資優班\uD83E\uDDE0錄取率百分之八十五以上\n" +
                "✅現任專業薇閣⚜️數學補教教學一線師資✨\n" +
                "✅過去考取前三志願學生每年占全體百分之十五\n" +
                "✅出版社目前打算商談出書的對象\uD83D\uDCD8\n" +
                "✅最親民\uD83D\uDE04的教學模式勝過填鴨\n" +
                "✅客製化一對一教學⌨️\n" +
                "\n" +
                "課程訓練與特色:\n" +
                "✏️#1 小學資優數學(小一~三)  \n" +
                "25分/堂 或 50分/堂 (由學生個案來調整)\n" +
                "中低年級學習目的在於：\n" +
                "\uD83D\uDD37數學基本邏輯架構\n" +
                "\uD83D\uDD37數的規律\n" +
                "\uD83D\uDD37基本圖形認知\n" +
                "\uD83D\uDD37運算概念\n" +
                "\uD83D\uDD37文字轉換數學語言.各項強化\n" +
                "\n" +
                "✏️#2 小學資優數學(小四~五) \n" +
                " 25分/堂 或 50分/堂 (由學生個案來調整)\n" +
                "中高年級學習目的在於：\n" +
                "\uD83D\uDD37數學邏輯反覆交錯反應\n" +
                "\uD83D\uDD37規律推導\n" +
                "\uD83D\uDD37圖形變通與混合\n" +
                "\uD83D\uDD37運算強化與化繁為簡\n" +
                "\uD83D\uDD37文字觀念一波三折的逐項拆解.各項強化\n" +
                "\uD83D\uDD37未知數建立與計算\n" +
                "\n" +
                "✏️#3 國中會考歷屆試題百分百火力衝刺班\n" +
                "80分/堂\n" +
                "\uD83D\uDD37了解國中課程各章節的定義與觀念\n" +
                "\uD83D\uDD37強化數學語言活用話術的破題\n" +
                "\uD83D\uDD37化繁為簡的圖解題\n" +
                "\uD83D\uDD37每年必考但也是學生必倒的文字作圖題.手把手讓您發現幾何很簡單\n" +
                "⚠️［新春牛\uD83D\uDC02轉乾坤♻️衝刺解題班］\n" +
                "\uD83D\uDD25火熱預約中\uD83D\uDD25\n" +
                "寫了一大堆坊間題本卻分數不見效⁉️\n" +
                "\uD83D\uDC49上課前需先寫完2回與批改\n" +
                "每堂80分鐘/共6堂（每天一堂）2/10.11.12.13.14.15\n" +
                "\uD83D\uDCA1每堂預計可檢討2回會考題型\n" +
                "\uD83D\uDCA1補教長年經驗告訴您拆題猜題㊙️\n" +
                "\uD83D\uDCA1過完個年實力大增像外星人\uD83D\uDEF8\uD83D\uDC7D\n" +
                "\n" +
                "✏️#4 國中會考非選滿級分鑽石班\uD83D\uDC8E\n" +
                "50分/堂（每堂5題）共計20堂\n" +
                "\uD83D\uDD37教材完全活化配合時事\n" +
                "\uD83D\uDD37閱讀整合搭配觀念帶入\n" +
                "\uD83D\uDD37條例式破題讓你輕鬆得分\uD83D\uDE1D\n" +
                "\uD83D\uDD37手能生巧讓你考試不費吹灰之力\uD83D\uDE46\n" +
                "\n" +
                "\uD83C\uDF88條例式的教學［抽絲剝繭］\n" +
                "\uD83C\uDF88圖形破冰破題❄️\uD83D\uDD28，勝過咬文嚼字\n" +
                "\uD83C\uDF88用生活化東西\uD83E\uDD70讓學生學會公式與推導\n" +
                "    ㊙️「配方法」每年都在冬天第三次段考或寒假間學到\n" +
                "          冬天\uD83C\uDF28️想到薑母鴨\uD83E\uDD86就知道要來的\"半平\uD83C\uDF77\"酒\n" +
                "\uD83C\uDF88非單一拘泥解法，只能用某一種方法解題(一題多解)\n" +
                "\uD83C\uDF88重視思考\uD83E\uDD14勝於數量\uD83E\uDD16\n" +
                "⚠️數量只是為了累積實力與數感,而非數學的全部\n" +
                "\uD83C\uDF88換句話說［等位思考方式］培養邏輯的重要\n" +
                "\n" +
                "\uD83E\uDD80客製化的解決學生需求\n" +
                "\uD83E\uDD80專業化的教學講義\n" +
                "\uD83E\uDD80每次上完課會有客製化的作業\n" +
                "\uD83E\uDD80下一次上課前可提早預習教學內容\n" +
                "\uD83E\uDD80教學偏重於能讓學生理解數理觀念\n" +
                "\uD83E\uDD80搭配適量練習，讓學生能舉一反三，\n" +
                "用最有效率的方法準備考試\n" +
                "而不是一昧背誦題目做法的填鴨式教法\n" +
                "㊙️課外有問題也能問我",
        birthday = stringToDate("1977/09/02"),
        cellPhone = "0955-666-777",
        homePhone = "02-555-6666",
        email = "ging@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitList_Test.filter { it.subjectId == 2L }
        )
    ),
    ClientInfo(
        id = "akuanId",
        auth = UserPref.Authority.TEACHER,
        bgUrl = "https://images.unsplash.com/photo-1624804269473-828dcc30a210?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=701&q=80",
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/647/644/normal/dynmkn.png?1621697783",
        name = "阿寬",
        gender = Gender.MALE,
        intro = "同學 ! 不用害怕數學 !\n" +
                "老師會陪著你一起面對數學! 加油!\n" +
                "\n" +
                "\n" +
                "【阿寬哥的教學成果】\n" +
                "\uD83D\uDC3C 使學生從數學第一次段考成績突飛猛進 30分 > 90分\n" +
                "\uD83D\uDC3C 人生的課題不是只有課業，具有社會經驗的我也幫助許多學生了解未來的志願，減少他對未來的迷惘。\n" +
                "\n" +
                "【阿寬哥的教學特色】\n" +
                "\uD83D\uDC3C 階梯式思辨，引導學生。\n" +
                "\uD83D\uDC3C 公式觀念別死背，用過就忘。\n" +
                "\uD83D\uDC3C 一來一往問答方式，讓學生熟悉數學。\n" +
                "\n" +
                "\n" +
                "【阿寬哥的特色】\n" +
                "\uD83D\uDCCC 年輕和善，易於親近，且興趣廣泛任何話題都可以聊，使上課不無聊。\n" +
                "\uD83D\uDCCC 教導學生如何善用筆記，筆記就是自己的寶典，自己的參考書，使學習事半功倍。\n" +
                "\uD83D\uDCCC 讓學生不是只為了考試而讀書，學數學就是鍛鍊自己的邏輯思考，我思，故我在。\n" +
                "\n" +
                "【阿寬哥的教學理念】\n" +
                "\uD83D\uDC3C 還是學不會數學?  --- 阿寬哥給學生適合的教學方式!\n" +
                "\uD83D\uDC3C 考試分數總是打擊你的信心? --- 阿寬哥牽著你找回分數! \n" +
                "\uD83D\uDC3C 總是沒有信心? --- 阿寬哥給你滿滿信心!\n" +
                "\n" +
                "\n" +
                "【選課提醒| 為保障每一位同學權益，請仔細閱讀 \uD83E\uDDD9\uD83C\uDFFB】\n" +
                "\uD83C\uDF6D若要取消課程請12小時前完成取消。\n" +
                "\uD83C\uDF6D若12小時前未完成取消視為曠課。\n" +
                "\uD83C\uDF6A\uD83C\uDF6A 若無法配合請勿提前排課，謝謝 \uD83C\uDF6A\uD83C\uDF6A \n" +
                "\uD83C\uDF6D正式課程10分鐘或體驗課程5分鐘未進教室，視為曠課。該預約課程不設退款或補課。",
        birthday = stringToDate("1982/12/29"),
        cellPhone = "0966-777-888",
        homePhone = "02-666-7777",
        email = "akuan@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitList_Test.filter { it.subjectId == 2L }
        )
    ),
    ClientInfo(
        id = "allenId",
        auth = UserPref.Authority.TEACHER,
        bgUrl = "https://images.unsplash.com/photo-1622495546876-3fccb94d3e2c?ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/643/730/normal/nujeje.png?1624109127",
        name = "艾倫",
        gender = Gender.MALE,
        intro = "\uD83E\uDD47 國中全校第一名畢業\n" +
                "\uD83E\uDD47 高雄第一志願 : 高雄中學畢業\n" +
                "\uD83E\uDD47 高雄中學全校前80名\n" +
                "\uD83E\uDD47 台北醫學大學畢業\n" +
                "\uD83E\uDD47 實體授課人數超過200人\n" +
                "\uD83E\uDD47 2013年家教至迄今將近8年經歷\n" +
                "\uD83E\uDD47 雙北各大補習班老師\n" +
                "樹林領航。南港十葉。宜蘭第一文教。三重紀算。\n" +
                "\n" +
                "生物魔術師培育課程\n" +
                "生物，生活中的物種。艾倫魔術師會帶你認識大自然\n" +
                "\n" +
                "\n" +
                "【艾倫老師的精彩課程】\n" +
                "\uD83D\uDD25 血液是透過什麼樣的黑魔法，流動到全身的呢?--------心臟體循環+肺循環\n" +
                "\uD83D\uDD25 魚類、兩生類、爬蟲類如何在地球上出現，帶你一起體會上帝的戲法-----生物的分類\n" +
                "\uD83D\uDD25 達爾文、達文西、達爾文人名好亂~看艾倫魔術師如何一次講明白-------演化論\n" +
                "\n" +
                "【艾倫老師熱門課程】\n" +
                "\uD83D\uDC0C生物魔術課程熱烈招生中\n" +
                "\uD83D\uDC0C初階魔術實習生: 國一上冊\n" +
                "\uD83D\uDC0C中階魔術培訓生: 國一下冊\n" +
                "\uD83D\uDC0C高階魔術家課程 :生物總複習\n" +
                "\n" +
                "【艾倫老師的課程介紹】\n" +
                "\uD83C\uDF0A 清晰圖解+精美筆記+電子化教學\n" +
                "\uD83C\uDF0A課程中會適時與學生問答，互動不死板\n" +
                "\uD83C\uDF0A 客製化課程，針對不同學生設計課程內容\n" +
                "\uD83C\uDF0A 老師充滿熱忱，面對學生問題，耐心解析\n" +
                "\uD83C\uDF0A 快來跟艾倫老師，一起成為高級魔術師吧\n" +
                "\n" +
                "\n" +
                "【\uD83E\uDDD9\uD83C\uDFFB 選課提醒| 為保障每一位同學權益，請仔細閱讀 \uD83E\uDDD9\uD83C\uDFFB】\n" +
                "\uD83C\uDF6D若要取消課程請12小時前完成取消。\n" +
                "\uD83C\uDF6D若12小時前未完成取消視為曠課。\n" +
                "\uD83C\uDF6A\uD83C\uDF6A 若無法配合請勿提前排課，謝謝 \uD83C\uDF6A\uD83C\uDF6A \n" +
                "\uD83C\uDF6D正式課程10分鐘或體驗課程5分鐘未進教室，視為曠課。該預約課程不設退款或補課。",
        birthday = stringToDate("1992/08/08"),
        cellPhone = "0977-888-999",
        homePhone = "02-777-8888",
        email = "allen@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitList_Test.filter { it.subjectId == 13L }
        )
    ),
    ClientInfo(
        id = "catfishId",
        auth = UserPref.Authority.TEACHER,
        bgUrl = "https://images.unsplash.com/photo-1621569898825-ef12e7592f94?ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80",
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/431/253/normal/wymplr.png?1624816289",
        name = "鯰魚",
        gender = Gender.MALE,
        intro = "\uD83D\uDC1E我專職在補習班/家教/自然科學實驗12年以上\n" +
                "\uD83D\uDC33我很熟悉108課綱發展，快速掌握命題新趨勢\n" +
                "\uD83E\uDD8B主要教授國小、國中、高中生物與自然科學實驗\n" +
                "\uD83D\uDC0C流暢的圖文e化教學，讓你學習不用想像\n" +
                "\uD83D\uDC22讓您孩子快樂學習並輕鬆擁有好成績\n" +
                "\uD83D\uDC3C免費索取【鯰魚生物秘笈】網址: cclfun.com\n" +
                "\n" +
                "\uD83D\uDD25課程內容\uD83D\uDD25\n" +
                "【國小、國高中生物(必修生物、選修生物)】\n" +
                "\uD83D\uDD25特殊課程\uD83D\uDD25\n" +
                "【自然科學實驗，科普教學】\n" +
                "\uD83D\uDD25課程特色\uD83D\uDD25\n" +
                "【客製教學，清晰引導，緊密配合】\n" +
                "\uD83D\uDCAF上課成效\uD83D\uDCAF\n" +
                "【輕鬆念懂自然科學有興趣才能進步】\n" +
                "\uD83D\uDD25主題式課程\uD83D\uDD25\n" +
                "牛糞火鍋\uD83D\uDC04、呼吸循環系統、白血球、工作細胞、萌菌物語、牛嗝氣、盲蛇、犰狳、\uD83D\uDC3B棕熊鮭魚、黑熊排遺、石虎保育、蜥蜴斷尾、遺傳刑事鑑定、遺傳疾病、病毒細菌、食物科學、\uD83D\uDC12大腦不一樣、\uD83D\uDC18演化、有性生殖、動物交配行為、育種雜交、\uD83D\uDC15狗的種類、護卵育幼、藍色的血、螳螂鐵線蟲、油棕櫚危機、藍鯨便便、紫斑蝶、黑面琵鷺、櫻花鉤吻鮭......\n" +
                "\n" +
                "\uD83D\uDC4D我很擅長自然科學，生物是我的強項\n" +
                "\uD83D\uDC4D數理邏輯是做為一個理科人的基礎\n" +
                "\uD83D\uDC4D讓同學以輕鬆的角度學習日常生活的課題\n" +
                "\uD83D\uDC4D所有教材都已電子化不需要額外準備教材\n" +
                "\uD83D\uDC4D練習與訓練會非常扎實\n" +
                "\uD83D\uDC4D持續追蹤你的學習狀況為你引導方向",
        birthday = stringToDate("1976/02/09"),
        cellPhone = "0988-999-000",
        homePhone = "02-888-9999",
        email = "catfish@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitList_Test.filter { it.subjectId == 13L }
        )
    ),
    ClientInfo(
        id = "encoreId",
        auth = UserPref.Authority.TEACHER,
        bgUrl = "https://images.unsplash.com/photo-1624602150320-041eb7374810?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/635/991/normal/bqvcbl.png?1620720822",
        name = "安可",
        gender = Gender.FEMALE,
        intro = "⭐ 經歷豐富 ｜ 實體家教5年、線上家教2年，國中理化皆可！\n" +
                "⭐ 活潑有趣 ｜ 教學輕鬆有趣，遇到不懂的也一定耐心指導！\n" +
                "⭐ 素養導向 ｜ 讓孩子對生物產生興趣，培養出真正的素養，又能反映在成績上！\n" +
                "\n" +
                "【課程包含 / 提供甚麼呢？】\n" +
                "\n" +
                "⭐ 國中生物（國一先修、國一基礎奠定、國三會考衝刺）、高中生物（高一基礎奠定）\n" +
                "⭐ 課前教材提供｜課中問題解惑、概念教授｜課後即時問答\n" +
                "\n" +
                "＃教材都給你－深入淺出教材（含心智圖）上課解鎖完整版，也能課製化教材及內容\n" +
                "＃上課隨你問－聽不懂馬上問！複習上週課程、新概念教授及複習，讓你概念記穩穩\n" +
                "＃課後來幫你－課後提供三次提問機會！自主學習不孤單，也提供課程進度追蹤表呦\n" +
                "\n" +
                "#重要通知\n" +
                "⭐ 目前也開放國中數理科解題，有興趣歡迎連絡討論課程內容！\n" +
                "⭐ 授課價格會受到目前學生數量而影響，為了保持教學品質，學生數每增加三名就會提高價格喲！\n" +
                "→ 所以你現在看到的價格就會是最低價，如果有興趣可以趕緊連絡體驗課，否則金額可能會再升高\n" +
                "2021/6/26  12 → 15 （穩定學生數3名）\n" +
                "\n" +
                "\n" +
                "【⭐安可說說話】\n" +
                "\n" +
                "嗨我是安可！謝謝你看到這裡！\n" +
                "生物是一門非常有趣的學科，更是我們探索世界的第一步！\n" +
                "可以認識自己，認識他人，甚至認識其他物種。\n" +
                "歡迎看自我介紹影片更了解我的教學風格，希望能和你一起透過生物角度認識這個世界！",
        birthday = stringToDate("1986/02/13"),
        cellPhone = "0999-000-111",
        homePhone = "02-999-0000",
        email = "encore@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitList_Test.filter { it.subjectId == 13L }
        )
    )
)
