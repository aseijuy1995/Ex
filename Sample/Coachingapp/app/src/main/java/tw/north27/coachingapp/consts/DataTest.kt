package tw.north27.coachingapp.consts

import com.yujie.utilmodule.UserPref
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.response.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

val educationListTest = listOf<Education>(
    Education(id = 1, name = "國小"),
    Education(id = 2, name = "國中"),
    Education(id = 3, name = "高中")
)
val gradeListTest = listOf<Grade>(
    Grade(id = 1, name = "(國小)一年級", educationId = 1),
    Grade(id = 2, name = "(國小)二年級", educationId = 1),
    Grade(id = 3, name = "(國小)三年級", educationId = 1),
    Grade(id = 4, name = "(國小)四年級", educationId = 1),
    Grade(id = 5, name = "(國小)五年級", educationId = 1),
    Grade(id = 6, name = "(國小)六年級", educationId = 1),
    //
    Grade(id = 7, name = "(國中)一年級", educationId = 2),
    Grade(id = 8, name = "(國中)二年級", educationId = 2),
    Grade(id = 9, name = "(國中)三年級", educationId = 2),
    //
    Grade(id = 10, name = "(高中)一年級", educationId = 3),
    Grade(id = 11, name = "(高中)二年級", educationId = 3),
    Grade(id = 12, name = "(高中)三年級", educationId = 3),
)
val subjectListTest = listOf<Subject>(
    Subject(id = 1, name = "國語"),
    Subject(id = 2, name = "數學"),
    Subject(id = 3, name = "生活"),
    Subject(id = 4, name = "自然"),
    Subject(id = 5, name = "社會"),
    Subject(id = 6, name = "藝文"),
    Subject(id = 7, name = "綜合"),
    Subject(id = 8, name = "健體"),
    Subject(id = 9, name = "閩南語"),
    Subject(id = 10, name = "國文"),
    Subject(id = 11, name = "英文"),
    Subject(id = 12, name = "科技"),
    Subject(id = 13, name = "生物"),
    Subject(id = 14, name = "物理"),
    Subject(id = 15, name = "化學"),
    Subject(id = 16, name = "地科"),
    Subject(id = 17, name = "地理"),
    Subject(id = 18, name = "歷史"),
    Subject(id = 19, name = "公民")
)
val unitListTest = listOf<Units>(
    //國語
    Units(id = 1, name = "手拉手", subjectId = 1),
    Units(id = 2, name = "排一排", subjectId = 1),
    Units(id = 3, name = "踩影子", subjectId = 1),
    Units(id = 4, name = "再玩一次", subjectId = 1),
    Units(id = 5, name = "時間是什麼", subjectId = 1),
    Units(id = 6, name = "神奇鐘錶店", subjectId = 1),
    Units(id = 7, name = "水中奇景", subjectId = 1),
    Units(id = 8, name = "大海的旋律", subjectId = 1),
    Units(id = 9, name = "貝殼砂", subjectId = 1),
    Units(id = 10, name = "湖邊散步", subjectId = 1),
    Units(id = 11, name = "旅客留言簿", subjectId = 1),
    Units(id = 12, name = "遊走在世界的市場裡", subjectId = 1),
    //數學
    Units(id = 13, name = "10以內的數", subjectId = 2),
    Units(id = 14, name = "比長短", subjectId = 2),
    Units(id = 15, name = "200以內的數", subjectId = 2),
    Units(id = 16, name = "二位數的加減法", subjectId = 2),
    Units(id = 17, name = "數線", subjectId = 2),
    Units(id = 18, name = "10000以內的數", subjectId = 2),
    Units(id = 19, name = "一億以內的數", subjectId = 2),
    Units(id = 20, name = "乘法", subjectId = 2),
    Units(id = 21, name = "多位小數", subjectId = 2),
    Units(id = 22, name = "因數與公因數", subjectId = 2),
    Units(id = 23, name = "最大公因數與最小公倍數", subjectId = 2),
    Units(id = 24, name = "分數的除法", subjectId = 2),
    Units(id = 25, name = "數與數線", subjectId = 2),
    Units(id = 26, name = "標準分解式與分數運算", subjectId = 2),
    Units(id = 27, name = "乘法公式與多項式", subjectId = 2),
    Units(id = 28, name = "二次方根與畢氏定理", subjectId = 2),
    Units(id = 29, name = "相似形", subjectId = 2),
    Units(id = 30, name = "圓形", subjectId = 2),
    Units(id = 31, name = "數與式", subjectId = 2),
    Units(id = 32, name = "指數、對數", subjectId = 2),
    Units(id = 33, name = "三角", subjectId = 2),
    Units(id = 34, name = "直線與圓", subjectId = 2),
    Units(id = 35, name = "機率統計", subjectId = 2),
    Units(id = 36, name = "三角函數", subjectId = 2),
    //生活
    Units(id = 37, name = "上一年級了", subjectId = 3),
    Units(id = 38, name = "我的新學校", subjectId = 3),
    Units(id = 39, name = "奇妙的影子", subjectId = 3),
    Units(id = 40, name = "和風做朋友", subjectId = 3),
    //自然
    Units(id = 41, name = "植物的身體", subjectId = 4),
    Units(id = 42, name = "奇妙的磁鐵", subjectId = 4),
    Units(id = 43, name = "月亮", subjectId = 4),
    Units(id = 44, name = "水生生物的世界", subjectId = 4),
    Units(id = 45, name = "觀測太陽", subjectId = 4),
    Units(id = 46, name = "植物的奧秘", subjectId = 4),
    Units(id = 47, name = "多變的天氣", subjectId = 4),
    Units(id = 48, name = "聲音與樂器", subjectId = 4),
    Units(id = 49, name = "生命世界與科學方法", subjectId = 4),
    Units(id = 50, name = "大生物體的組成氣中的水", subjectId = 4),
    Units(id = 51, name = "基本測量", subjectId = 4),
    Units(id = 52, name = "物質的世界", subjectId = 4),
    Units(id = 53, name = "直線運動", subjectId = 4),
    Units(id = 54, name = "力與運動", subjectId = 4),
    //社會
    Units(id = 55, name = "我會快樂學習", subjectId = 5),
    Units(id = 56, name = "家庭生活", subjectId = 5),
    Units(id = 57, name = "家鄉的地名與位置", subjectId = 5),
    Units(id = 58, name = "家鄉的自然環境", subjectId = 5),
    Units(id = 59, name = "嗨！臺灣你好", subjectId = 5),
    Units(id = 60, name = "臺灣的自然環境", subjectId = 5),
    Units(id = 61, name = "臺灣的自然資源與物產", subjectId = 5),
    Units(id = 62, name = "生產與消費", subjectId = 5),
    Units(id = 63, name = "認識位置與地圖", subjectId = 5),
    Units(id = 64, name = "世界中的臺灣", subjectId = 5),
    Units(id = 65, name = "中國的自然環境", subjectId = 5),
    Units(id = 66, name = "地形", subjectId = 5),
    Units(id = 67, name = "西亞與中亞", subjectId = 5),
    Units(id = 68, name = "歐洲概說與南歐", subjectId = 5),
    //藝文
    Units(id = 69, name = "創意．夢想．家", subjectId = 6),
    Units(id = 70, name = "表演任我行", subjectId = 6),
    Units(id = 71, name = "生活之美", subjectId = 6),
    Units(id = 72, name = "表演任我行", subjectId = 6),
    Units(id = 73, name = "動手玩創意", subjectId = 6),
    Units(id = 74, name = "表演任我行", subjectId = 6),
    Units(id = 75, name = "音樂人生", subjectId = 6),
    Units(id = 76, name = "藝想新世界", subjectId = 6),
    Units(id = 77, name = "表演任我行", subjectId = 6),
    Units(id = 78, name = "藝遊未盡", subjectId = 6),
    Units(id = 79, name = "視覺藝術", subjectId = 6),
    Units(id = 80, name = "歡慶臺灣民俗風華", subjectId = 6),
    Units(id = 81, name = "視覺藝術", subjectId = 6),
    Units(id = 82, name = "成年禮", subjectId = 6),
    Units(id = 83, name = "視覺藝術", subjectId = 6),
    //綜合
    Units(id = 84, name = "當我們同在一起", subjectId = 7),
    Units(id = 85, name = "情緒萬花筒", subjectId = 7),
    Units(id = 86, name = "豐盛的文化饗宴", subjectId = 7),
    Units(id = 87, name = "散播關懷散播愛", subjectId = 7),
    Units(id = 88, name = "嶄新的我", subjectId = 7),
    Units(id = 89, name = "多元的角色", subjectId = 7),
    Units(id = 90, name = "生活大富翁", subjectId = 7),
    Units(id = 91, name = "學習天地", subjectId = 7),
    //健體
    Units(id = 92, name = "成長變變變", subjectId = 8),
    Units(id = 93, name = "營養的食物", subjectId = 8),
    Units(id = 94, name = "吃出健康和活力", subjectId = 8),
    Units(id = 95, name = "我真的很不錯", subjectId = 8),
    Units(id = 96, name = "生命的樂章", subjectId = 8),
    Units(id = 97, name = "傳球遊戲樂無窮", subjectId = 8),
    Units(id = 98, name = "球兒攻防戰", subjectId = 8),
    Units(id = 99, name = "籃球球來瘋", subjectId = 8),
    Units(id = 100, name = "大顯身手", subjectId = 8),
    Units(id = 101, name = "揪團來運動大顯身手", subjectId = 8),
    Units(id = 102, name = "馳騁球場", subjectId = 8),
    Units(id = 103, name = "輕如鴻毛", subjectId = 8),
    Units(id = 104, name = "健康滿點", subjectId = 8),
    Units(id = 105, name = "快樂青春行", subjectId = 8),
    Units(id = 106, name = "致命的吸引力", subjectId = 8),
    Units(id = 107, name = "防疫總動員", subjectId = 8),
    Units(id = 108, name = "家和萬事興", subjectId = 8),
    Units(id = 109, name = "飲食情報站", subjectId = 8),
    //閩南語
    Units(id = 110, name = "親族生活", subjectId = 9),
    Units(id = 111, name = "時間", subjectId = 9),
    Units(id = 112, name = "臺灣是寶島", subjectId = 9),
    Units(id = 113, name = "運動佮安全", subjectId = 9),
    Units(id = 114, name = "語言的趣味", subjectId = 9),
    Units(id = 115, name = "生活佮休閒", subjectId = 9),
    //國文
    Units(id = 116, name = "夏夜 楊喚", subjectId = 10),
    Units(id = 117, name = "論語選", subjectId = 10),
    Units(id = 118, name = "田園之秋選", subjectId = 10),
    Units(id = 119, name = "古詩選", subjectId = 10),
    Units(id = 120, name = "故鄉的桂花雨", subjectId = 10),
    Units(id = 121, name = "生於憂患死於安樂", subjectId = 10),
    Units(id = 122, name = "師說", subjectId = 10),
    Units(id = 123, name = "愛之淚珠", subjectId = 10),
    Units(id = 124, name = "燭之武退秦師", subjectId = 10),
    Units(id = 125, name = "范進中舉", subjectId = 10),
    Units(id = 126, name = "蘭亭集序", subjectId = 10),
    Units(id = 127, name = "花和尚大鬧桃花村", subjectId = 10),
    //英文
    Units(id = 128, name = "Who's That Handsome Boy?", subjectId = 11),
    Units(id = 129, name = "Where Is the Bedroom?", subjectId = 11),
    Units(id = 130, name = "How Was the Weather in Australia？", subjectId = 11),
    Units(id = 131, name = "You Can Learn About Game Design After You Join the Club", subjectId = 11),
    Units(id = 132, name = "Have You Decided on the Gift?", subjectId = 11),
    Units(id = 133, name = "Seeing Is Believing, Isn't It?", subjectId = 11),
    //科技
    Units(id = 134, name = "生活科技導論", subjectId = 12),
    Units(id = 135, name = "認識科技", subjectId = 12),
    Units(id = 136, name = "認識能源", subjectId = 12),
    Units(id = 137, name = "創意線控仿生獸設計", subjectId = 12),
    //生物
    Units(id = 138, name = "細胞的構造與功能", subjectId = 13),
    Units(id = 139, name = "遺傳", subjectId = 13),
    Units(id = 140, name = "遺傳", subjectId = 13),
    Units(id = 141, name = "演化與生物多樣性", subjectId = 13),
    Units(id = 142, name = "生物體的基本構造與功能", subjectId = 13),
    Units(id = 143, name = "維持生命現象的能量", subjectId = 13),
    //物理
    Units(id = 144, name = "科學的態度與方法", subjectId = 14),
    Units(id = 145, name = "物質的組成與交互作用", subjectId = 14),
    Units(id = 146, name = "運動學—直線運動", subjectId = 14),
    Units(id = 147, name = "運動學—平面運動", subjectId = 14),
    Units(id = 148, name = "動量與動量守恆律", subjectId = 14),
    Units(id = 149, name = "萬有引力", subjectId = 14),
    Units(id = 150, name = "熱學", subjectId = 14),
    Units(id = 151, name = "波動", subjectId = 14),
    Units(id = 152, name = "電流﹑電阻與電路", subjectId = 14),
    Units(id = 153, name = "電流的磁效應", subjectId = 14),
    //化學
    Units(id = 154, name = "物質的組成", subjectId = 15),
    Units(id = 155, name = "物質的構造與分類", subjectId = 15),
    Units(id = 156, name = "常見的化學反應", subjectId = 15),
    Units(id = 157, name = "物質的構造與特性", subjectId = 15),
    Units(id = 158, name = "氣體", subjectId = 15),
    Units(id = 159, name = "化學反應速率", subjectId = 15),
    Units(id = 160, name = "原子構造", subjectId = 15),
    Units(id = 161, name = "化學鍵結", subjectId = 15),
    //地科
    Units(id = 162, name = "地球的故事", subjectId = 16),
    Units(id = 163, name = "從地球看宇宙", subjectId = 16),
    Units(id = 164, name = "地球古今談", subjectId = 16),
    Units(id = 165, name = "變動的地球", subjectId = 16),
    //地理
    Units(id = 166, name = "生活的地理", subjectId = 17),
    Units(id = 167, name = "認識地圖", subjectId = 17),
    Units(id = 168, name = "世界的劃分", subjectId = 17),
    Units(id = 169, name = "東北亞(一)", subjectId = 17),
    Units(id = 170, name = "地理議題探索", subjectId = 17),
    Units(id = 171, name = "資源(一)：水", subjectId = 17),
    //歷史
    Units(id = 172, name = "十六世紀中葉前的臺灣與原住民民族", subjectId = 18),
    Units(id = 173, name = "國際競逐時期", subjectId = 18),
    Units(id = 174, name = "建國與政權分裂", subjectId = 18),
    Units(id = 175, name = "從北伐到抗戰", subjectId = 18),
    Units(id = 176, name = "華夏文明的誕生", subjectId = 18),
    Units(id = 177, name = "秦漢至隋唐的文明開展", subjectId = 18),
    //公民
    Units(id = 178, name = "國家的形成與目的", subjectId = 19),
    Units(id = 179, name = "民主政治與憲政主義", subjectId = 19),
)

