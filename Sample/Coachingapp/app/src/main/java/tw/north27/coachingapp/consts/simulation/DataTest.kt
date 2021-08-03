package tw.north27.coachingapp.consts.simulation

import tw.north27.coachingapp.model.response.CommentInfo
import tw.north27.coachingapp.model.response.ReplyCountInfo
import tw.north27.coachingapp.model.response.ScoreCountInfo
import tw.north27.coachingapp.model.response.CommonProblem
import tw.north27.coachingapp.model.response.Reflect
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun stringToDate(date: String): Date = SimpleDateFormat("yyyy/MM/dd").parse(date)

fun dateToString(date: Date): String = SimpleDateFormat("yyyy/MM/dd").format(date)

fun stringToDateTime(date: String): Date = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date)

fun dateTimeToString(date: Date): String = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date)


val commentListTest = listOf(
    CommentInfo(id = 0, senderId = "eliseLeeAccount", sendName = "Elise Lee", receiveId = clientId_Test, receiveName = name_Test, score = 5.0, content = "非常友善的老師，教學引導方式也很有趣，孩子非常有意願開始學習。", date = stringToDate("2021/06/17"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 1),
    CommentInfo(id = 1, senderId = "cathychuAccount", sendName = "CATHY CHU", receiveId = clientId_Test, receiveName = name_Test, score = 4.0, content = "用心教學。", date = stringToDate("2021/06/12"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 1),
    CommentInfo(id = 2, senderId = "harrisAccount", sendName = "Harris", receiveId = clientId_Test, receiveName = name_Test, score = 4.0, content = "老師很友善，也有豐富家教經驗~而老師本身也是教育系背景出身~懂得學生需求，國文和英文也都能細心教授~非常推薦喔!", date = stringToDate("2021/05/31"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 3),
    CommentInfo(id = 3, senderId = "justinAccount", sendName = "Justin", receiveId = clientId_Test, receiveName = name_Test, score = 3.0, content = "Good", date = stringToDate("2021/01/23"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),
    CommentInfo(id = 4, senderId = "chenWenshanAccount", sendName = "陳文山", receiveId = clientId_Test, receiveName = name_Test, score = 5.0, content = "AT平台上難得的一位優質老師！。", date = stringToDate("2021/01/22"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 5),
    CommentInfo(id = 5, senderId = "zachAccount", sendName = "Zach", receiveId = clientId_Test, receiveName = name_Test, score = 4.0, content = "老師的口條與ppt重點整理清晰，非常推薦各位被國文所苦的同學\uD83D\uDC4D\uD83C\uDFFB。", date = stringToDate("2021/01/16"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),
    CommentInfo(id = 6, senderId = "ranniehsieAccount", sendName = "rannie hsie", receiveId = clientId_Test, receiveName = name_Test, score = 5.0, content = "老師很有耐性引導孩子, 有自製教材", date = stringToDate("2021/01/15"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),
    CommentInfo(id = 7, senderId = "wangLiangyinAccount", sendName = "王亮尹", receiveId = clientId_Test, receiveName = name_Test, score = 3.0, content = "great", date = stringToDate("2021/01/11"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 4),
    CommentInfo(id = 8, senderId = "zhaiQingfengAccount", sendName = "翟清峰", receiveId = clientId_Test, receiveName = name_Test, score = 4.0, content = "家裡的姐姐很喜歡上妳的課，平常對國文不怎麼有興趣的都活了起來了，哈", date = stringToDate("2020/12/29"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 3),
    CommentInfo(id = 9, senderId = "wangZhengAccount", sendName = "王正", receiveId = clientId_Test, receiveName = name_Test, score = 5.0, content = "很棒的老師，課前會認真的訊問我的狀況，體驗課後給予適合的規劃與建議！", date = stringToDate("2020/12/27"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 4),
    CommentInfo(id = 10, senderId = "michaelAccount", sendName = "Michael", receiveId = clientId_Test, receiveName = name_Test, score = 4.0, content = "Excellent lesson with interesting material and clear explanations!", date = stringToDate("2020/12/04"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),

    CommentInfo(id = 11, senderId = "xieWantingAct", sendName = "謝宛庭", receiveId = clientId_Test, receiveName = name_Test, score = 1.0, content = "(數學)問題已經問一天了，你們的是不是覺得太難故意不答啊，服務品質真是好啊。明天就要段考了都不答是怎樣，我買你們產品有什麼用，需要的時候都是最沒用的，像裝飾品一樣，啊，不對，裝飾品比你們好用多了，至少它還能讓我心情變好，貴公司真的超優秀。你們最好給我在段考前回答。你們是不是要等到富○義○更新了才要回答我，我知道獵人很好看啦，但也不用這麼在乎它啦，只是它的更新速度和貴公司的回答問題速度一樣快，真的讓人很滿意，能有這麼棒的速度，一定有很多人想買吧，畢竟有優質的老師幫助我們學習，令人很高興呢!!", date = stringToDate("2020/12/02"), educationId = 1, gradeId = 1, subjectId = 2, unitId = 13),
    CommentInfo(id = 12, senderId = "lowtideBearAct", sendName = "低潮熊LowtideBear", receiveId = clientId_Test, receiveName = name_Test, score = 2.0, content = "(數學)真心建議買這軟體不如去上補習班，業務嘴說的跟行動上不一樣，真的愛理不理的，客訴也不怕！現在花這爛軟體15萬還是要去補習！爛透了，早知道先看評論，負評滿滿！！！怒氣都快變超級賽亞人了！", date = stringToDate("2020/12/02"), educationId = 1, gradeId = 2, subjectId = 2, unitId = 16),
    CommentInfo(id = 13, senderId = "zhaoZixingAct", sendName = "趙子興", receiveId = clientId_Test, receiveName = name_Test, score = 1.0, content = "(生活)只有爛可以形容..推銷一個網路教學我兒子國中是翰林版本的送來講義是108綱的,錢又不能退,送我平版的電腦又爛,書本根本放在那養蚊子,錢我打算不會繳也跟業務黃先生聯繫也都不太理,決定打去消基會問問。", date = stringToDate("2020/12/01"), educationId = 1, gradeId = 1, subjectId = 3, unitId = 37),
    CommentInfo(id = 14, senderId = "suZongshunAct", sendName = "蘇宗瞬", receiveId = clientId_Test, receiveName = name_Test, score = 1.0, content = "(數學)真的爛 希望有負分系統 說真的看到這篇 請記住不要買他們的任何產品 老師教的差 解題老師根本是照著答案掰答案 學習顧問也是賺到了錢就已讀學生 自身有經驗 我相信那些說他們產品好的都是低學歷的人 要是有點知識 就會發現他們老師和解題老師教的都是錯的 ps他們產品是萬試通 真的是地雷", date = stringToDate("2020/11/27"), educationId = 1, gradeId = 4, subjectId = 2, unitId = 20),
    CommentInfo(id = 15, senderId = "daiFengting", sendName = "戴峰庭", receiveId = clientId_Test, receiveName = name_Test, score = 2.0, content = "(自然)非常好用，問什麼問題，老師都會很快回答", date = stringToDate("2020/11/25"), educationId = 1, gradeId = 3, subjectId = 4, unitId = 42),
    CommentInfo(id = 16, senderId = "kwakWoo-nyeongAct", sendName = "곽우녕", receiveId = clientId_Test, receiveName = name_Test, score = 1.0, content = "(數學)相同題目晚上6.傳一次，到了9.發現沒有被解答，然後又傳了一次，結果到了晚上11.也還是沒有被回………………", date = stringToDate("2020/11/25"), educationId = 1, gradeId = 4, subjectId = 2, unitId = 19),
    CommentInfo(id = 17, senderId = "ruanKimHo", sendName = "阮金和", receiveId = clientId_Test, receiveName = name_Test, score = 1.0, content = "(生活)好用是沒錯，但為什麼有些傳了過不知道幾天才說，為什麼我有疑問奇怪了很急著要看結果結果沒回其他就有這個沒有很不爽可不可以改啊？", date = stringToDate("2020/11/23"), educationId = 1, gradeId = 2, subjectId = 3, unitId = 39),
    CommentInfo(id = 18, senderId = "jACKFFAct", sendName = "JACK_ FF", receiveId = clientId_Test + "1", receiveName = name_Test, score = 2.0, content = "(自然)放了40分鐘沒人回", date = stringToDate("2020/11/21"), educationId = 1, gradeId = 6, subjectId = 4, unitId = 47),
    CommentInfo(id = 19, senderId = "caiWanyu", sendName = "蔡宛育", receiveId = clientId_Test, receiveName = name_Test, score = 3.0, content = "(生活)手機壞掉，換手機，重新下載，用原本的帳密，但始終登不進去，打客服，很複雜難懂。", date = stringToDate("2020/11/05"), educationId = 1, gradeId = 1, subjectId = 3, unitId = 38),
)

/**
 * 評論平均評分
 * */
fun getCommentScoreAvg(): Double {
    val scoreListTest = getCommentScoreCountList()
    val score = scoreListTest.sumByDouble { it.score * it.count }
    val size = scoreListTest.sumBy(ScoreCountInfo::count)
    return DecimalFormat("##0.00").format(score / size).toDouble()
}

/**
 * 評論各得分數量
 * */
fun getCommentScoreCountList(): List<ScoreCountInfo> {
    val commentScoreListTest = commentListTest.groupBy(CommentInfo::score)
    var scoreInfoListTest = mutableListOf<ScoreCountInfo>()
    commentScoreListTest.map { scoreInfoListTest.add(ScoreCountInfo(it.key, it.value.size)) }
    scoreInfoListTest = scoreInfoListTest.sortedBy(ScoreCountInfo::score).toMutableList()
    return scoreInfoListTest
}

/**
 * 取得回覆率
 * */
fun getReplyRate(): Double {
    val count = replyCountListTest.sumOf { it.count }
    return DecimalFormat("##0.00").format((replyCountListTest.find { it.reply == "已回覆" }?.count?.toDouble()!! / count) * 100).toDouble()
}

/**
 * 取得回覆數量
 * */
val replyCountListTest = listOf<ReplyCountInfo>(
    ReplyCountInfo("已回覆", 5),
    ReplyCountInfo("未回覆", 2)
)


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

val shareLinkTest = "好東西就是要分享給新朋友知道！即日起下載\"Coaching\"\n\n登錄Coaching帳戶，這樣完成接受邀請囉！"

val aboutCoachingTest = "在大家引頸期盼下，萬試通推出『解惑通』線上解題平台的APP。\n\n" +
        "是否曾經想過竟然會有補教名師可以親自為你的學習問題做解答？\n\n" +
        "『解惑通』為你做到了，\n\n" +
        "『解惑通』聯合線上一流補教名師及各名校醫科生組成的輔導老師將親自為你的問題一一解答，學習上出現問題不需要再等待，只要登入『解惑通』可以讓你的學習問題快速得到解答。\n\n" +
        "『解惑通』官網：http://www.question123.com.tw"

val commonProblemListTest = listOf(
    CommonProblem(
        id = 1,
        title = "真人線上直播課程，會有互動嗎？有老師嗎？還是只是看錄影檔呢？",
        content = "真人線上直播課程與坊間錄播的影片課程不同，是在約定的時間內上線，與真人教師上課互動，不限地點、載具。另有聊天室可即時談話，每堂課助教均可協助整理問題，課後統一回覆。"
    ),
    CommonProblem(
        id = 2,
        title = "家裡電腦配備沒有很好，上課會不順暢嗎？",
        content = "真人線上直播課程不需高檔的電腦設備，但需要穩定的網路環境。建議在家網路使用Wifi連線會比手機網路穩定，建議最佳使用環境為桌上型電腦或平板，搭配手機互動，學習效果更好喔！使用筆電時，確認筆電為「最佳性能模式」。"
    ),
    CommonProblem(
        id = 3,
        title = "我是MAC系統電腦，請問適合購買菜鳥救星的課程嗎？",
        content = "目前菜鳥救星Live學堂均提供Windows系統下的軟體教學，不額外提供MAC使用方式。如不熟悉MAC與Windows系統間快捷鍵的對應，建議可以至其它提供MAC課程的平台購買喔！"
    ),
    CommonProblem(
        id = 4,
        title = "如果購買的品項後來發現錯誤，可以修改訂單嗎？",
        content = "如果您已經完成購課付款，請洽菜鳥救星Line官方帳號，由專人為您服務。如尚未付款完成，可另外選擇正確品項，再次下單購買即可。"
    ),
    CommonProblem(
        id = 5,
        title = "如果沒有收到發票，是否可以補發？",
        content = "若您已付款完成，在3日內都沒收到「菜鳥救星 電子發票開立通知」的電子信件，請您來信至客服信箱 service@rookiesavior.net與我們聯繫，並提供以下資訊：\n" +
                "付款人姓名\n" +
                "您的購買課程使用的Email帳號和手機電話\n" +
                "提供綠界ECPay訂單編號(請至Email信箱內，查詢綠界ECPay發送之付款成功信件)"
    ),
    CommonProblem(
        id = 6,
        title = "為何雲端教室點選進入教室，會出現null的空白頁面錯誤，無法進入雲端教室上課？",
        content = "此情況為Line內建瀏覽器的問題造成，請學員獨立開啟習慣使用的網頁瀏覽器，輸入雲端教室網址，輸入帳密後，即可正常進入教室。"
    ),
    CommonProblem(
        id = 7,
        title = "為什麼上課一直聽不到聲音？是我的設備問題嗎？",
        content = "菜鳥救星採雲端上課系統，只要能連網，均可以上課。建議上課前，請務必先至https://zoom.us/test進行連線與語音測試。"
    ),
    CommonProblem(
        id = 8,
        title = "請問購買菜鳥救星Live學堂的課程，會提供上課軟體嗎？",
        content = "菜鳥救星課程中提供軟體之試用連結，僅作教學示範服務。試用期限依各軟體廠商規範。菜鳥救星支持購買正版授權軟體，故學習者請自行評估，可向各大通路或官方網站購買。"
    ),
    CommonProblem(
        id = 9,
        title = "請問課程結束後要如何複習或補課？",
        content = "每堂線上直播課程，課後隔天18:00後，均可以在雲端教室直接查看影片，不用擔心漏掉任何一個細節。且經大部份使用者回饋，在下次課程開始前，重新複習上次課程內容，學習效果會更好喔。\n" +
                "*推廣期間免費觀看，依專案不同，觀看期限可能有所調整，菜鳥救星保留修改權益之權利。"
    ),
    CommonProblem(
        id = 10,
        title = "我是第一次購買者，擔心無法順利上課，可以提前登入或測試嗎？",
        content = "建議在購買課課程，請先利用zoom測試，進行會議測試，以確保視訊及音訊都沒問題。\n" +
                "確認會議室連線沒有問題時，建議利用網路連線品質進行網路連線品質測試。\n" +
                "*下載速度(Download)須高於8Mbps，上傳速度(Upload)須高於1Mbps。\n" +
                "\n" +
                "以上都確認無誤，就可以在上課前10分鐘登入菜鳥救星雲端教室，點選進入教室即可開始當天的課程喔！ *由於課前準備中，『進入教室』鈕會呈現反白無法點選，請同學務必等待教室開啟後進入教室。"
    ),
)
val privacyPolicyTest = "學習王科技股份有限公司(以下簡稱本公司)為保護您的個人資料，依據個人資料保護法規定，於下列事由與目的範圍內，說明本公司直接或間接蒐集、處理及利用您的個人資料，當您填表完成，表示您同意以下內容：\n\n" +
        "一、本公司依據個資法及相關法令之規定下，蒐集、處理及利用您的個人資料，目的為提供良好服務及執行業務之必要範圍，包括但不限於進行會員登錄及驗證、數據統計分析、贈品兌換、廣告行銷、服務訊息、特別活動、新產品、新服務之通知等用途，以及其他隱私權保護政策規範之使用方式。 本公司保留隨時修改、補充、或變更之權利，任何修改、補充或變更均透過本公司網站平台通知會員，會員如不同意修改、補充或變更，應立即停止接受或使用會員服務，如於接受前述通知後，仍繼續接受或使用會員服務，將被視為已閱讀、瞭解並同意接受修改、補充或變更。 若您的年紀未滿二十歲，請您的家長（或監護人）一同詳細閱讀、瞭解並同意本會員服務契約之所有內容後，方得接受或使用會員服務。若您接受或使 用會員服務，將視為您的家長（或監護人）已閱讀、瞭解並同意接受本會員服務契約之所有內容。\n\n" +
        "二、您可依您的需要提供個人資料，包含姓名、連絡方式，包括但不限於電話（含手機號碼）、電子信箱、居住地址或其他合於營業項目之特定目的所須蒐集之個人資料及後續與本公司往來之個人資料。\n\n" +
        "三、本公司對您之個人資料利用之期間、地區、對象及方式：\n\n" +
        "1.期間：本公司存續期間或依相關法令或契約約定之保存期限。\n\n" +
        "2.地區：中華民國領域。\n\n" +
        "3.對象：本公司、本公司所屬分支機構、本公司擁有一定比例以上股份之機構、本公司之共同行銷或合作推廣對象、其他與本公司有業務往來或合作之機構。\n\n" +
        "4.方式：以書面、電子郵件、電話、簡訊、傳真或其他合於當時科學技術之適當方式作個人資料之利用。\n\n" +
        "四、您對於本公司保有您之個人資料，除法令另有規定外，得行使下列權利：\n\n" +
        "1.得向本公司查詢、請求閱覽或請求製給複製本，而本公司依法得酌收必要成本費用。\n\n" +
        "2.得向本公司請求補充或更正，惟依法台端應為適當之釋明。\n\n" +
        "3.得向本公司請求停止蒐集、處理或利用及請求刪除。\n\n" +
        "五、您有權選擇是否提供個人資料予本公司，若您選擇不提供個人資料或提供不完全、不正確、不真實之個人資料，或提供後向本公司請求刪除您之個人資料，本公司有權暫時停止提供對您的服務，尚祈見諒。\n\n" +
        "六、您之個人資料蒐集之特定目的消失或期限屆滿時，您同意本公司得繼續保存、處理或利用您之個人資料。除本公司因執行職務或業務所必須或為遵循相關法令規定外，您得向本公司請求停止蒐集、處理或利用或請求刪除您之個人資料。\n\n" +
        "七、若您有任何問題請洽學習王科技股份有限公司客戶服務免付費專線0800-088-589 或隨時利用學習王網站http://www.study123.com.tw 點選客服信箱-聯絡我們，我們將竭誠為您服務。\n\n" +
        "八、您已完全瞭解此一同意符合個資法及相關法規之要求，具有書面同意學習王科技股份有限公司蒐集、處理及利用您個人資料之效果。"

val contactUsTest = "學習王科技股份有限公司版權所有\n\n" +
        "連絡時間\n" +
        "平日一 ~ 五（早上08:00 ~ 下午16:00)\n\n" +
        "連絡手機\n" +
        "0912-345-678\n\n" +
        "連絡電話\n" +
        "(02)345-6789\n\n" +
        "Email\n" +
        "study123.tw@gmail.com\n\n" +
        "Copyright © Study123 Technology\n" +
        "Co.Ltd. All Rights Reserved.\n\n" +
        "官網\n" +
        "www.study123.com.tw"

val reflectListTest = listOf<Reflect>(
    Reflect(id = 0, name = "帳號相關問題"),
    Reflect(id = 0, name = "平台相關問題"),
    Reflect(id = 0, name = "歷程相關問題"),
    Reflect(id = 0, name = "老師回覆相關問題"),
    Reflect(id = 0, name = "發問相關問題"),
    Reflect(id = 0, name = "其他問題"),
)

