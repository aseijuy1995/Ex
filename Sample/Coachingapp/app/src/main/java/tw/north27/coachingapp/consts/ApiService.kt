package tw.north27.coachingapp.consts

import android.content.Context
import com.yujie.prefmodule.dataStore.userPref
import com.yujie.prefmodule.protobuf.UserPref
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Query
import timber.log.Timber
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.result.*

class ApiService(val cxt: Context) : IApiService {


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

    private val userIdTest = 0
    private val accountTest = "north27"
    private val passwordTest = "north27"
    private val authorityTest = UserPref.Authority.STUDENT
    private val accessTokenTest = "accessTokenTest"
    private val refreshTokenTest = "refreshTokenTest"
    private val avatarPathTest = "http://static.104.com.tw/b_profile/cust_picture/8063/130000000158063/logo.png?v=20210220092939"
    private val nameTest = "北緯科技"
    private val emailTest = "north27@north27.tw"

    //
    private val signInSuccessMsgTest = "即將登入..."
    private val teacherInfoListTest = listOf<UserInfo>(
        UserInfo(
            id = 1,
            account = "chienChien",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/405/322/normal/hbfwvp.png?1603952064",
            name = "chienChien",
            email = "chienChien@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "哈囉～你好，我是芊芊。\n" +
                        "\uD83D\uDC9B 師大中文畢業、有教學證照TCSOL，聽說讀寫有效加強 \uD83D\uDCAF\n" +
                        "\uD83D\uDC9B 有實體課教學經驗，學生年齡廣： 5~55歲\n" +
                        "\uD83D\uDC9B 可全英文、初級韓語、一點點日語授課 \uD83C\uDD97\n" +
                        "\uD83D\uDC9B 史上最愛笑的老師，孩子們最愛的中文課\n" +
                        "\uD83D\uDC9B 注重口說，輕鬆有趣，上課笑聲\uD83C\uDE35️\uD83C\uDE35️\n" +
                        "\uD83D\uDC9B 可客製化，繁/簡/注音/拼音 \uD83C\uDD97\n" +
                        "\uD83D\uDC9B 認真看待每一堂課，希望學生都在歡樂中有所收穫",
                score = 5.0,
                subjectList = listOf(Subject(id = 1, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "國文"))
            )
        ),
        UserInfo(
            id = 2,
            account = "missLin",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/485/086/normal/spkxab.png?1621247145",
            name = "Miss Lin",
            email = "missLin@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "\uD83D\uDD8A 曾任國民小學老師\uD83D\uDC23 超耐心，有經驗，有方法\n" +
                        "\uD83D\uDC4F 透過繪本、遊戲兒歌、主題式閱讀，培養孩子中文對話能力及語感\n" +
                        "✨ 專注兒童中文教學\uD83D\uDC24 開設25分鐘課程，短時間集中注意力，學習效果也更好喔～\n" +
                        "✏️ 回購課程有優惠有優惠~請諮詢Miss Lin!\n" +
                        "\uD83C\uDF89 獨特的教學法，讓孩子愛上中文學習！點擊【瀏覽全部自我介紹】探索更多\uD83D\uDC47\uD83C\uDFFB\n" +
                        "\uD83C\uDF10 想要更暸解老師，也可以到老師的部落格去看看 kuhsiang.com",
                score = 4.9,
                subjectList = listOf(Subject(id = 1, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "國文"))
            )
        ),
        UserInfo(
            id = 3,
            account = "kIWI",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/063/801/normal/dliopk.png?1587162839",
            name = "KIWI",
            email = "kIWI@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "\uD83D\uDC8E團課滿班滿班滿班~~~熱門團課→✨幼兒4yr ↑ 識字團課+小一翰林+知識中文團課✨→一直開✨一直開✨一直開✨~快來尋問團班時段哦！✨\n" +
                        "\uD83D\uDC8E(為了讓學生能夠達成學習目標，Kiwi花很多的時間備課，所以課表上沒開放太多的時間，若要約課，請傳訊息，Kiwi會幫妳約哦！)\uD83D\uDC8E\n" +
                        "♥️♥️我是Kiwi老師，來自台灣，目前定居加拿大，來看看我的課有什麼特別的♥️♥️\n" +
                        "\n" +
                        "\uD83D\uDC8E老師有10年的豐富經驗，多方面多元化的題材，讓你永遠有討論不完的話題\n" +
                        "\uD83D\uDC8E老師總是全力促進學生的思考、口說\n" +
                        "\uD83D\uDC8E超有趣的小遊戲，提高孩子的上課樂趣",
                score = 4.9,
                subjectList = listOf(Subject(id = 1, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "國文"), Subject(id = 5, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "社會"))
            )
        ),
        UserInfo(
            id = 4,
            account = "andrea",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/381/609/normal/kzxfmi.png?1615736927",
            name = "Andrea",
            email = "andrea@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "\uD83D\uDC49\uD83C\uDFFB已授課時數超過2000小時(含線上與實體)\n" +
                        "\uD83D\uDC49\uD83C\uDFFB學生來自世界各地：美加、歐洲、亞洲、俄羅斯都有我的學生。\n" +
                        "\uD83D\uDC49\uD83C\uDFFB學生年齡廣泛：3至65歲皆有，不論是學齡前亦或是退休後。\n" +
                        "\uD83D\uDD06因應台灣停課，有課業學習需求之台灣學生/家長可以私訊索取優惠價格\uD83D\uDD06\n" +
                        "\uD83D\uDD06如果沒有開放您想選擇的時段，歡迎隨時私訊詢問，Andrea會盡力滿足您需求\uD83D\uDD06\n",
                score = 4.9,
                subjectList = listOf(Subject(id = 1, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "國文"))
            )
        ),
        UserInfo(
            id = 5,
            account = "yoyo",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/179/750/normal/psrnjt.png?1610427294",
            name = "YOYO",
            email = "yoyo@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "\uD83D\uDCE2\uD83D\uDCE2\uD83D\uDCE2多益/文法/閱讀團班開課啦！！！早鳥優惠95折！！！\n" +
                        "\uD83D\uDCCC想提高多益分數，卻沒有方法嗎？\n" +
                        "\uD83D\uDCCC有詞彙不足，文章看不懂的困擾嗎？\n" +
                        "\uD83D\uDCCC想和外國人交流卻有有發音不准，影響聽力的障礙嗎？\n" +
                        "\n" +
                        "\uD83D\uDC54其實YOYO真的懂。\n" +
                        "英語學了20年的我，還以為自己很厲害。\n" +
                        "真正跨出台灣才發現，原來，我只會課本書裡的英文....\n" +
                        "\n" +
                        "\uD83D\uDC54但是我沒有氣餒，所以現在的我能夠進入百大企業。\n" +
                        "也因為英文說得好，在職場占了很大的優勢。\n" +
                        "在公司負責國際市場開拓，台灣，大陸，歐美以及中東。\n" +
                        "\n" +
                        "\uD83D\uDCBCYOYO一路走來磕磕碰碰，如果你想知道...\n" +
                        "①怎麼從多益500分進步到900分！\n" +
                        "②如何跳脫教科書說出一口活英文！\n" +
                        "\uD83C\uDF88時間就是金錢!想把英語學好就快點來吧！\n" +
                        "\uD83C\uDF88Instagram: YOYO_BUSINESS_ENGLISH",
                score = 5.0,
                subjectList = listOf(Subject(id = 2, gradeIdList = listOf(7, 8, 9), text = "英語"))
            )
        ),
        UserInfo(
            id = 6,
            account = "rose",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/049/219/normal/ehtvpb.png?1617187714",
            name = "Rose",
            email = "rose@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "⚠️ 學生已爆量 \uD83D\uDD25 請先私訊詢問、告知程度 \uD83D\uDE0A 謝謝 \n" +
                        "\uD83C\uDF1F 升學補教老師 | 熟知台灣學生的痛點\n" +
                        "\uD83C\uDFC6 平台頂尖教師 | AT 學生破兩百位\n" +
                        "\uD83E\uDD47 5+ 年以上全職英語教學 | 完成破萬堂課程\n" +
                        "\uD83D\uDC8E 教學專長 | 「教科書」所學應用於「生活」和「工作」\n" +
                        "⚡️ 出版影音課程 | 2020年和snapask合作推出“學測黑馬計畫”\n" +
                        "\uD83E\uDDF8 IG: onemin.english 發起人 | 會教的老師只要一分鐘即能解釋易混淆字\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 曾旅居美國 | 標準的美式口音",
                score = 5.0,
                subjectList = listOf(Subject(id = 2, gradeIdList = listOf(7, 8, 9), text = "英語"))
            )
        ),
        UserInfo(
            id = 7,
            account = "micky",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/396/065/normal/cdgzhf.png?1614517235",
            name = "Micky",
            email = "micky@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "❤️你好，那裡有驚人的學習者，這是你的驚人老師米奇！ ❤️\n" +
                        "\uD83D\uDD25。這裡只給您最好的教育。\n" +
                        "\uD83D\uDD25。母語為英語的人，出生於美國\n" +
                        "\uD83D\uDD25。每個課程的重點都是您，您和您！\n" +
                        "\n" +
                        "\n" +
                        "⭐最好的在線英語課程。\n" +
                        "⭐有趣的課程材料。\n" +
                        "⭐保證進度。\n" +
                        "⭐只有最好的英語課程才有6年以上。\n",
                score = 4.9,
                subjectList = listOf(Subject(id = 2, gradeIdList = listOf(7, 8, 9), text = "英語"))
            )
        ),
        UserInfo(
            id = 8,
            account = "tony",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/609/572/normal/nlhdtn.png?1620446864",
            name = "Tony",
            email = "tony@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "跟著Tony學數學是你最好的選擇!! Let's go～～\n" +
                        "\uD83D\uDCD6清大數學系畢業\n" +
                        "\uD83D\uDCD6就讀台大研究所\n" +
                        "\uD83D\uDCD6七年資深家教及補教經驗\n" +
                        "\uD83D\uDCD6已幫助多名學生進入明星中學及大學\n" +
                        "\uD83D\uDCD6完全客製化課程，用學生感興趣的話題引導出背後的數學邏輯\n" +
                        "\n" +
                        "\uD83D\uDCDA25分鐘數學診療門診\uD83D\uDCDA\n" +
                        "讓Tony更了解你的弱點以及程度\n" +
                        "客製出最適合你的課程安排\uD83D\uDC4D\n" +
                        "- 免費教材，不需購買課本\n" +
                        "- 找到學生弱點，擊敗弱點化為優勢！\n" +
                        "- 規劃學習目標以及計劃，有效提升數學能力",
                score = 5.0,
                subjectList = listOf(Subject(id = 3, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "數學"))
            )
        ),
        UserInfo(
            id = 9,
            account = "eason",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/600/908/normal/wvrwhf.png?1618157750",
            name = "Eason陳羿丞",
            email = "eason@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "您好，我在補習班教書已有超過10年的時間，過去幫助許多學生突破自己，不論程度如何，有從段考24分進步到75分的案例，也有從80分進步到穩定95分以上的學生。關鍵是針對各種不同的狀況，找到問題並改變突破，透過我的經驗可以提供您適合的規劃跟建議，找到方法！期待與您討論，怎麼樣順利達標！\n" +
                        "\n" +
                        "1、國中會考自然科統整式教學。\n" +
                        "2、生活化的學習內容，更能理解、記憶、融會貫通。\n" +
                        "3、針對不同程度，給予不同深度跟廣度的內容。\n" +
                        "4、互動式課程，幫助您在當下就學習，減少課後負擔。\n" +
                        "5、透過客製化安排，幫助您順利在會考拿下理想分數。",
                score = 5.0,
                subjectList = listOf(Subject(id = 4, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "自然"))
            )
        ),
        UserInfo(
            id = 10,
            account = "catfishTeacher",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/431/253/normal/jjzfgo.png?1606364504",
            name = "鯰魚老師",
            email = "catfishTeacher@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "\uD83D\uDC1E我專職在補習班/家教/自然科學實驗12年以上\n" +
                        "\uD83D\uDC33我很熟悉108課綱發展，快速掌握命題新趨勢\n" +
                        "\uD83E\uDD8B主要教授國小、國中、高中生物與自然科學實驗\n" +
                        "\uD83D\uDC0C流暢的圖文e化教學，讓你學習不用想像\n" +
                        "\uD83D\uDC22讓您孩子快樂學習並輕鬆擁有好成績\n" +
                        "\uD83D\uDC3C免費索取【鯰魚生物秘笈】網址: cclfun.com",
                score = 5.0,
                subjectList = listOf(Subject(id = 4, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "自然"))
            )
        ),
        UserInfo(
            id = 11,
            account = "zhengKaihan",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/510/964/normal/samstu.png?1611670666",
            name = "鄭凱瀚",
            email = "zhengKaihan@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "就讀國立中山大學，學測社會頂標，教學風格活潑有趣，讓學生不再是死讀書，而是把社會玩得很愉快！不同學生運用不同學習方式，教學時，我會在體驗課觀察你所適合的方式，在正式課程時則是準備好一套專屬於你的教材，讓你得心應手。\n" +
                        "\n" +
                        "重視圖表分類，以及學生自主思考，透過引導式的教學來讓你可以靠自己找出答案，運用想像力來讓你的社會更加清楚。\n" +
                        "社會不是用背的，是用來玩的！在上課過程中，會藉由圖解來讓你更明白，在面對考試時，我會教你如何分解題目，讓你不再是盲目作答。\n" +
                        "\n" +
                        "期待讓你從對社會感興趣到熱愛社會，利用肢體的實際體驗，了解每種地形分類，不同的氣候類型，讓自己用繪畫來彩色出屬於你自己的社會，不需要死背，活絡思緒讓地理自動了解你，只要你想要學習，我能把一切都交給你！",
                score = 4.5,
                subjectList = listOf(Subject(id = 5, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "社會"))
            )
        ),
        UserInfo(
            id = 12,
            account = "lydia",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/335/039/normal/ufchzp.png?1603374353",
            name = "Lydia",
            email = "lydia@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "【舊生回饋大優惠4/15~5/15】\n" +
                        "Step1.撰寫五星評價(內容須含上課內容及狀況）\n" +
                        "Step2..私訊告知我審查！\n" +
                        "Step3.即可享有回購優惠85折\n" +
                        "❗活動僅限4/15至5/15，回購85折優惠亦限於期限內購買❗\n" +
                        "*活動折扣不得與其他優惠券一同使用\n" +
                        "๑۩۞۩๑ ๑۩۞۩๑ ๑۩۞۩๑ ๑۩۞۩๑ ๑۩۞۩๑ ๑۩۞۩๑\n" +
                        "\uD83D\uDD06新制學測國文社會類科頂標，讓我分享我的學習方法，頂標你也可以！\n" +
                        "\uD83D\uDD06國中小、高中歷史、社會各類科私藏學習法，獨家傳授！\n" +
                        "\uD83D\uDD06歷史就是一連串的故事，擺脫學校劃重點背誦，知識融合生活才能貫通～\n" +
                        "\uD83D\uDD06地理才不像社會科！靠理解、想像得高分不難\n" +
                        "\uD83D\uDD06公民是各種社會現況和觀察，讓法律系的我來幫你/妳！",
                score = 5.0,
                subjectList = listOf(Subject(id = 5, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "社會"))
            )
        ),
        UserInfo(
            id = 13,
            account = "jessica",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/083/752/normal/etsomh.png?1613789421",
            name = "Jessica",
            email = "jessica@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "\uD83E\uDD47國立台灣師範大學畢業\n" +
                        "\uD83E\uDD47教育部認證語言教學證照\n" +
                        "\uD83E\uDD477年台灣國中小私立雙語學校家教經驗（康橋、復興、薇格）\n" +
                        "\n" +
                        "可教授的課程：\n" +
                        "\uD83E\uDD47國小：全科教學（國文、英文、數學、社會、自然）\n" +
                        "\uD83E\uDD47國中：國文、英文、社會科\n" +
                        "\n" +
                        "＊可安排每天固定線上輔導完成作業\n" +
                        "＊解題技巧輔導、培養學生解題思維及提升效率\n" +
                        "＊培養學生有效率的讀書技巧及方式\n" +
                        "＊除了教學亦擅長鼓勵激勵學生\n",
                score = 5.0,
                subjectList = listOf(Subject(id = 5, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "社會"))
            )
        ),
        UserInfo(
            id = 14,
            account = "ivy",
            auth = UserPref.Authority.TEACHER,
            avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/432/954/normal/duybxa.png?1620795602",
            name = "Ivy師",
            email = "ivy@gmail.com",
            teacherInfo = TeacherInfo(
                desc = "熟知學校授課方式及教學系統\n" +
                        "掌握解題技巧和出題方向\n" +
                        "不用再苦苦尋覓合適的老師，一鍵開啟試聽\n" +
                        "＝＝＝＝＝＝＝＝＝＝＝＝＝\n" +
                        "女孩上課不尷尬，不必擔心安全問題，輕鬆放心提升成績",
                score = 5.0,
                subjectList = listOf(Subject(id = 5, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "社會"))
            )
        )
    )
    private val gradeListTest = listOf<Grade>(
        Grade(id = 0, text = "預設"),
        Grade(id = 1, text = "一年級"),
        Grade(id = 2, text = "二年級"),
        Grade(id = 3, text = "三年級"),
        Grade(id = 4, text = "四年級"),
        Grade(id = 5, text = "五年級"),
        Grade(id = 6, text = "六年級"),
        Grade(id = 7, text = "七年級"),
        Grade(id = 8, text = "八年級"),
        Grade(id = 9, text = "九年級"),
    )
    private val subjectListTest = listOf<Subject>(
        Subject(id = 0, gradeIdList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), text = "預設"),
        Subject(id = 1, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "國文"),
        Subject(id = 2, gradeIdList = listOf(7, 8, 9), text = "英語"),
        Subject(id = 3, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "數學"),
        Subject(id = 4, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "自然"),
        Subject(id = 5, gradeIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), text = "社會"),
        Subject(id = 6, gradeIdList = listOf(7, 8, 9), text = "科技"),
    )
    private val chapterListTest = listOf<Chapter>(
        Chapter(id = 0, gradeId = 0, subjectId = 0, text = "預設"),
        //
        Chapter(id = 1, gradeId = 1, subjectId = 1, text = "手拉手"),
        Chapter(id = 2, gradeId = 1, subjectId = 1, text = "排一排"),
        Chapter(id = 3, gradeId = 1, subjectId = 1, text = "來數數"),
        Chapter(id = 4, gradeId = 1, subjectId = 1, text = "找一找"),
        Chapter(id = 5, gradeId = 1, subjectId = 1, text = "雨來了"),
        Chapter(id = 6, gradeId = 1, subjectId = 1, text = "山坡上的學校"),
        Chapter(id = 7, gradeId = 1, subjectId = 1, text = "值日生"),
        Chapter(id = 8, gradeId = 1, subjectId = 1, text = "運動會"),
        Chapter(id = 9, gradeId = 1, subjectId = 1, text = "做卡片"),
        Chapter(id = 10, gradeId = 1, subjectId = 1, text = "紙飛機"),
        //
        Chapter(id = 11, gradeId = 2, subjectId = 1, text = "踩影子"),
        Chapter(id = 12, gradeId = 2, subjectId = 1, text = "再玩一次"),
        Chapter(id = 13, gradeId = 2, subjectId = 1, text = "謝謝好朋友"),
        Chapter(id = 14, gradeId = 2, subjectId = 1, text = "水草下的呱呱"),
        Chapter(id = 15, gradeId = 2, subjectId = 1, text = "沙灘上的畫"),
        Chapter(id = 16, gradeId = 2, subjectId = 1, text = "草叢裡的星星"),
        Chapter(id = 17, gradeId = 2, subjectId = 1, text = "神奇的竹筒飯"),
        Chapter(id = 18, gradeId = 2, subjectId = 1, text = "不一樣的故事"),
        Chapter(id = 19, gradeId = 2, subjectId = 1, text = "美味的一堂課"),
        Chapter(id = 20, gradeId = 2, subjectId = 1, text = "加加減減"),
        Chapter(id = 21, gradeId = 2, subjectId = 1, text = "門、鬥大仙"),
        Chapter(id = 22, gradeId = 2, subjectId = 1, text = "詠鵝"),
        //
        Chapter(id = 23, gradeId = 3, subjectId = 1, text = "時間是什麼"),
        Chapter(id = 24, gradeId = 3, subjectId = 1, text = "神奇鐘錶店"),
        Chapter(id = 25, gradeId = 3, subjectId = 1, text = "明天再寫"),
        Chapter(id = 26, gradeId = 3, subjectId = 1, text = "提早五分鐘"),
        Chapter(id = 27, gradeId = 3, subjectId = 1, text = "不賣馬的人"),
        Chapter(id = 28, gradeId = 3, subjectId = 1, text = "老榕樹"),
        Chapter(id = 29, gradeId = 3, subjectId = 1, text = "王子的耳朵"),
        Chapter(id = 30, gradeId = 3, subjectId = 1, text = "世界上的海洋"),
        Chapter(id = 31, gradeId = 3, subjectId = 1, text = "大自然的雕刻家"),
        Chapter(id = 32, gradeId = 3, subjectId = 1, text = "月世界"),
        Chapter(id = 33, gradeId = 3, subjectId = 1, text = "看海豚跳舞"),
        Chapter(id = 34, gradeId = 3, subjectId = 1, text = "客家擂茶"),
        Chapter(id = 35, gradeId = 3, subjectId = 1, text = "秋千上的婚禮"),
        Chapter(id = 36, gradeId = 3, subjectId = 1, text = "小鎮風情"),
        //
        Chapter(id = 37, gradeId = 4, subjectId = 1, text = "水中奇景"),
        Chapter(id = 38, gradeId = 4, subjectId = 1, text = "大海的旋律"),
        Chapter(id = 39, gradeId = 4, subjectId = 1, text = "海底世界"),
        Chapter(id = 40, gradeId = 4, subjectId = 1, text = "藍色的海洋大軍"),
        Chapter(id = 41, gradeId = 4, subjectId = 1, text = "老榕樹下讀報紙"),
        Chapter(id = 42, gradeId = 4, subjectId = 1, text = "特別的滋味"),
        Chapter(id = 43, gradeId = 4, subjectId = 1, text = "收藏秋天"),
        Chapter(id = 44, gradeId = 4, subjectId = 1, text = "靜靜的淡水河"),
        Chapter(id = 45, gradeId = 4, subjectId = 1, text = "鹿谷美地，凍頂茶香"),
        Chapter(id = 46, gradeId = 4, subjectId = 1, text = "落山風"),
        Chapter(id = 47, gradeId = 4, subjectId = 1, text = "澎湖，我來了"),
        Chapter(id = 48, gradeId = 4, subjectId = 1, text = "圓夢之旅"),
        Chapter(id = 49, gradeId = 4, subjectId = 1, text = "松鼠先生的麵包"),
        Chapter(id = 50, gradeId = 4, subjectId = 1, text = "身心手腦四合一"),
        //英文
        Chapter(id = 51, gradeId = 7, subjectId = 2, text = "Who's That Handsome Boy?"),
        Chapter(id = 52, gradeId = 7, subjectId = 2, text = "Where Is the Bedroom?"),
        Chapter(id = 53, gradeId = 7, subjectId = 2, text = "Look at the Sign"),
        Chapter(id = 54, gradeId = 7, subjectId = 2, text = "What Time Is the Concert?"),
        Chapter(id = 55, gradeId = 7, subjectId = 2, text = "What's the Date?"),
        Chapter(id = 56, gradeId = 7, subjectId = 2, text = "There Are Some Elephants over There"),
        //
        Chapter(id = 57, gradeId = 8, subjectId = 2, text = "How Was the Weather in Australia？"),
        Chapter(id = 58, gradeId = 8, subjectId = 2, text = "You Can Learn About Game Design After You Join the Club"),
        Chapter(id = 59, gradeId = 8, subjectId = 2, text = "Mom Was Doing the Dishes at Half past Twelve"),
        Chapter(id = 60, gradeId = 8, subjectId = 2, text = "What Do You Want to Be in the Future？"),
        Chapter(id = 61, gradeId = 8, subjectId = 2, text = "How Do We Get to Big Ben？"),
        Chapter(id = 62, gradeId = 8, subjectId = 2, text = "She’ll Wear a Sweater to the Party"),
        Chapter(id = 63, gradeId = 8, subjectId = 2, text = "She’ll Wear a Sweater to the Party"),
        //
        Chapter(id = 64, gradeId = 9, subjectId = 2, text = "Have You Decided on the Gift?"),
        Chapter(id = 65, gradeId = 9, subjectId = 2, text = "Seeing Is Believing, Isn't It?"),
        Chapter(id = 66, gradeId = 9, subjectId = 2, text = "People Get Excited About Halloween"),
        Chapter(id = 67, gradeId = 9, subjectId = 2, text = "Spiders Are Served as Food Here"),
        Chapter(id = 68, gradeId = 9, subjectId = 2, text = "Can You Tell Me What to Do?"),
        Chapter(id = 69, gradeId = 9, subjectId = 2, text = "They Asked Me If I Liked Taiwan"),
        Chapter(id = 70, gradeId = 9, subjectId = 2, text = "Studying Is Important, and So Is Taking Up a Hobby"),
        Chapter(id = 71, gradeId = 9, subjectId = 2, text = "She Is the Girl Who Helps the Homeless"),
        Chapter(id = 72, gradeId = 9, subjectId = 2, text = "A Girl I Met Online Asked Me Out"),
    )

    var deleteNotify: NotifyInfo? = null
    var isReadAllNotify: Boolean? = null
    override suspend fun getAppConfig(
        @Query(value = "uuid") uuid: String,
        @Query(value = "fcm_token") fcmToken: String
    ): AppConfig {
        delay(1500)
//        return AppConfig(
//            appState = AppState.MAINTAIN,
//            maintainInfo = MaintainInfo(
//                title = "維護通知",
//                text = "1. 不可視因素遭受攻擊。\n" +
//                        "2. 增加監控、效能分析、執行網路維護。\n" +
//                        "3. 描述統一規範化。\n" +
//                        "",
//                time = "2021/03/01 16:00",
//            )
//        )
        return AppConfig(
            appState = AppState.RUN,
            updateInfo = UpdateInfo(
                versionName = "1.0.0",
                url = "https://play.google.com/store/apps/details?id=ojisan.Droid&hl=zh_TW",
                text = "1. 今天要加班(現在幾點了?)\n2. 噴灑殺蟲劑，殺死些Dug蟲蟲\n3. 泡茶休息下~~~\n\t請稍等...",
                size = "5M",
                isMandatory = true
            )
        )
    }

    override suspend fun checkSignIn(
        @Query(value = "uuid") uuid: String,
        @Query(value = "account") account: String,
        @Query(value = "access_token") accessToken: String,
        @Query(value = "fcm_token") fcmToken: String
    ): Response<SignIn> {
        delay(1500)
        val userPref = cxt.userPref.data.first()
        val uuidTest = userPref.uuid
        val fcmTokenTest = userPref.fcmToken
        Timber.d("uuid == uuidTest : ${uuid == uuidTest}")
        Timber.d("fcmToken == fcmTokenTest : ${fcmToken == fcmTokenTest}")
        return if (account == accountTest && accessToken == accessTokenTest)
            Response.success<SignIn>(
                SignIn(
                    signInState = SignInState.SIGN_IN,
                    signInInfo = SignInInfo(
                        userInfo = UserInfo(
                            id = 0,
                            account = accountTest,
                            auth = authorityTest,
                            avatarPath = avatarPathTest,
                            name = nameTest,
                            email = emailTest,
                        ),
                        isFirst = true,
                        accessToken = accessTokenTest,
                        refreshToken = refreshTokenTest,
                        fcmToken = fcmToken,
                        msg = signInSuccessMsgTest
                    )
                )
            )
        else
            Response.success<SignIn>(
                SignIn(
                    signInState = SignInState.SIGN_OUT,
                    signOutInfo = SignOutInfo(
                        msg = "密碼已被修改，請重新登入!"
                    )
                )
            )

    }

    override suspend fun signIn(
        uuid: String,
        account: String,
        password: String,
        fcmToken: String
    ): Response<SignIn> {
        delay(1500)
        return if (account == accountTest && password == passwordTest)
            Response.success<SignIn>(
                SignIn(
                    signInState = SignInState.SIGN_IN,
                    signInInfo = SignInInfo(
                        userInfo = UserInfo(
                            id = userIdTest,
                            account = accountTest,
                            auth = authorityTest,
                            avatarPath = avatarPathTest,
                            name = nameTest,
                            email = emailTest
                        ),
                        isFirst = true,
                        accessToken = accessTokenTest,
                        refreshToken = refreshTokenTest,
                        fcmToken = fcmToken,
                        msg = signInSuccessMsgTest
                    )
                )
            )
        else
            Response.success<SignIn>(
                SignIn(
                    signInState = SignInState.SIGN_OUT,
                    signOutInfo = SignOutInfo("帳號、密碼錯誤，請再試一次")
                )
            )
    }

    override suspend fun signOut(account: String, deviceId: String): SignIn {
        delay(1500)
        return SignIn(
            signInState = SignInState.SIGN_OUT,
            signOutInfo = SignOutInfo(
                msg = "成功登出!"
            )
        )
//        return SignIn(
//            signInState = SignInState.SIGN_IN,
//            signInInfo = SignInInfo(
//                msg = "登出失敗，請刪除APP並重新下載!"
//            )
//        )
    }

    override suspend fun refreshToken(@Query(value = "refresh_token") refreshToken: String): TokenInfo {
        delay(500)
        return TokenInfo(
            accessToken = "accessToken002",
            refreshToken = "refreshToken002"
        )
    }

    override suspend fun getLoadTeacher(): List<UserInfo> {
        delay(1500)
        return teacherInfoListTest
    }

    override suspend fun getGrade(): List<Grade> {
        return gradeListTest
    }

    override suspend fun getSubject(gradeId: Long?): List<Subject> {
        return if (gradeId != null)
            subjectListTest.filter { (it.gradeIdList.find { it == gradeId } != null) }
        else
            subjectListTest.filter { (it.gradeIdList.find { it == 0L } != null) }
    }

    override suspend fun getChapter(gradeId: Long?, subjectId: Long?): List<Chapter> {
        return if (gradeId != null && subjectId != null) {
            chapterListTest.filter { it.gradeId == gradeId && it.subjectId == subjectId }
        } else if (gradeId != null) {
            chapterListTest.filter { it.gradeId == gradeId }
        } else if (subjectId != null) {
            chapterListTest.filter { it.subjectId == subjectId }
        } else {
            chapterListTest.filter { it.gradeId == 0L && it.subjectId == 0L }
        }
    }

    //
    //
    //
    //

    override suspend fun getLoadChat(): List<ChatInfo> {
        delay(1500)
//        return listOf()
        return listOf(
            ChatInfo(
                id = 0,
                sender = UserInfo(
                    id = 0,
                    account = "jason.89",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://cf.shopee.tw/file/7da238c4f922ccc81be94692d9449eec",
                    name = "jason.89"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2/19",
                chatType = ChatType.TEXT,
                text = "不會哦",
                read = ChatRead.HAVE_READ,
                unReadCount = 0,
                isSound = true
            ),
            ChatInfo(
                id = 1,
                sender = UserInfo(
                    id = 1,
                    account = "yc86209",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://cf.shopee.tw/file/1a765f40612e142a1e308bd4c3cb07b9",
                    name = "yc86209"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "1/7",
                chatType = ChatType.TEXT,
                text = "好哦，感謝",
                read = ChatRead.UN_READ,
                unReadCount = 2,
                isSound = true
            ),
            ChatInfo(
                id = 2,
                sender = UserInfo(
                    id = 2,
                    account = "turboted",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://www.actphoto.org.tw/themes/zh-tw/assets/images/default_member.jpg",
                    name = "turboted"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "1/3",
                chatType = ChatType.TEXT,
                text = "已下單有時間再看一下感恩",
                read = ChatRead.UN_READ,
                unReadCount = 5,
                isSound = false
            ),
            ChatInfo(
                id = 3,
                sender = UserInfo(
                    id = 3,
                    account = "ghr7v2c41o",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://cf.shopee.tw/file/da790bc3a58c6099ec5b759af0bda797",
                    name = "ghr7v2c41o"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2020/12/28",
                chatType = ChatType.TEXT,
                text = "您的這個寄件編號一直產生不了 已經三天時間 蝦皮也沒辦法解決 您是否可以重新下訂試試",
                read = ChatRead.UN_READ,
                unReadCount = 3,
                isSound = true
            ),
            ChatInfo(
                id = 4,
                sender = UserInfo(
                    id = 4,
                    account = "iuea654321",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://www.actphoto.org.tw/themes/zh-tw/assets/images/default_member.jpg",
                    name = "iuea654321"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2020/12/17",
                chatType = ChatType.TEXT,
                text = "好哦感謝 待收到領貨 謝謝",
                read = ChatRead.UN_READ,
                unReadCount = 14,
                isSound = true
            ),
            ChatInfo(
                id = 5,
                sender = UserInfo(
                    id = 5,
                    account = "lbj7871",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://cf.shopee.tw/file/66f6a55ddd243f22b78c99847406b516",
                    name = "lbj7871"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2020/12/17",
                chatType = ChatType.TEXT,
                text = "好",
                read = ChatRead.HAVE_READ,
                unReadCount = 0,
                isSound = true
            ),
            ChatInfo(
                id = 6,
                sender = UserInfo(
                    id = 6,
                    account = "jimmy_jbj",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://cf.shopee.tw/file/77f6988c95356ae95bf22ff65682c92c",
                    name = "jimmy_jbj"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2020/12/16",
                chatType = ChatType.TEXT,
                text = "[貼圖]",
                read = ChatRead.UN_READ,
                unReadCount = 12,
                isSound = false
            ),
            ChatInfo(
                id = 7,
                sender = UserInfo(
                    id = 7,
                    account = "cg71124",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://www.actphoto.org.tw/themes/zh-tw/assets/images/default_member.jpg",
                    name = "cg71124"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2020/11/28",
                chatType = ChatType.TEXT,
                text = "你好對哦  一頭是usb 一頭是lighting",
                read = ChatRead.UN_READ,
                unReadCount = 5,
                isSound = false
            ),
            ChatInfo(
                id = 8,
                sender = UserInfo(
                    id = 8,
                    account = "shopee24h",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://cf.shopee.tw/file/589630be1b03af44d5c8ce0ddc73b4e4",
                    name = "shopee24h"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2020/11/10",
                chatType = ChatType.TEXT,
                text = "\uD83D\uDCE3\uD83D\uDCE3\uD83D\uDCE3\uD83D\uDCE3嗨，11.11 最強購物節就是明天！為您送上全平台折扣券優惠~一起剁手吧！\uD83D\uDCE3\uD83D\uDCE3\uD83D\uDCE3\uD83D\uDCE3\n" +
                        "\n" +
                        "\uD83C\uDF81\uD83C\uDF81\uD83C\uDF81",
                read = ChatRead.HAVE_READ,
                unReadCount = 0,
                isSound = true
            ),
            ChatInfo(
                id = 9,
                sender = UserInfo(
                    id = 9,
                    account = "itbook168",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://www.actphoto.org.tw/themes/zh-tw/assets/images/default_member.jpg",
                    name = "itbook168"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2020/8/19",
                chatType = ChatType.TEXT,
                text = "[貼圖]",
                read = ChatRead.HAVE_READ,
                unReadCount = 0,
                isSound = true
            ),
            ChatInfo(
                id = 10,
                sender = UserInfo(
                    id = 10,
                    account = "e4dcomic",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://cf.shopee.tw/file/60f61225f97535ce4a86695743e4d26c",
                    name = "e4dcomic"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2020/5/8",
                chatType = ChatType.TEXT,
                text = "放到購物車會自動結算",
                read = ChatRead.HAVE_READ,
                unReadCount = 0,
                isSound = true
            ),
            ChatInfo(
                id = 11,
                sender = UserInfo(
                    id = 11,
                    account = "l91113001",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://cf.shopee.tw/file/8813359970cc66bca769e27b69701bd7",
                    name = "l91113001"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2020/4/8",
                chatType = ChatType.TEXT,
                text = "[貼圖]",
                read = ChatRead.HAVE_READ,
                unReadCount = 0,
                isSound = true
            ),
            ChatInfo(
                id = 12,
                sender = UserInfo(
                    id = 12,
                    account = "zeloves.hz",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://www.actphoto.org.tw/themes/zh-tw/assets/images/default_member.jpg",
                    name = "zeloves.hz"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2020/3/22",
                chatType = ChatType.TEXT,
                text = "拿到了謝謝",
                read = ChatRead.UN_READ,
                unReadCount = 99,
                isSound = true
            ),
            ChatInfo(
                id = 13,
                sender = UserInfo(
                    id = 13,
                    account = "xhkjdzsf",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://cf.shopee.tw/file/106f2613e695547c9be9a6d7edf21560",
                    name = "xhkjdzsf"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2020/1/1",
                chatType = ChatType.TEXT,
                text = "[貼圖]",
                read = ChatRead.UN_READ,
                unReadCount = 50,
                isSound = true
            ),
            ChatInfo(
                id = 14,
                sender = UserInfo(
                    id = 14,
                    account = "dmf666",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://cf.shopee.tw/file/56022c505d88d5f690e9ab833e1c7593",
                    name = "dmf666"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "2019/11/17",
                chatType = ChatType.TEXT,
                text = "好哦 謝謝",
                read = ChatRead.UN_READ,
                unReadCount = 100,
                isSound = true
            )
        )
    }

    override suspend fun postSwitchChatSound(chat: ChatInfo): Boolean {
        delay(500)
        return true
    }

    override suspend fun deleteChatRoom(chat: ChatInfo): Boolean {
        delay(500)
        return true
    }

    override suspend fun getChatList(@Field(value = "chat") chat: ChatInfo): List<ChatInfo> {
        delay(1500)
        return listOf(
            ChatInfo(
                id = 0,
                sender = UserInfo(
                    id = 100,
                    account = "jie110",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
                    name = "阿吉 - 測試號"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "15:23",
                chatType = ChatType.TEXT,
                text = "我曾經養了一隻可愛的兔子\n" +
                        "在夜市買的，一隻150，米白色，超可愛\uD83D\uDE03\n" +
                        "\n" +
                        "\n" +
                        "而某天，我在花園裡放兔子曬太陽\n" +
                        "\n" +
                        "我媽打開花園的門想要去倒垃圾的瞬間\n" +
                        "兔子蹦蹦跳跳，逃到外面的茶園\n" +
                        "開始享受自由\uD83D\uDE11",
                read = ChatRead.HAVE_READ,
                unReadCount = 0
            ),
            ChatInfo(
                id = 0,
                sender = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉",
                ),
                recipient = UserInfo(
                    id = 100,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
                    name = "阿吉 - 測試號",
                ),
                sendTime = "15:44",
                chatType = ChatType.TEXT,
                text = "當時的我開始緊張的追著跑，\n" +
                        "兔子開心的在這茶園裡跳來跳去\uD83D\uDE11\n" +
                        "\n" +
                        "追不到的我，在心急的狀態下\n" +
                        "開始邊跑邊哭邊喘\uD83D\uDE11\n" +
                        "\n" +
                        "「啊啊嗚嗚嗚，不要再跑了～～～」\n" +
                        "「嗚嗚嗚媽媽你幹嘛開門啦～～～」\n" +
                        "「嗚嗚嗚嗚，你們快點來抓啦～～～」\n" +
                        "\n" +
                        "我大姐打開二樓窗戶，指揮著我\n" +
                        "「在左邊！⋯往右！對！快追！！」\n" +
                        "\n" +
                        "我二姐打開另一個二樓窗戶，吃著吐司\n" +
                        "「哈哈哈哈哈哈哈哈！！」\n" +
                        "\n" +
                        "⋯幹，去死\uD83D\uDE11",
                read = ChatRead.HAVE_READ,
                unReadCount = 0
            ),
            ChatInfo(
                id = 0,
                sender = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                recipient = UserInfo(
                    id = 100,
                    account = "jie110",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
                    name = "阿吉 - 測試號"
                ),
                sendTime = "15:46",
                chatType = ChatType.TEXT,
                text = "我依舊邊大哭邊追\n" +
                        "真的是一直跑一直掉眼淚地大喊\n" +
                        "\n" +
                        "「不要再跑了！蛇會吃掉你啦！！」\n" +
                        "\n" +
                        "\n" +
                        "嗓門大的好處，在這時完美體現了\n" +
                        "我嚎啕大哭到鄰居們紛紛前來抓兔子\n" +
                        "至少有快10個人跟我一起在茶園奔跑\n" +
                        "只為了抓到那隻該死的兔子",
                read = ChatRead.HAVE_READ,
                unReadCount = 0
            ),
            ChatInfo(
                id = 0,
                sender = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                recipient = UserInfo(
                    id = 100,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
                    name = "阿吉 - 測試號"
                ),
                sendTime = "15:46",
                chatType = ChatType.TEXT,
                text = "⋯你們能想像那畫面有多好笑嗎\uD83D\uDE03",
                read = ChatRead.HAVE_READ,
                unReadCount = 0
            ),
            ChatInfo(
                id = 0,
                sender = UserInfo(
                    id = 100,
                    account = "jie110",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
                    name = "阿吉 - 測試號"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "16:01",
                chatType = ChatType.TEXT,
                text = "我爸拿著麻布袋跑來跑去\n" +
                        "我媽一直尖叫的用手指在導航著我們\n" +
                        "其它鄰居還拿掃把、耙子、捕鼠籠（？）\n" +
                        "一起在茶園裡抓一隻兔子",
                read = ChatRead.HAVE_READ,
                unReadCount = 0
            ),
            ChatInfo(
                id = 0,
                sender = UserInfo(
                    id = 100,
                    account = "jie110",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
                    name = "阿吉 - 測試號"
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉"
                ),
                sendTime = "16:19",
                chatType = ChatType.TEXT,
                text = "最終，花了很久的時間\n" +
                        "終於把兔子逼到角落\n" +
                        "我跑過去抱起兔子邊哭邊罵三字經\n" +
                        "\n" +
                        "但兔子在我懷裡，比我還喘⋯\uD83D\uDE11",
                read = ChatRead.HAVE_READ,
                unReadCount = 0
            ),
            ChatInfo(
                id = 0,
                sender = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉",
                ),
                recipient = UserInfo(
                    id = 100,
                    account = "jie110",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
                    name = "阿吉 - 測試號",
                ),
                sendTime = "16:20",
                chatType = ChatType.TEXT,
                text = "而最後我和爸媽一起跟鄰居們道謝\n" +
                        "還摘了一些自己種的木瓜當作小禮物送給鄰居\n" +
                        "\n" +
                        "這件事情才落幕。",
                read = ChatRead.HAVE_READ,
                unReadCount = 0
            ),
            ChatInfo(
                id = 0,
                sender = UserInfo(
                    id = 100,
                    account = "jie110",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
                    name = "阿吉 - 測試號",
                ),
                recipient = UserInfo(
                    id = -1,
                    account = "north27",
                    auth = UserPref.Authority.STUDENT,
                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                    name = "阿吉",
                ),
                sendTime = "18:30",
                chatType = ChatType.TEXT,
                text = "阿為什麼現在又想起這件事情？\n" +
                        "因為鄰居其中一個就是我同學\n" +
                        "剛剛聊天聊到這個\uD83E\uDD72\n" +
                        "事隔快10年了，還在嘲笑我當時哭很大聲\uD83E\uDD72\n" +
                        "\n" +
                        "\n" +
                        "所以真的不能有把柄在朋友手上，\n" +
                        "會被笑到過世為止\uD83E\uDD72\n" +
                        "\n" +
                        "可惡。",
                read = ChatRead.HAVE_READ,
                unReadCount = 0
            )

        )

    }

    override suspend fun getLoadNotify(@Field(value = "page") page: Int): List<NotifyInfo> {
        var notifyList = mutableListOf<Pair<Int, MutableList<NotifyInfo>>>()
        var page = page
        notifyList.add(
            1 to mutableListOf(
                NotifyInfo(
                    id = 0,
                    imgUrl = "https://cf.shopee.tw/file/b7b28075865ae8a751109478b6c59f2b_tn",
                    title = "噓！偷偷告訴你促銷期間輕鬆提升流量秘笈 v1.0.0",
                    desc = "大促期間流量&轉單大幅成長，商品下折扣卻沒有曝光嗎✨使用蝦皮關鍵字廣告讓你輕鬆獲得賣場流量，要曝光和業績就趁現在！",
                    time = "2021-02-26 16:31",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 1,
                    imgUrl = "https://cf.shopee.tw/file/b3275b57f030fac1288e08a75f364017_tn",
                    title = "【重要】3/3(三)自動提款順延至3/10(三)執行 v1.0.1",
                    desc = "親愛的蝦皮用戶您好，由於內部作業系統維護，原定3/3(三)執行的自動提款將順延至3/10(三)執行，後續自動提款作業時間仍維持於3/17、3/31...以此類推，造成您的不便還請見諒\uD83D\uDE47未執行自動提領當週，仍有乙次免費提領機會，還請多加利用\uD83D\uDE4F",
                    time = "2021-02-26 14:33",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 2,
                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                    title = "賣場佈置新組件上線！ v1.0.2",
                    desc = "賣場佈置全新組件讓您展示熱銷商品、新品以及促銷折扣，幫助您打造吸睛的賣場首頁，提升下單轉換率，點入了解更多\uD83D\uDC49",
                    time = "2021-02-23 14:49",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 3,
                    imgUrl = "https://cf.shopee.tw/file/bcf02170cead12ea5d9319f8d4611823_tn",
                    title = "蝦皮動態模板懶人包送給你 v1.0.3",
                    desc = "【直播課程最便利－貼文牆引流量】在家就可以看\uD83D\uDD25到底怎麼經營粉絲，貼文牆怎麼玩？下半年最強功能之一你不能不會！貼文牆各種秘笈我們來教你\uD83D\uDE4C",
                    time = "2021-02-18 20:10",
                    isRead = true,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 4,
                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                    title = "新推出手機版【我的主題活動】 v1.0.4",
                    desc = "為了方便報名主題活動，您將可透過蝦皮APP來管理【我的主題活動】，同時也可即時查詢已報名成功的商品。更多相關說明請參考連結\uD83D\uDC49",
                    time = "2021-02-02 17:01",
                    isRead = true,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 5,
                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                    title = "防詐騙提醒 v1.0.5",
                    desc = "【重要提醒】蝦皮購物貼心提醒您，千萬不要透過LINE或其他通訊軟體約定交易、私下匯款賣家，也不要在商品未確認收到前聽信賣家指示提前點選〔完成訂單〕千萬注意，以免受騙上當哦！",
                    time = "2021-01-28 15:33",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 6,
                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                    title = "賣家中心新功能上線：商品優化工具 v1.0.6",
                    desc = "親愛的賣家您好，賣家中心內的數據中心全新上線商品優化工具，透過商品優化工具可以幫助您找出需要優化的商品並提供調整方向與建議，確保賣場內所有商品內容都是高品質，吸引買家關注！立即點入了解更多\uD83D\uDC49",
                    time = "2021-01-27 17:00",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 7,
                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                    title = "【重要提醒！強化帳戶安全性指南】 v1.0.7",
                    desc = "蝦皮絕不會要求您提供個人密碼、驗證碼，當您有以下狀況：無法登入帳號、有不明提款動作、發現非本人下單的訂單時，請盡速聯繫蝦皮客服團隊。點擊確認了解如何設定高強度密碼／帳戶有安全疑慮該怎麼辦\uD83D\uDC49",
                    time = "2021-01-25 11:03",
                    isRead = true,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 8,
                    imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
                    title = "邀請新朋友來蝦皮 v1.0.8",
                    desc = "新朋友註冊蝦皮後，在蝦皮購物App完成第1筆訂單，可享訂單金額 ",
                    time = "2021-01-11 18:56",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 9,
                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                    title = "您的訂單已被取消！ v1.0.9",
                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                    time = "2021-01-01 12:00",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                )
            )
        )
        notifyList.add(
            2 to mutableListOf(
                NotifyInfo(
                    id = 10,
                    imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
                    title = "邀請新朋友來蝦皮 v1.1.0",
                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                    time = "2020-12-21 19:42",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 11,
                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                    title = "您的訂單已被取消！ v1.1.1",
                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                    time = "2020-12-21 12:00",
                    isRead = true,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 12,
                    imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
                    title = "邀請新朋友來蝦皮 v1.1.2",
                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                    time = "2020-12-20 20:38",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 13,
                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                    title = "您的訂單已被取消！ v1.1.3",
                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                    time = "2020-12-18 12:00",
                    isRead = true,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 14,
                    imgUrl = "https://cf.shopee.tw/file/683d408bda45da45ca2f9414e19a16a0_tn",
                    title = "恭喜! 您已完成跨境實名認證 v1.1.4",
                    desc = "您已經成功地使用街口帳戶 +886972911675 完成了跨境商品實名認證流程。您不需再進行實名認證。請注意，您的跨境購物退款金額將被退回到這個街口帳戶。",
                    time = "2020-11-28 22:57",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 15,
                    imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                    title = "您的訂單已被取消！ v1.1.5",
                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                    time = "2020-11-25 12:00",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 16,
                    imgUrl = "https://cf.shopee.tw/file/79f99f9f49370fef139475db254e8c76_tn",
                    title = "別忘記你購物車內的商品！ v1.1.6",
                    desc = "Android TDD 測試驅動開發：從 UnitTest、TD... 還在你的購物車內，在商品完售前趕快購買！",
                    time = "2020-11-21 20:01",
                    isRead = true,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 17,
                    imgUrl = "https://cf.shopee.tw/file/9f70ed5d9a2f1105b5916c8c1309a721_tn",
                    title = "提醒您有蝦幣即將到期啦！ v1.1.7",
                    desc = "嗨，你的蝦幣即將在9/30, 23:59過期囉！趕緊進蝦皮，折抵蝦幣立即逛\uD83D\uDC49",
                    time = "2020-09-19 09:00",
                    isRead = true,
                    notifyType = NotifyType.NORMAL
                ),
                NotifyInfo(
                    id = 18,
                    imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
                    title = "邀請新朋友來蝦皮 v1.1.8",
                    desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                    time = "2020-07-04 20:21",
                    isRead = false,
                    notifyType = NotifyType.NORMAL
                )
            )
        )
        isReadAllNotify?.let {
            if (it) {
                notifyList = notifyList.map { it.copy() }.toMutableList()
                notifyList.forEach {
                    it.second.forEach { it.isRead = true }
                }
                Timber.d("isReadAllNotify")
            }
            isReadAllNotify = null
        }
        deleteNotify?.let {
            Timber.d("deleteNotify = $it")
            notifyList = notifyList.map { it.copy() }.toMutableList()
            notifyList.forEach {
                val isRemove = it.second.removeAll { it.id == deleteNotify!!.id }
                Timber.d("deleteNotify = isRemove = $isRemove")
            }
            deleteNotify = null
        }
        delay(1500)
        return when {
            page > notifyList.last().first -> emptyList()
            else -> notifyList.first { page == it.first }.second
        }
    }

    override suspend fun postSwitchNotify(isOpen: Boolean): Boolean {
        delay(500)
        return true
    }

    override suspend fun postReadAllNotify(): Boolean {
        isReadAllNotify = true
        delay(1000)
        return true
    }

    override suspend fun deleteNotify(notify: NotifyInfo): Boolean {
        deleteNotify = notify
        delay(500)
        return true
    }

}