var uuidTest = ""
val accountTest = "north27"
val passwordTest = "north27"
val expireTimeTest = 1624377600L
var accessTokenTest = "accessTokenTest"
var refreshTokenTest = "refreshTokenTest"
var isFirstTest = true
var pushTokenTest = "pushTokenTest"

val authorityTest = UserPref.Authority.STUDENT

//val authorityTest = UserPref.Authority.TEACHER
var bgUrlTest = "https://images.unsplash.com/photo-1499951360447-b19be8fe80f5?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"
var avatarUrlTest = "http://static.104.com.tw/b_profile/cust_picture/8063/130000000158063/logo.png?v=20210220092939"
var nameTest = "北緯科技"
var genderTest = Gender.MALE
var introTest = "這是簡單的自我介紹！這是簡單的自我介紹2！這是簡單的自我介紹3！這是簡單的自我介紹4！這是簡單的自我介紹5！"
var birthdayTest = setDate("2018-12-21")
var cellPhoneTest = "0912-345-678"
var homePhoneTest = "02-3456-7890"
var emailTest = "north27@north27.tw"
var schoolTest = "新北市板橋區板橋國小"
var gradeIdTest = 8L
var replyNoticeTest = false
var msgNoticeTest = true

fun setDate(date: String): Date {
    return SimpleDateFormat("yyyy-MM-dd").parse(date)
}

val commentListTest = listOf(
    CommentInfo(id = 0, sendAccount = "eliseLeeAccount", sendName = "Elise Lee", receiveAccount = accountTest, receiveName = nameTest, score = 5.0, content = "非常友善的老師，教學引導方式也很有趣，孩子非常有意願開始學習。", date = setDate("2021-06-17"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 1),
    CommentInfo(id = 1, sendAccount = "cathychuAccount", sendName = "CATHY CHU", receiveAccount = accountTest, receiveName = nameTest, score = 4.0, content = "用心教學。", date = setDate("2021-06-12"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 1),
    CommentInfo(id = 2, sendAccount = "harrisAccount", sendName = "Harris", receiveAccount = accountTest, receiveName = nameTest, score = 4.0, content = "老師很友善，也有豐富家教經驗~而老師本身也是教育系背景出身~懂得學生需求，國文和英文也都能細心教授~非常推薦喔!", date = setDate("2021-05-31"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 3),
    CommentInfo(id = 3, sendAccount = "justinAccount", sendName = "Justin", receiveAccount = accountTest, receiveName = nameTest, score = 3.0, content = "Good", date = setDate("2021-01-23"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),
    CommentInfo(id = 4, sendAccount = "chenWenshanAccount", sendName = "陳文山", receiveAccount = accountTest, receiveName = nameTest, score = 5.0, content = "AT平台上難得的一位優質老師！。", date = setDate("2021-01-22"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 5),
    CommentInfo(id = 5, sendAccount = "zachAccount", sendName = "Zach", receiveAccount = accountTest, receiveName = nameTest, score = 4.0, content = "老師的口條與ppt重點整理清晰，非常推薦各位被國文所苦的同學\uD83D\uDC4D\uD83C\uDFFB。", date = setDate("2021-01-16"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),
    CommentInfo(id = 6, sendAccount = "ranniehsieAccount", sendName = "rannie hsie", receiveAccount = accountTest, receiveName = nameTest, score = 5.0, content = "老師很有耐性引導孩子, 有自製教材", date = setDate("2021-01-15"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),
    CommentInfo(id = 7, sendAccount = "wangLiangyinAccount", sendName = "王亮尹", receiveAccount = accountTest, receiveName = nameTest, score = 3.0, content = "great", date = setDate("2021-01-11"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 4),
    CommentInfo(id = 8, sendAccount = "zhaiQingfengAccount", sendName = "翟清峰", receiveAccount = accountTest, receiveName = nameTest, score = 4.0, content = "家裡的姐姐很喜歡上妳的課，平常對國文不怎麼有興趣的都活了起來了，哈", date = setDate("2020-12-29"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 3),
    CommentInfo(id = 9, sendAccount = "wangZhengAccount", sendName = "王正", receiveAccount = accountTest, receiveName = nameTest, score = 5.0, content = "很棒的老師，課前會認真的訊問我的狀況，體驗課後給予適合的規劃與建議！", date = setDate("2020-12-27"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 4),
    CommentInfo(id = 10, sendAccount = "michaelAccount", sendName = "Michael", receiveAccount = accountTest, receiveName = nameTest, score = 4.0, content = "Excellent lesson with interesting material and clear explanations!", date = setDate("2020-12-04"), educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),

    CommentInfo(id = 11, sendAccount = "xieWantingAct", sendName = "謝宛庭", receiveAccount = accountTest, receiveName = nameTest, score = 1.0, content = "(數學)問題已經問一天了，你們的是不是覺得太難故意不答啊，服務品質真是好啊。明天就要段考了都不答是怎樣，我買你們產品有什麼用，需要的時候都是最沒用的，像裝飾品一樣，啊，不對，裝飾品比你們好用多了，至少它還能讓我心情變好，貴公司真的超優秀。你們最好給我在段考前回答。你們是不是要等到富○義○更新了才要回答我，我知道獵人很好看啦，但也不用這麼在乎它啦，只是它的更新速度和貴公司的回答問題速度一樣快，真的讓人很滿意，能有這麼棒的速度，一定有很多人想買吧，畢竟有優質的老師幫助我們學習，令人很高興呢!!", date = setDate("2020-12-02"), educationId = 1, gradeId = 1, subjectId = 2, unitId = 13),
    CommentInfo(id = 12, sendAccount = "lowtideBearAct", sendName = "低潮熊LowtideBear", receiveAccount = accountTest, receiveName = nameTest, score = 2.0, content = "(數學)真心建議買這軟體不如去上補習班，業務嘴說的跟行動上不一樣，真的愛理不理的，客訴也不怕！現在花這爛軟體15萬還是要去補習！爛透了，早知道先看評論，負評滿滿！！！怒氣都快變超級賽亞人了！", date = setDate("2020-12-02"), educationId = 1, gradeId = 2, subjectId = 2, unitId = 16),
    CommentInfo(id = 13, sendAccount = "zhaoZixingAct", sendName = "趙子興", receiveAccount = accountTest, receiveName = nameTest, score = 1.0, content = "(生活)只有爛可以形容..推銷一個網路教學我兒子國中是翰林版本的送來講義是108綱的,錢又不能退,送我平版的電腦又爛,書本根本放在那養蚊子,錢我打算不會繳也跟業務黃先生聯繫也都不太理,決定打去消基會問問。", date = setDate("2020-12-01"), educationId = 1, gradeId = 1, subjectId = 3, unitId = 37),
    CommentInfo(id = 14, sendAccount = "suZongshunAct", sendName = "蘇宗瞬", receiveAccount = accountTest, receiveName = nameTest, score = 1.0, content = "(數學)真的爛 希望有負分系統 說真的看到這篇 請記住不要買他們的任何產品 老師教的差 解題老師根本是照著答案掰答案 學習顧問也是賺到了錢就已讀學生 自身有經驗 我相信那些說他們產品好的都是低學歷的人 要是有點知識 就會發現他們老師和解題老師教的都是錯的 ps他們產品是萬試通 真的是地雷", date = setDate("2020-11-27"), educationId = 1, gradeId = 4, subjectId = 2, unitId = 20),
    CommentInfo(id = 15, sendAccount = "daiFengting", sendName = "戴峰庭", receiveAccount = accountTest, receiveName = nameTest, score = 2.0, content = "(自然)非常好用，問什麼問題，老師都會很快回答", date = setDate("2020-11-25"), educationId = 1, gradeId = 3, subjectId = 4, unitId = 42),
    CommentInfo(id = 16, sendAccount = "kwakWoo-nyeongAct", sendName = "곽우녕", receiveAccount = accountTest, receiveName = nameTest, score = 1.0, content = "(數學)相同題目晚上6.傳一次，到了9.發現沒有被解答，然後又傳了一次，結果到了晚上11.也還是沒有被回………………", date = setDate("2020-11-25"), educationId = 1, gradeId = 4, subjectId = 2, unitId = 19),
    CommentInfo(id = 17, sendAccount = "ruanKimHo", sendName = "阮金和", receiveAccount = accountTest, receiveName = nameTest, score = 1.0, content = "(生活)好用是沒錯，但為什麼有些傳了過不知道幾天才說，為什麼我有疑問奇怪了很急著要看結果結果沒回其他就有這個沒有很不爽可不可以改啊？", date = setDate("2020-11-23"), educationId = 1, gradeId = 2, subjectId = 3, unitId = 39),
    CommentInfo(id = 18, sendAccount = "jACKFFAct", sendName = "JACK_ FF", receiveAccount = accountTest + "1", receiveName = nameTest, score = 2.0, content = "(自然)放了40分鐘沒人回", date = setDate("2020-11-21"), educationId = 1, gradeId = 6, subjectId = 4, unitId = 47),
    CommentInfo(id = 19, sendAccount = "caiWanyu", sendName = "蔡宛育", receiveAccount = accountTest, receiveName = nameTest, score = 3.0, content = "(生活)手機壞掉，換手機，重新下載，用原本的帳密，但始終登不進去，打客服，很複雜難懂。", date = setDate("2020-11-05"), educationId = 1, gradeId = 1, subjectId = 3, unitId = 38),
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

/**
 * 用戶資訊
 * */
val userInfoTest = UserInfo(
    id = uuidTest,
    account = accountTest,
    auth = authorityTest,
    bgUrl = bgUrlTest,
    avatarUrl = avatarUrlTest,
    name = nameTest,
    gender = genderTest,
    intro = introTest,
    birthday = birthdayTest,
    cellPhone = cellPhoneTest,
    homePhone = homePhoneTest,
    email = emailTest,
    studentInfo = StudentInfo(
        school = schoolTest,
        gradeId = gradeIdTest,
    ),
    teacherInfo = TeacherInfo(
        commentScoreAvg = getCommentScoreAvg(),
        commentScoreCountList = getCommentScoreCountList(),
        replyRate = getReplyRate(),
        replyCountList = replyCountListTest,
        unitsList = listOf(
            Units(id = 1, name = "手拉手", subjectId = 1),//國文
            Units(id = 13, name = "10以內的數", subjectId = 2),//數學
        )
    ),
    userConfig = UserConfig(
        replyNotice = replyNoticeTest,
        msgNotice = msgNoticeTest,
    ),
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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//
val teacherInfoListTest = listOf<UserInfo>(
    UserInfo(
        id = "rebecca",
        account = "rebeccaAct",
        auth = UserPref.Authority.TEACHER,
        bgUrl = "https://images.unsplash.com/photo-1622495807835-858ac1da986d?ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=967&q=80",
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/732/757/normal/nlpknz.png?1623138408",
        name = "Rebecca",
        gender = Gender.FEMALE,
        intro = "\uD83D\uDFE1關於Rebecca：\n" +
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
        birthday = setDate("1990-02-05"),
        cellPhone = "0911-222-333",
        homePhone = "02-111-2222",
        email = "rebecca@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = "mimi",
        account = "mimiAct",
        auth = UserPref.Authority.TEACHER,
        bgUrl = "https://images.unsplash.com/photo-1624799993735-41a4ee092f7b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/142/217/normal/vovnbl.png?1621920493",
        name = "米米老師",
        gender = Gender.FEMALE,
        intro = "\uD83D\uDD25疫情期間鼓勵停課不停學「享有特別專案」\uD83D\uDD25\n" +
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
        birthday = setDate("1987-07-23"),
        cellPhone = "0922-333-444",
        homePhone = "02-222-3333",
        email = "mimi@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = "yujun",
        account = "yujunAct",
        auth = UserPref.Authority.TEACHER,
        bgUrl = "https://images.unsplash.com/photo-1584920956891-2fccb1c144ad?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/251/483/normal/vrvmsw.png?1624516244",
        name = "昱君",
        gender = Gender.FEMALE,
        intro = "\u200B\u200B✔️語文教育碩士、國文升學考補教多年經驗\n" +
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
        birthday = setDate("1988-05-14"),
        cellPhone = "0933-444-555",
        homePhone = "02-333-4444",
        email = "yujun@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = "peiYu",
        account = "peiYuAct",
        auth = UserPref.Authority.TEACHER,
        bgUrl = "https://images.unsplash.com/photo-1621570070821-2e2b1358fae3?ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80",
        avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/425/315/normal/acwdjm.png?1605207454",
        name = "裴育",
        gender = Gender.MALE,
        intro = "自己在百世資優數學任教，曾帶過前三志願、延平、薇閣班等私中小組班，也帶過學測班，熟悉學生對大考需求。\n" +
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
        birthday = setDate("1977-09-02"),
        cellPhone = "0944-555-666",
        homePhone = "02-444-5555",
        email = "peiYu@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = "ging",
        account = "gingAct",
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
        birthday = setDate("1977-09-02"),
        cellPhone = "0955-666-777",
        homePhone = "02-555-6666",
        email = "ging@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = "akuan",
        account = "akuanAct",
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
        birthday = setDate("1982-12-29"),
        cellPhone = "0966-777-888",
        homePhone = "02-666-7777",
        email = "akuan@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = "allen",
        account = "allenAct",
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
        birthday = setDate("1992-08-08"),
        cellPhone = "0977-888-999",
        homePhone = "02-777-8888",
        email = "allen@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitListTest.filter { it.subjectId == 13L }
        )
    ),
    UserInfo(
        id = "catfish",
        account = "catfishAct",
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
        birthday = setDate("1976-02-09"),
        cellPhone = "0988-999-000",
        homePhone = "02-888-9999",
        email = "catfish@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitListTest.filter { it.subjectId == 13L }
        )
    ),
    UserInfo(
        id = "encore",
        account = "encoreAct",
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
        birthday = setDate("1986-02-13"),
        cellPhone = "0999-000-111",
        homePhone = "02-999-0000",
        email = "encore@gmail.com",
        teacherInfo = TeacherInfo(
            commentScoreAvg = getCommentScoreAvg(),
            commentScoreCountList = getCommentScoreCountList(),
            replyRate = getReplyRate(),
            replyCountList = replyCountListTest,
            unitsList = unitListTest.filter { it.subjectId == 13L }
        )
    )
)

val askListTest = listOf(
    AskInfo(
        id = 0,
        sender = teacherInfoListTest[0],
        receiver = userInfoTest,
        askType = AskType.TEXT,
        text = "老師請問數學這題怎麼寫？",
        isRead = false,
        sendTime = setDate("2021-06-30"),
        displayMsg = "老師請問國文這題怎麼寫？"
    ),
    AskInfo(
        id = 1,
        sender = teacherInfoListTest[1],
        receiver = userInfoTest,
        askType = AskType.TEXT,
        text = "老師請問國文這題怎麼寫？",
        isRead = false,
        sendTime = setDate("2021-06-24"),
        displayMsg = "老師請問數學這題怎麼寫？"
    )

)
