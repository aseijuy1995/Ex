package tw.north27.coachingapp.consts

import com.yujie.utilmodule.UserPref
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.Unit
import java.text.DecimalFormat
import java.text.SimpleDateFormat

var accessTokenTest = "accessTokenTest"
var refreshTokenTest = "refreshTokenTest"
val userIdTest = 83138683L
val accountTest = "north27"
val passwordTest = "north27"

//val authorityTest = UserPref.Authority.STUDENT
val authorityTest = UserPref.Authority.TEACHER
var bgPathTest = "https://images.unsplash.com/photo-1499951360447-b19be8fe80f5?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"
var avatarPathTest = "http://static.104.com.tw/b_profile/cust_picture/8063/130000000158063/logo.png?v=20210220092939"
var nameTest = "北緯科技"

var genderTest = Gender.MALE
var introTest = "這是簡單的自我介紹！這是簡單的自我介紹2！這是簡單的自我介紹3！這是簡單的自我介紹4！這是簡單的自我介紹5！"
var birthdayTest = SimpleDateFormat("yyyy-MM-dd").parse("2018-12-21")
var cellPhoneTest = "0912-345-678"
var homePhoneTest = "02-3456-7890"
var emailTest = "north27@north27.tw"
var schoolTest = "新北市板橋區板橋國小"
var gradeIdTest = 8L

var replyNoticeTest = false
var msgNoticeTest = true

val commentListTest = listOf(
    CommentInfo(id = 0, account = "eliseLeeAccount", name = "Elise Lee", score = 5.0, content = "非常友善的老師，教學引導方式也很有趣，孩子非常有意願開始學習。", date = "2021-06-17", educationId = 1, gradeId = 1, subjectId = 1, unitId = 1),
    CommentInfo(id = 1, account = "cathychuAccount", name = "CATHY CHU", score = 4.0, content = "用心教學。", date = "2021-06-12", educationId = 1, gradeId = 1, subjectId = 1, unitId = 1),
    CommentInfo(id = 2, account = "harrisAccount", name = "Harris", score = 4.0, content = "老師很友善，也有豐富家教經驗~而老師本身也是教育系背景出身~懂得學生需求，國文和英文也都能細心教授~非常推薦喔!", date = "2021-05-31", educationId = 1, gradeId = 1, subjectId = 1, unitId = 3),
    CommentInfo(id = 3, account = "justinAccount", name = "Justin", score = 3.0, content = "Good", date = "2021-01-23", educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),
    CommentInfo(id = 4, account = "chenWenshanAccount", name = "陳文山", score = 4.5, content = "AT平台上難得的一位優質老師！。", date = "2021-01-22", educationId = 1, gradeId = 1, subjectId = 1, unitId = 5),
    CommentInfo(id = 5, account = "zachAccount", name = "Zach", score = 4.5, content = "老師的口條與ppt重點整理清晰，非常推薦各位被國文所苦的同學\uD83D\uDC4D\uD83C\uDFFB。", date = "2021-01-16", educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),
    CommentInfo(id = 6, account = "ranniehsieAccount", name = "rannie hsie", score = 5.0, content = "老師很有耐性引導孩子, 有自製教材", date = "2021-01-15", educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),
    CommentInfo(id = 7, account = "wangLiangyinAccount", name = "王亮尹", score = 3.5, content = "great", date = "2021-01-11", educationId = 1, gradeId = 1, subjectId = 1, unitId = 4),
    CommentInfo(id = 8, account = "zhaiQingfengAccount", name = "翟清峰", score = 4.0, content = "家裡的姐姐很喜歡上妳的課，平常對國文不怎麼有興趣的都活了起來了，哈", date = "2020-12-29", educationId = 1, gradeId = 1, subjectId = 1, unitId = 3),
    CommentInfo(id = 9, account = "wangZhengAccount", name = "王正", score = 5.0, content = "很棒的老師，課前會認真的訊問我的狀況，體驗課後給予適合的規劃與建議！", date = "2020-12-27", educationId = 1, gradeId = 1, subjectId = 1, unitId = 4),
    CommentInfo(id = 10, account = "michaelAccount", name = "Michael", score = 4.0, content = "Excellent lesson with interesting material and clear explanations!", date = "2020-12-04", educationId = 1, gradeId = 1, subjectId = 1, unitId = 2),
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

val userInfoTest = UserInfo(
    id = userIdTest,
    account = accountTest,
    auth = authorityTest,
    bgPath = bgPathTest,
    avatarPath = avatarPathTest,
    name = nameTest,
    gender = genderTest,
    birthday = birthdayTest,
    cellPhone = cellPhoneTest,
    homePhone = homePhoneTest,
    email = emailTest,
    intro = introTest,
    studentInfo = StudentInfo(
        school = schoolTest,
        gradeId = gradeIdTest,
    ),
    teacherInfo = TeacherInfo(
        commentScoreAvg = getCommentScoreAvg(),
        commentScoreCountList = getCommentScoreCountList(),
        replyRate = getReplyRate(),
        replyCountList = replyCountListTest,
        subjectList = listOf(
            Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6))
        ),
        unitList = listOf()
    ),
    userConfig = UserConfig(
        replyNotice = replyNoticeTest,
        msgNotice = msgNoticeTest,
    ),
)

val educationListTest = listOf<Education>(
    Education(id = 1, text = "國小"),
    Education(id = 2, text = "國中"),
    Education(id = 3, text = "高中")
)
val gradeListTest = listOf<Grade>(
    Grade(id = 1, text = "(國小)一年級", educationId = 1),
    Grade(id = 2, text = "(國小)二年級", educationId = 1),
    Grade(id = 3, text = "(國小)三年級", educationId = 1),
    Grade(id = 4, text = "(國小)四年級", educationId = 1),
    Grade(id = 5, text = "(國小)五年級", educationId = 1),
    Grade(id = 6, text = "(國小)六年級", educationId = 1),
    Grade(id = 7, text = "(國中)一年級", educationId = 2),
    Grade(id = 8, text = "(國中)二年級", educationId = 2),
    Grade(id = 9, text = "(國中)三年級", educationId = 2),
    Grade(id = 10, text = "(高中)一年級", educationId = 3),
    Grade(id = 11, text = "(高中)二年級", educationId = 3),
    Grade(id = 12, text = "(高中)三年級", educationId = 3),
)
val subjectListTest = listOf<Subject>(
    Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6)),
    Subject(id = 2, text = "數學", educationIdList = listOf(1, 2, 3), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)),
    Subject(id = 3, text = "生活", educationIdList = listOf(1), gradleIdList = listOf(1, 2)),
    Subject(id = 4, text = "自然", educationIdList = listOf(1, 2), gradleIdList = listOf(3, 4, 5, 6, 7, 8, 9)),
    Subject(id = 5, text = "社會", educationIdList = listOf(1, 2), gradleIdList = listOf(3, 4, 5, 6, 7, 8, 9)),
    Subject(id = 6, text = "藝文", educationIdList = listOf(1, 2), gradleIdList = listOf(3, 4, 5, 6, 7, 8, 9)),
    Subject(id = 7, text = "綜合", educationIdList = listOf(1, 2), gradleIdList = listOf(3, 4, 5, 6, 7, 8, 9)),
    Subject(id = 8, text = "健體", educationIdList = listOf(1, 2), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)),
    Subject(id = 9, text = "閩南語", educationIdList = listOf(1), gradleIdList = listOf(2, 4, 6)),
    Subject(id = 10, text = "國文", educationIdList = listOf(2, 3), gradleIdList = listOf(7, 8, 9, 10, 11, 12)),
    Subject(id = 11, text = "英文", educationIdList = listOf(2), gradleIdList = listOf(7, 8, 9)),
    Subject(id = 12, text = "科技", educationIdList = listOf(2), gradleIdList = listOf(7, 8, 9)),
    Subject(id = 13, text = "生物", educationIdList = listOf(3), gradleIdList = listOf(10, 11, 12)),
    Subject(id = 14, text = "物理", educationIdList = listOf(3), gradleIdList = listOf(10, 11, 12)),
    Subject(id = 15, text = "化學", educationIdList = listOf(3), gradleIdList = listOf(10, 11, 12)),
    Subject(id = 16, text = "地科", educationIdList = listOf(3), gradleIdList = listOf(10, 11, 12)),
    Subject(id = 17, text = "地理", educationIdList = listOf(3), gradleIdList = listOf(10, 11, 12)),
    Subject(id = 18, text = "歷史", educationIdList = listOf(3), gradleIdList = listOf(10, 11, 12)),
    Subject(id = 19, text = "公民", educationIdList = listOf(3), gradleIdList = listOf(10)),
)
val unitListTest = listOf<tw.north27.coachingapp.model.Unit>(
    Unit(id = 1, educationId = 1, gradeId = 1, subjectId = 1, text = "手拉手"),
    Unit(id = 2, educationId = 1, gradeId = 1, subjectId = 1, text = "排一排"),
    Unit(id = 3, educationId = 1, gradeId = 1, subjectId = 1, text = "來數數"),
    Unit(id = 4, educationId = 1, gradeId = 1, subjectId = 1, text = "找一找"),
    Unit(id = 5, educationId = 1, gradeId = 2, subjectId = 1, text = "踩影子"),
    Unit(id = 6, educationId = 1, gradeId = 2, subjectId = 1, text = "再玩一次"),
    Unit(id = 7, educationId = 1, gradeId = 2, subjectId = 1, text = "謝謝好朋友"),
    Unit(id = 8, educationId = 1, gradeId = 2, subjectId = 1, text = "統整活動一"),
    Unit(id = 9, educationId = 1, gradeId = 3, subjectId = 1, text = "時間是什麼"),
    Unit(id = 10, educationId = 1, gradeId = 3, subjectId = 1, text = "神奇鐘錶店"),
    Unit(id = 11, educationId = 1, gradeId = 3, subjectId = 1, text = "明天再寫"),
    Unit(id = 12, educationId = 1, gradeId = 3, subjectId = 1, text = "提早五分鐘"),
    Unit(id = 13, educationId = 1, gradeId = 3, subjectId = 1, text = "統整活動一"),
    Unit(id = 14, educationId = 1, gradeId = 4, subjectId = 1, text = "水中奇景"),
    Unit(id = 15, educationId = 1, gradeId = 4, subjectId = 1, text = "大海的旋律"),
    Unit(id = 16, educationId = 1, gradeId = 4, subjectId = 1, text = "海底世界"),
    Unit(id = 17, educationId = 1, gradeId = 4, subjectId = 1, text = "藍色的海洋大軍"),
    Unit(id = 18, educationId = 1, gradeId = 4, subjectId = 1, text = "統整活動一"),
    Unit(id = 19, educationId = 1, gradeId = 5, subjectId = 1, text = "貝殼砂"),
    Unit(id = 20, educationId = 1, gradeId = 5, subjectId = 1, text = "湖邊散步"),
    Unit(id = 21, educationId = 1, gradeId = 5, subjectId = 1, text = "一池子的綠"),
    Unit(id = 22, educationId = 1, gradeId = 5, subjectId = 1, text = "與山為鄰"),
    Unit(id = 23, educationId = 1, gradeId = 5, subjectId = 1, text = "統整活動一"),
    Unit(id = 24, educationId = 1, gradeId = 6, subjectId = 1, text = "旅客留言簿"),
    Unit(id = 25, educationId = 1, gradeId = 6, subjectId = 1, text = "遊走在世界的市場裡"),
    Unit(id = 26, educationId = 1, gradeId = 6, subjectId = 1, text = "我乘雲朵歸來"),
    Unit(id = 27, educationId = 1, gradeId = 6, subjectId = 1, text = "再別康橋"),
    Unit(id = 28, educationId = 1, gradeId = 6, subjectId = 1, text = "統整活動一"),
    //
    Unit(id = 29, educationId = 1, gradeId = 1, subjectId = 2, text = "1～5的數"),
    Unit(id = 31, educationId = 1, gradeId = 1, subjectId = 2, text = "6～10的數"),
    Unit(id = 32, educationId = 1, gradeId = 1, subjectId = 2, text = "點數與對應"),
    Unit(id = 33, educationId = 1, gradeId = 2, subjectId = 2, text = "數到200"),
    Unit(id = 34, educationId = 1, gradeId = 2, subjectId = 2, text = "位值與化聚"),
    Unit(id = 35, educationId = 1, gradeId = 2, subjectId = 2, text = "付錢"),
    Unit(id = 36, educationId = 1, gradeId = 2, subjectId = 2, text = "數的大小比較"),
    Unit(id = 37, educationId = 1, gradeId = 3, subjectId = 2, text = "認識數線"),
    Unit(id = 38, educationId = 1, gradeId = 3, subjectId = 2, text = "在數線上做大小比較"),
    Unit(id = 39, educationId = 1, gradeId = 3, subjectId = 2, text = "在數線上做加減"),
    Unit(id = 40, educationId = 1, gradeId = 3, subjectId = 2, text = "數間隔"),
    Unit(id = 41, educationId = 1, gradeId = 4, subjectId = 2, text = "十萬以內的數"),
    Unit(id = 42, educationId = 1, gradeId = 4, subjectId = 2, text = "認識萬的家族"),
    Unit(id = 43, educationId = 1, gradeId = 4, subjectId = 2, text = "一億以內的數"),
    Unit(id = 44, educationId = 1, gradeId = 4, subjectId = 2, text = "十萬以內的加減"),
    Unit(id = 45, educationId = 1, gradeId = 5, subjectId = 2, text = "三位小數"),
    Unit(id = 46, educationId = 1, gradeId = 5, subjectId = 2, text = "多位小數與大小比較"),
    Unit(id = 47, educationId = 1, gradeId = 5, subjectId = 2, text = "多位小數的加減"),
    Unit(id = 48, educationId = 1, gradeId = 5, subjectId = 2, text = "分數和小數的數線"),
    Unit(id = 49, educationId = 1, gradeId = 6, subjectId = 2, text = "質數與合數"),
    Unit(id = 50, educationId = 1, gradeId = 6, subjectId = 2, text = "質因數分解"),
    Unit(id = 51, educationId = 1, gradeId = 6, subjectId = 2, text = "最大公因數"),
    Unit(id = 52, educationId = 1, gradeId = 6, subjectId = 2, text = "最小公倍數"),
    //
    Unit(id = 53, educationId = 1, gradeId = 1, subjectId = 3, text = "我上一年級了"),
    Unit(id = 54, educationId = 1, gradeId = 1, subjectId = 3, text = "我的新學校"),
    Unit(id = 55, educationId = 1, gradeId = 1, subjectId = 3, text = "大樹高小花香"),
    Unit(id = 56, educationId = 1, gradeId = 1, subjectId = 3, text = "聲音的世界"),
    Unit(id = 57, educationId = 1, gradeId = 1, subjectId = 3, text = "玩具總動員"),
    Unit(id = 58, educationId = 1, gradeId = 1, subjectId = 3, text = "新年快樂"),
    Unit(id = 59, educationId = 1, gradeId = 2, subjectId = 3, text = "奇妙的影子"),
    Unit(id = 60, educationId = 1, gradeId = 2, subjectId = 3, text = "和風做朋友"),
    Unit(id = 61, educationId = 1, gradeId = 2, subjectId = 3, text = "泡泡真有趣"),
    Unit(id = 62, educationId = 1, gradeId = 2, subjectId = 3, text = "動物好朋友"),
    Unit(id = 63, educationId = 1, gradeId = 2, subjectId = 3, text = "美麗的色彩"),
    Unit(id = 64, educationId = 1, gradeId = 2, subjectId = 3, text = "溫暖過冬天"),
    Unit(id = 65, educationId = 1, gradeId = 2, subjectId = 3, text = "溫暖過冬天"),
    //
    Unit(id = 66, educationId = 1, gradeId = 3, subjectId = 4, text = "植物的葉子、莖和根"),
    Unit(id = 67, educationId = 1, gradeId = 3, subjectId = 4, text = "植物的花、果實和種子"),
    Unit(id = 68, educationId = 1, gradeId = 3, subjectId = 4, text = "植物與生活"),
    Unit(id = 69, educationId = 1, gradeId = 4, subjectId = 4, text = "大家來賞月"),
    Unit(id = 70, educationId = 1, gradeId = 4, subjectId = 4, text = "月亮位置的移動"),
    Unit(id = 71, educationId = 1, gradeId = 4, subjectId = 4, text = "月相的變化"),
    Unit(id = 72, educationId = 1, gradeId = 5, subjectId = 4, text = "一天中太陽位置的變化"),
    Unit(id = 73, educationId = 1, gradeId = 5, subjectId = 4, text = "一年中太陽位置的變化"),
    Unit(id = 74, educationId = 1, gradeId = 5, subjectId = 4, text = "太陽與生活"),
    Unit(id = 75, educationId = 1, gradeId = 6, subjectId = 4, text = "大氣中的水"),
    Unit(id = 76, educationId = 1, gradeId = 6, subjectId = 4, text = "認識天氣圖"),
    Unit(id = 77, educationId = 1, gradeId = 6, subjectId = 4, text = "颱風與防災"),
    //
    Unit(id = 78, educationId = 1, gradeId = 3, subjectId = 5, text = "我會認真學習"),
    Unit(id = 79, educationId = 1, gradeId = 3, subjectId = 5, text = "我會善用時間"),
    Unit(id = 80, educationId = 1, gradeId = 4, subjectId = 5, text = "家鄉的地名"),
    Unit(id = 81, educationId = 1, gradeId = 4, subjectId = 5, text = "地圖上的家鄉"),
    Unit(id = 82, educationId = 1, gradeId = 5, subjectId = 5, text = "認識我們的家園"),
    Unit(id = 83, educationId = 1, gradeId = 5, subjectId = 5, text = "海洋中的家園"),
    Unit(id = 84, educationId = 1, gradeId = 6, subjectId = 5, text = "資源與生活"),
    Unit(id = 85, educationId = 1, gradeId = 6, subjectId = 5, text = "物產概況"),
    //
    Unit(id = 86, educationId = 1, gradeId = 3, subjectId = 6, text = "創意大玩家"),
    Unit(id = 87, educationId = 1, gradeId = 3, subjectId = 6, text = "創意冒險地圖"),
    Unit(id = 88, educationId = 1, gradeId = 3, subjectId = 6, text = "奇幻世界"),
    Unit(id = 89, educationId = 1, gradeId = 3, subjectId = 6, text = "家人"),
    Unit(id = 90, educationId = 1, gradeId = 4, subjectId = 6, text = "校園之美"),
    Unit(id = 91, educationId = 1, gradeId = 4, subjectId = 6, text = "生活中的視覺藝術"),
    Unit(id = 92, educationId = 1, gradeId = 4, subjectId = 6, text = "自然之美"),
    Unit(id = 93, educationId = 1, gradeId = 5, subjectId = 6, text = "天生好手"),
    Unit(id = 94, educationId = 1, gradeId = 5, subjectId = 6, text = "我生長的地方"),
    Unit(id = 95, educationId = 1, gradeId = 5, subjectId = 6, text = "環保你我他"),
    Unit(id = 96, educationId = 1, gradeId = 6, subjectId = 6, text = "視覺藝術點線面"),
    Unit(id = 97, educationId = 1, gradeId = 6, subjectId = 6, text = "視覺藝術大進擊"),
    Unit(id = 98, educationId = 1, gradeId = 6, subjectId = 6, text = "版畫藝術"),
    //
    Unit(id = 99, educationId = 1, gradeId = 3, subjectId = 7, text = "認識你我他"),
    Unit(id = 100, educationId = 1, gradeId = 3, subjectId = 7, text = "共同的任務"),
    Unit(id = 101, educationId = 1, gradeId = 4, subjectId = 7, text = "體驗文化活動"),
    Unit(id = 102, educationId = 1, gradeId = 4, subjectId = 7, text = "文化生活小記者"),
    Unit(id = 103, educationId = 1, gradeId = 5, subjectId = 7, text = "環境新鮮事"),
    Unit(id = 104, educationId = 1, gradeId = 5, subjectId = 7, text = "環境適應面面觀"),
    Unit(id = 105, educationId = 1, gradeId = 6, subjectId = 7, text = "時間管理師"),
    Unit(id = 106, educationId = 1, gradeId = 6, subjectId = 7, text = "小小理財員"),
    //
    Unit(id = 107, educationId = 1, gradeId = 1, subjectId = 8, text = "長大真好"),
    Unit(id = 108, educationId = 1, gradeId = 1, subjectId = 8, text = "清潔衛生好習慣"),
    Unit(id = 109, educationId = 1, gradeId = 2, subjectId = 8, text = "健康飲食"),
    Unit(id = 110, educationId = 1, gradeId = 2, subjectId = 8, text = "飲食追追追"),
    Unit(id = 111, educationId = 1, gradeId = 2, subjectId = 8, text = "飲食安全小秘訣"),
    Unit(id = 112, educationId = 1, gradeId = 3, subjectId = 8, text = "奇妙的生命"),
    Unit(id = 113, educationId = 1, gradeId = 3, subjectId = 8, text = "成長的奧妙"),
    Unit(id = 114, educationId = 1, gradeId = 3, subjectId = 8, text = "關懷銀髮族"),
    Unit(id = 115, educationId = 1, gradeId = 3, subjectId = 8, text = "永恆的回憶"),
    Unit(id = 116, educationId = 1, gradeId = 4, subjectId = 8, text = "閃躲高手"),
    Unit(id = 117, educationId = 1, gradeId = 4, subjectId = 8, text = "排球樂"),
    Unit(id = 118, educationId = 1, gradeId = 5, subjectId = 8, text = "籃球天地"),
    Unit(id = 119, educationId = 1, gradeId = 5, subjectId = 8, text = "移動傳球變化多"),
    Unit(id = 120, educationId = 1, gradeId = 5, subjectId = 8, text = "我是神射手"),
    Unit(id = 121, educationId = 1, gradeId = 5, subjectId = 8, text = "與球共舞"),
    Unit(id = 122, educationId = 1, gradeId = 6, subjectId = 8, text = "上籃練習"),
    Unit(id = 123, educationId = 1, gradeId = 6, subjectId = 8, text = "防守動作與移位步伐"),
    Unit(id = 124, educationId = 1, gradeId = 6, subjectId = 8, text = "對戰遊戲"),
    Unit(id = 125, educationId = 1, gradeId = 6, subjectId = 8, text = "趣味鬥牛賽"),
    //
    Unit(id = 126, educationId = 1, gradeId = 2, subjectId = 9, text = "看新娘"),
    Unit(id = 127, educationId = 1, gradeId = 2, subjectId = 9, text = "中秋暝"),
    Unit(id = 128, educationId = 1, gradeId = 2, subjectId = 9, text = "故事磅米芳(一)"),
    Unit(id = 129, educationId = 1, gradeId = 4, subjectId = 9, text = "臺灣！臺灣！"),
    Unit(id = 130, educationId = 1, gradeId = 4, subjectId = 9, text = "伴手禮"),
    Unit(id = 131, educationId = 1, gradeId = 6, subjectId = 9, text = "無尾熊無尾"),
    Unit(id = 132, educationId = 1, gradeId = 6, subjectId = 9, text = "反序詞"),
)

//
val teacherInfoListTest = listOf<UserInfo>(
    UserInfo(
        id = 1,
        account = "beijingTeacherGuo",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/446/248/normal/naumcr.png?1608264749",
        name = "北京郭老師",
        email = "beijingTeacherGuo@gmail.com",
        intro = "\uD83C\uDF1E\uD83C\uDF1E\uD83C\uDF1E介紹新學生，免費領課程，詳情請諮詢我哦。\n" +
                "\uD83C\uDF1E\uD83C\uDF1E\uD83C\uDF1E我是一名全職老師，如有未適合您的上課時間，請聯繫我。\n" +
                "\uD83C\uDF1E\uD83C\uDF1E\uD83C\uDF1E提供25分鐘、50分鐘、80分鐘、110分鐘及定製時間的課程包，購課前可私訊詢問，謝謝。\n" +
                "\uD83C\uDF1E\uD83C\uDF1E\uD83C\uDF1E我的團隊還有其他非常非常優秀的中文老師，如您需要，請聯繫我。\n" +
                "\uD83C\uDF1E\uD83C\uDF1E\uD83C\uDF1E如您想找在內地的事業上的合作夥伴也可以聯繫我哦。\n" +
                "\uD83D\uDC3C\uD83D\uDC3C認識郭郭老師\uD83D\uDC3C\uD83D\uDC3C\n" +
                "✨可說純正普通話、英文\n" +
                "✨長期在北京工作和生活\n" +
                "✨多年線上和線下教學經驗\n" +
                "✨教學對象年齡5-60歲不等\n" +
                "✨學生來自美國，墨西哥，香港，台灣，加拿大，澳大利亞，新加坡，塞爾維亞，意大利，以色列，土耳其等\n" +
                "✨學生重複訂課率高達90%，深受學生歡迎\n" +
                "\n" +
                "以下所有課程，如您想用自己的課本，麻煩在上課前發email給我，越早越好 哈\n" +
                "\n" +
                "\uD83D\uDC30\uD83D\uDC30\uD83D\uDC30PSC課程（以下文件可免費發您PDF版）\n" +
                "\uD83D\uDC30使用教材：普通話水平測試實施綱要（繁體字）（含音視頻文件）\n" +
                "\uD83D\uDC30模擬練習題：30套（可掃描二維碼聽示範朗讀）\n" +
                "依據考試構成分模塊學習：\n" +
                "\uD83D\uDC30讀單音節字詞；讀多音節詞語；選擇判斷；朗讀短文；命題說話。\n" +
                "\uD83D\uDC3D\uD83D\uDC3D普通話級別：\n" +
                "97分及以上—— 一級甲等\n" +
                "92分至97分（不含）—— 一級乙等\n" +
                "87分至92分（不含）—— 二級甲等\n" +
                "80分至87分（不含）—— 二級乙等\n" +
                "\n" +
                "\uD83E\uDD84\uD83E\uDD84\uD83E\uDD84拼音課程\n" +
                "\uD83E\uDD84含23個聲母、6個單韻母、9個複韻母、9個前後鼻音韻母、16個整體認讀\n" +
                "\uD83E\uDD84教授每個字母的純正發音及發音技巧、方法\n" +
                "\uD83E\uDD84含有此聲母或韻母的相關詞語和兒歌幫助其記憶\n" +
                "\uD83E\uDD84做練習題：①看圖寫出或說出丟失的聲母或韻母；②根據漢字寫拼音或根據拼音寫漢字。\n" +
                "\n" +
                "\uD83C\uDF4E\uD83C\uDF4E\uD83C\uDF4EGAPSK幼稚園考級課程\n" +
                "使用教材如下：\n" +
                "\uD83C\uDF4EGAPSK幼稚園（初級）普通話水平考試綱要\n" +
                "\uD83C\uDF4EGAPSK幼稚園（中級）普通話水平考試綱要\n" +
                "\uD83C\uDF4EGAPSK幼稚園（高級）普通話水平考試綱要\n" +
                "\n" +
                "\uD83C\uDF4E\uD83C\uDF4E\uD83C\uDF4EGAPSK小學檔考級課程\n" +
                "初級試（小一至小二）中級試（小三至小四）高級試（小五至小六）\n" +
                "使用教材如下：\n" +
                "\uD83C\uDF4EGAPSK小學檔普通話水平測試綱要\n" +
                "\uD83C\uDF4EGAPSK啓迪應試指南模擬題—小學檔—初級試（小一至小二）\n" +
                "\uD83C\uDF4EGAPSK領航應試指南模擬題—小學檔（小三至小四） \n" +
                "\uD83C\uDF4EGAPSK中學組應試指南（課文詞語+20則命題說話）\n" +
                "\uD83C\uDF4EGAPSK港澳地區中小學普通話水平考試綱要\n" +
                "\n" +
                "\uD83E\uDD96\uD83E\uDD96\uD83E\uDD96成年人口語速成 \n" +
                "\uD83E\uDD96針對香港同胞發音問題逐一糾正\n" +
                "\uD83E\uDD96解決您工作中遇到的問題\n" +
                "\uD83E\uDD96自定義學習內容：您可以將工作中遇到的問題進行統計或發送其他的文件作爲學習材料\n" +
                "\uD83E\uDD96糾音，糾錯，尤其針對平舌音、翹舌音發音不清楚的學生\n" +
                "\uD83E\uDD96學習不同的習俗文化\n" +
                "\n" +
                "\uD83E\uDD9A\uD83E\uDD9A\uD83E\uDD9AHSK1-6級考试课程\n" +
                "\uD83E\uDD9A主要針對有HSK考試需求的同學\n" +
                "\uD83E\uDD9AHSK練習題，試卷批閱\n" +
                "\uD83E\uDD9A完美的文法讓你輕鬆拿高分\n" +
                "\uD83E\uDD9A考試技巧分享\n" +
                "\uD83E\uDD9A為你規劃學習計畫\n" +
                "\n" +
                "❓在我們的首次體驗堂，你會有什麼體驗❓\n" +
                "\uD83D\uDC44我們會討論您的學習需求\uD83D\uDC42\uD83C\uDFFB\uD83D\uDC42\uD83C\uDFFB\n" +
                "\uD83D\uDC40為你安排最好的學習計畫\uD83D\uDCBB\n" +
                "\uD83D\uDD90\uD83C\uDFFB讓我們就一些目標達成共識\uD83D\uDD90\uD83C\uDFFB\n" +
                "\uD83D\uDD25快預約體驗課吧\uD83D\uDD25\n" +
                "\n" +
                "\uD83C\uDFC6\uD83C\uDFC6\uD83C\uDFC6您將收穫\n" +
                "\uD83C\uDFC6專業，高水平，創新，有趣，色彩豐富的PPT上課課件\n" +
                "\uD83C\uDFC6輕鬆，活潑，幽默，開心的課堂教學\n" +
                "\uD83C\uDFC6標準，地道的普通話\n" +
                "\uD83C\uDFC6認真，負責，有愛心的老師全程輔導\n" +
                "\uD83C\uDFC6高效，快速提升您的中文聽說聽力技能水平\n" +
                "\uD83C\uDFC6夯實您的中文語法水平",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 4.99,
            replyRate = 92.0,
            subjectList = listOf(
                Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6))
            ),
            unitList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = 2,
        account = "begonia",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/461/549/normal/fdekja.png?1616501949",
        name = "Begonia老師",
        email = "begonia@gmail.com",
        intro = "【Begonia老師是誰？】\n" +
                "\n" +
                "\uD83C\uDF38 美國 TCSOL (成人/少兒) 師資認證\n" +
                "\uD83C\uDF38 英國 TQUK 師資認證\n" +
                "\uD83C\uDF38 活潑、互動的教學方式\n" +
                "\uD83C\uDF38 老師超有耐心的\n" +
                "\uD83C\uDF38 依學習狀況調整課程內容，幫助孩子不再畏懼中文並且輕鬆簡單的學會中文\n" +
                "\uD83C\uDF38 擔任幼兒園愛心媽媽，著重幼兒、少兒中文\n" +
                "\n" +
                "【Begonia老師的課程內容】\n" +
                "\n" +
                "\uD83C\uDF88 從拼音/注音到漢字認讀 \n" +
                "\uD83C\uDF88 日常生活會話\n" +
                "\uD83C\uDF88 閃卡互動\n" +
                "\uD83C\uDF88 繪本/故事導讀、共讀\n" +
                "\uD83C\uDF88 看圖說故事\n" +
                "\uD83C\uDF88 短文/唐詩\n" +
                "\uD83C\uDF88 培養寫作能力\n" +
                "\uD83C\uDF88 客製化課程\n" +
                "\n" +
                "【Begonia老師的課程特色】\n" +
                "\n" +
                "\uD83C\uDF80 用輕鬆、互動的方式學中文\n" +
                "\uD83C\uDF80 引導孩子開口說中文\n" +
                "\uD83C\uDF80 課堂中提供多元練習、不枯燥\n" +
                "\uD83C\uDF80 讓孩子向老師發問，增加思考及挑戰\n" +
                "\uD83C\uDF80 初階可以全中文教學也可以英文雙語教學\n" +
                "\uD83C\uDF80 中高階課程使用全中文教學\n" +
                "\uD83C\uDF80 中高階課程安排課後作業，加強課堂記憶\n" +
                "\n" +
                "請私信老師小朋友的目標以及需求，我會幫忙打造出專屬的學習計畫～\uD83D\uDCEA\n" +
                "\n" +
                "【Begonia老師的25分鐘體驗課】\n" +
                "\n" +
                "✨ 了解孩子的中文水平\n" +
                "✨ 根據需求提供合適的學習方案\n" +
                "\n" +
                "【Begonia老師的50 分鐘課程】\n" +
                "\n" +
                "\uD83C\uDF4E 「學齡前幼兒口語班」\n" +
                "✨ 以繪本、圖卡，提升中文的學習動機及興趣\n" +
                "✨ 互動式引導，自然中對話\n" +
                "✨ 從單字引導加長口說內容\n" +
                "✨ 學齡前幼兒上課時間可調整為 25 mins, 2次/週\n" +
                "\n" +
                "\uD83C\uDF4A 「學齡兒童初階認讀班」\n" +
                "✨ 拼音/注音\n" +
                "✨ 繁體字/簡化字\n" +
                "✨ 一起閱讀繪本\n" +
                "✨ 口說練習\n" +
                " \n" +
                "\uD83C\uDF49 「小學初階中文課程」\n" +
                "✨ 以繪本及簡單閱讀為主\n" +
                "✨ 輔以文法練習\n" +
                "✨ 口語表達練習\n" +
                "\n" +
                "\uD83C\uDF47 「小學中階中文課程」\n" +
                "✨ 趣味故事、短文增加閱讀理解能力\n" +
                "✨ 看圖說故事\n" +
                "✨ 寫作練習\n" +
                "\n" +
                "\uD83E\uDD5D 「小學高階中文課程」\n" +
                "✨ 著重閱讀理解技巧及文法訓練\n" +
                "✨ 寫作練習",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 100.0,
            subjectList = listOf(Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6))),
            unitList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = 3,
        account = "kIWI",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/063/801/normal/dliopk.png?1587162839",
        name = "KIWI",
        email = "kIWI@gmail.com",
        intro = " \uD83D\uDD35團課滿班滿班滿班~~~熱門團課→\uD83D\uDFE0幼兒4yr ↑ 識字團課+小一翰林+知識中文團課 \uD83D\uDFE1一直開一直開 一直開\uD83D\uDFE2~快來尋問團班時段哦！\uD83D\uDFE1\n" +
                "\uD83D\uDC8E(為了讓學生能夠達成學習目標，Kiwi花很多的時間備課，所以課表上沒開放太多的時間，若要約課，請傳訊息，Kiwi會幫妳約哦！)\uD83D\uDC8E\n" +
                "♥️♥️我是Kiwi老師，來自台灣，目前定居加拿大，來看看我的課有什麼特別的♥️♥️\n" +
                "\n" +
                "\uD83D\uDC8E老師有10年的豐富經驗，多方面多元化的題材，讓你永遠有討論不完的話題\n" +
                "\uD83D\uDC8E老師總是全力促進學生的思考、口說\n" +
                "\uD83D\uDC8E超有趣的小遊戲，提高孩子的上課樂趣\n" +
                "-------------------------------------------------\n" +
                "\uD83D\uDC8E老師曾經教過的學生包含\n" +
                "    \uD83D\uDC49\uD83D\uDC49幼兒，小學生專屬課程  : \n" +
                "           \uD83D\uDC4D識字專門課，帶你認識不同領域的詞彙\n" +
                "           \uD83D\uDC4D兒童週刊閱讀課，讓你提升閱讀及識字識詞能力 \n" +
                "           \uD83D\uDC4D句型引導課，讓你輕鬆加長句子，輕鬆想到要說什麼！ \n" +
                "           \uD83D\uDC4D依需求提供練習作業，給你更多的練習\n" +
                "    \uD83D\uDC49\uD83D\uDC49非母語者的商務人士\n" +
                "    \uD83D\uDC49\uD83D\uDC49外交官、面試者、入學考試者\n" +
                "-----------------------------------------------\n" +
                "  \uD83D\uDC49\uD83D\uDC49來看看學生怎麼評價Kiwi的課    \uD83D\uDC49\uD83D\uDC49\n" +
                "\n" +
                "\uD83C\uDFB5 Kiwi 的教學非常生動有趣，也很有耐心\n" +
                "\uD83C\uDFB5老師給的作業、口語練習，都與孩子日常生活有關聯，幾個月下來，\n" +
                "     孩子們中文進步的非常多\n" +
                "\uD83C\uDFB5謝謝KIWI幫助住在海外的我們 \n" +
                "\uD83C\uDFB5老師很有耐心教導，讓女兒很有自信的在學校課堂做present ，謝謝老師的幫忙 \n" +
                "\uD83C\uDFB5課前詢問學習狀況, 可以使用英文講解, 給學生很多口說的機會\n" +
                "\uD83C\uDFB5Very nice and talkative teacher. I haven't spoken Chinese in a very long time,\n" +
                "       and I felt comfortable doing so. I love that we went straight into learning some basic greetings with some pronunciation help. I look forward to homework so I \n" +
                "       can practice in between classes!\n" +
                "------------------------------------------------------------------------\n" +
                "\n" +
                "在Kiwi這兒可以學到什麼？\n" +
                "\uD83D\uDE0E認讀識字(不同領域的詞彙)\n" +
                "\uD83D\uDE0E提供小學兒童週刊(繁/簡體)-提升閱讀及識字識詞能力\n" +
                "\uD83D\uDE0E引導你如何使用句型-加長句子(不會想不到要說什麼！)\n" +
                "\uD83D\uDE0E多方面多元化的題材-永遠有討論不完的話題\n" +
                "\uD83D\uDE0E以PDF, ODP 及小遊戲(客製化)來上課，提高上課樂趣。\n" +
                "\uD83D\uDE0E用盡全力\"促進\"學生的\"思考\"&\"口說\"能力\n" +
                "\uD83D\uDE0E提供錄音檔/圖檔/上課內容-練習過的句型\n" +
                "\uD83D\uDC4D依需求提供練習作業-若課堂上練習的不夠，可以再提供更多練習的給學生哦！\n" +
                "\uD83D\uDE4C每個人都有自己的學習方式，我想幫助你能在愉快的環境下把中文學好，我很樂意和你一起討論適合你的學習方法，讓你能趕快把中文學好！期待與您相見哦\uD83D\uDE4C\n" +
                "\n" +
                "我的教學理念\n" +
                "\uD83D\uDC78穩定基礎，不求速成\n" +
                "\uD83D\uDC78越是不熟，就越要弄懂它\n" +
                "\uD83D\uDC78試各種方法 ，就是要你學會它\n" +
                "\uD83D\uDC78滿滿的耐心，不怕你不會\n" +
                "\uD83D\uDC78超級親切，讓你什麼都想跟我聊\n" +
                "\uD83D\uDC78能學得會，就是好教材!(不管什麼教材)\n" +
                "\uD83D\uDC78課後願意輔導\n" +
                "\uD83E\uDD73Kiwi 我自己育有2個混血兒，因此面對自己的孩子，在教學上也都遇過所有爸爸媽媽都會遇到的問題。\n" +
                "　-----------------------------------------------\n" +
                "你有這些困擾嗎 ? \n" +
                "\uD83C\uDFB5學校沒什麼課業壓力，如何讓他情願的完成指定的中文作業?\n" +
                "\uD83C\uDFB5要用什麼方式，培養孩子閱讀中文的習慣?\n" +
                "\uD83C\uDFB5選取什麼教材好呢？繁體還是簡體呢?\n" +
                "\uD83C\uDFB5怎麼讀，讀多少呢? \n" +
                "\uD83C\uDFB5外國孩子能聽、說，還要讀、寫！這有可能嗎？\n" +
                "\uD83D\uDC8E這些需求，Kiwi的課程都可以幫你做到 ! \n" +
                "➤Kiwi自己的孩子在學中文\n" +
                "➤有相當多的教學經驗及\"出頭\"可以和爸爸媽媽一起分享 \n" +
                "➤ 老師在台灣時，就是教兒童美語的老師\n" +
                "➤一起幫助孩子，能夠開心愉快學習， 長大後才會感謝我們 !",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 4.99,
            replyRate = 100.0,
            subjectList = listOf(Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6))),
            unitList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = 4,
        account = "mrGuo",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/569/220/normal/ajaavn.png?1616236503",
        name = "郭老師",
        email = "mrGuo@gmail.com",
        intro = "\uD83C\uDF88持有國家普通話證書\n" +
                "\uD83C\uDF88擅長教兒童普通話口說\n" +
                "\uD83C\uDF88持有國家教師資格證書並獲得優秀教師稱號\n" +
                "\uD83C\uDF88了解兒童教育學和心理學\n" +
                "\uD83C\uDF88四年全職教學經驗和兩年線上教學經驗\n" +
                "\uD83C\uDF88在玩中學,通過孩子感興趣的方式使他們愛上學普通話\n" +
                "\uD83C\uDF88課堂活潑機具親和力,趣味性十足\n" +
                "\uD83C\uDF88獨特遊戲教學法讓小朋友愛上我的課堂\n" +
                "\n" +
                "❤️歡迎家長們有任何問題提前和老師商量,以便改進課堂中的不足之處\n" +
                "\n" +
                "\n" +
                "\n" +
                "\uD83C\uDFAA郭老師的遊戲課堂\uD83C\uDFAA\n" +
                "\n" +
                "\uD83E\uDDF8宝宝幼兒普通話（3-6歲）\n" +
                "\uD83E\uDDF8掌握事物單詞，例如數字天氣衣服等，使得孩子可以再日常生活中交流\n" +
                "\uD83E\uDDF8基本對話，有趣的兒歌，培養普聽話的語感和節奏感\n" +
                "\uD83E\uDDF8通過幾節課的積累，孩子對中文的指令可以準確並且順蘇的反應\n" +
                "\uD83E\uDDF8上課時長25分鐘，高效風趣\n" +
                "\n" +
                "\n" +
                "\uD83D\uDE97兒童中文（7-12歲）\n" +
                "\uD83D\uDE97閱讀短文，繪本，寓言，小故事，看圖識畫，進行討論分析\n" +
                "\uD83D\uDE97探討內容並且增加詞匯量和閱讀理解能力\n" +
                "\uD83D\uDE97風趣拼音課堂，學習認讀聲母韻母，音調，進行模仿跟讀，普通話發音so easy\n" +
                "\uD83D\uDE97口語練習和書面表達\n" +
                "\uD83D\uDE97多次課後孩子可以獨立自主閱讀，並且無障礙溝通交流\n" +
                "\uD83D\uDE97上課時長25或50分鐘，根據孩子的情況來定\n" +
                "\n" +
                "\n" +
                "\uD83C\uDFAF香港成人專門課\n" +
                "\uD83C\uDFAF國家語委普通話等級考試\n" +
                "\uD83C\uDFAF普通話正音\n" +
                "\uD83C\uDFAF商務普通話",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 4.91,
            replyRate = 93.0,
            subjectList = listOf(Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6))),
            unitList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = 5,
        account = "jason",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/099/404/normal/iepcgn.png?1596331985",
        name = "Jason",
        email = "jason@gmail.com",
        intro = "✨曾於美國新常春藤名校任教\n" +
                "✨曾拿獎學金赴美協助華語教學\n" +
                "✨協助外籍學生申請通過台灣華語文獎學金 \n" +
                "✨二十年中文教學資歷 \n" +
                "✨曾於台灣華語文學會論文發表 \n" +
                "✨目前在台灣某國小以及某國立大學語言中心任教(平時一堂課美金25元, 這裡只收23美元, 買到賺到!)\n" +
                "\n" +
                "\uD83D\uDE03七月起星期二三五白天時間開放預約! \n" +
                "\uD83D\uDC4C想在三個月的時間內學會中文，並通過HSK或TOCFL初級的考試嗎? 來找我就對了！\n" +
                "\uD83D\uDC4F除了教中文外，Jason還會以吉他伴奏，教你唱中文歌\uD83C\uDFB6\n" +
                "\uD83D\uDE0D 帶你玩玩線上小遊戲\uD83D\uDC31\u200D\uD83D\uDE80讓你覺得學中文是件有趣的事！\n" +
                "\uD83C\uDF8A只要下個時段沒預約，自動延長20-30分鐘不收費，體驗課當正式50分鐘上好上滿。\n" +
                "這麼好康的事只有跟Jason學中文才有! \n" +
                "\n" +
                "幼兒及兒童中文(4歲半到12歲): Jason本身就是國小老師, 已經有二十多年的教學資歷\n" +
                "\uD83D\uDC4D課程內容: 漢語拼音及ㄅㄆㄇ注音符號/中文歌曲律動/香港普通話對話及中文文法/國小兒童教材銜接/繪本\n" +
                "✌上課成效: 一個月學會漢語拼音及ㄅㄆㄇ注音符號(每週兩堂課)/三個月後銜接台灣國小教材\n" +
                "\uD83D\uDC4F課程特色: 幼兒以TPR的方式教學, 讓學生在有趣的互動中, 自然學會中文\uD83D\uDC4C加上線上繪本, 互動遊戲以及對話的補充, 讓您的孩子期待上中文課!\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\n" +
                "\n" +
                "青少年(13歲至18歲)\n" +
                "\uD83D\uDC4D課程內容: 漢語拼音及ㄅㄆㄇ注音符號/中文歌曲教唱/香港普通話對話及中文文法/台灣國中教材銜接/電影\n" +
                "✌上課成效: 一個月學會漢語拼音及ㄅㄆㄇ注音符號(每週兩堂課)/三個月後銜接台灣國中教材, TOCFL入門考試\n" +
                "\uD83D\uDC4F課程特色: 青少年喜歡自己探索\uD83D\uDC31\u200D\uD83D\uDE80以及模仿偶像\uD83D\uDC31\u200D\uD83D\uDC53, 因此教學過程會以任務型教學法, 以及專案式學習方案(Project-based learning), 來帶領學生自主性的學習, 並為HSK以及TOCFL預做準備\uD83E\uDD33\n" +
                "\n" +
                "大學中文, 成人中文及中文會話(18歲以上):\n" +
                "\uD83D\uDC4D課程內容: 漢語拼音及ㄅㄆㄇ注音符號/中文歌曲教唱/香港普通話對話及中文文法/HSK及TOCFL測驗聽說讀寫練習/商業中文(繁體簡體都能提供教材)/免費中文能力診斷/太極拳及氣功體驗活動\uD83E\uDD33\n" +
                "✌上課成效: 一個月學會漢語拼音及ㄅㄆㄇ注音符號(每週兩堂課)/三個月後(每週一堂課, 共12堂課), 掌握HSK3及TOCFL入門考試高分技巧/日常生活會話流利/商業中文能掌握基本關鍵詞彙\n" +
                "\uD83D\uDC4F課程特色: 完全客製化, 以學生需求為導向, 目前學生中有學校老師, 準備考TOCFL的工程師, 本身是業務經理的工程師, 甚或運動選手. 因此Jason都會客製PowerPoint並於上完課後傳送到學生的信箱. 重點是教材費全免! \n" +
                "\n" +
                "為什麼要選Jason呢? 因為Jason是個有經驗的華語教師, 曾經在美國Emory大學任教, 目前也仍在台灣的國小以及某間大學的華語中心任教, 並且因為長達二十多年的經驗, 已蒐集以下的課程資料:\n" +
                "\uD83D\uDC4CHSK聽說讀寫練習 (HSK3 到 HSK6都有, 曾有指導學生通過HSK level 6的經驗)\n" +
                "\uD83D\uDC4C華語文能力測驗(TOCFL)聽說讀寫練習 (Band A, B到 C都有客製化PowerPoint供參考)\n" +
                "\uD83D\uDC4C商務漢語教材 (繁體簡體的教材都有)\n" +
                "\uD83D\uDC4CHSK及TOCFL考古題題庫分享 (線上模擬練習也沒問題)\n" +
                "\uD83D\uDC4C旅遊中文 (點菜, 買票搭地鐵沒煩惱)\n" +
                "\uD83D\uDC4C中文簡報準備或中文論文發表 (無私分享自己華語文論文發表之經驗)\n" +
                "\uD83D\uDC4C學齡前兒童普通話口說練習 (幼童唱唱跳跳歡樂學中文, 學齡前拼音帶入肢體動作練習, 自然學會拼音)\n" +
                "\uD83D\uDC4C國小學生普通話及中文課程練習 \n" +
                "\uD83D\uDC4C香港GAPSK測試及準備\n" +
                "\uD83D\uDC4C中文歌曲教唱\n" +
                "這些教材完全無償提供, 免去自己上網找資料之苦! 這麼好康的事情只有跟Jason學中文才有!\n" +
                "\n" +
                "✌1. 以學生需求為最重要原則-完全客製化的教材, 考試導向, 商業中文, 財經中文, 文化藝術等都能符合您的需求!\n" +
                "✌2. 免費分享題庫及講義-課堂上的講義或教材無償提供, 也會分享線上題庫.\n" +
                "✌3. 免費分享線上app及軟體-讓您離線也能學中文! 線上教太極及氣功, 讓您學中文又能體會中華文化!",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 89.0,
            subjectList = listOf(Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6))),
            unitList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = 6,
        account = "rosalie",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/361/494/normal/uzuuno.png?1603293537",
        name = "Rosalie",
        email = "rosalie@gmail.com",
        intro = "＊ 日本東京早稻田大學國際教養學部畢業，主修語言學\n" +
                "＊ 母語中文，熱愛學習外語，有五年旅居美國、日本、德國經驗\n" +
                "＊ 英、日、德、西 豐富四國外語學習經驗 \n" +
                "＊ TOEFL 107｜IELTS 7｜JLPT N1\n" +
                "＊ 我懂你學中文的困難！\n" +
                "\n" +
                "\uD83C\uDF3B  活潑又實用的客製化教材\n" +
                "\uD83C\uDF3B  每堂課皆有複習、正音及大量的口說機會\n" +
                "\uD83C\uDF3B  先聽、説，後讀、寫，全面掌握，一樣不漏\n" +
                "\uD83C\uDF3B  用對學習方式，您與一口流利中文之間的距離，將只是時間上的問題 \n" +
                "\n" +
                "\uD83C\uDF3A  入門/初階華語｜拼/注音、簡/繁體字教學、初級詞彙、會話練習、聲調/變調規則講解、短文閱讀、句型寫作、聽力練習\n" +
                "\uD83C\uDF3A  中/高階華語｜中高級詞彙、短文寫作、主題式討論會話練習、中長文閱讀、聽力練習\n" +
                "\uD83C\uDF3A  華語會話｜校園/旅遊/生活會話、聽力練習\n" +
                "\uD83C\uDF3A  HSK & TOCFL｜考試準備、寫作文章修改\n" +
                "\n" +
                "♥️ 請在上課前與我聯繫，讓我知道您的學習目標與需求，我會替您的量身打造合適的上課內容。如果您方便的話，請在上課前透過訊息回答以下問題：\n" +
                "\n" +
                "1. 您學習中文多久了？\n" +
                "2. 您學習中文的目的是什麼？\n" +
                "3. 您目前的中文水平和期望到達的目標是什麼？\n" +
                "4. 您學習中文最希望加強的部份是什麼？ \n" +
                "\n" +
                "我已經迫不及待地想在課堂中見到您，讓我們一起輕鬆快樂地學習中文吧！",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 88.0,
            subjectList = listOf(Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6))),
            unitList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = 7,
        account = "shen",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/567/933/normal/pgnyro.png?1619936582",
        name = "Shen",
        email = "shen@gmail.com",
        intro = "*语言: 中文，粤语，英语，日语，马来语\n" +
                "*5年日本东京留学经验\n" +
                "*毕业于日本前三私立大学\n" +
                "*2年内取得日语能力考试 JLPT N1 \n" +
                "*2年的富士电视台翻译兼职经验\n" +
                "*2年的中文，日语和英语教学兼职经验\n" +
                "*日本大学入学面试和奖学金申请指导经验\n" +
                "\n" +
                "[中文语言考试HSK] (含小测试和作业) \n" +
                "*通过HSK课本教学\n" +
                "*互动方式教学\n" +
                "\n" +
                "[基本日常会话] (含娱乐作业) \n" +
                "*通过日常会话课本学习\n" +
                "*通过看剧或电影片段练习\n" +
                "*课程中尽量中文交谈！\n" +
                "\n" +
                "[基础中文] \n" +
                "*拼音\n" +
                "*阅读\n" +
                "*听力，口语\n" +
                "\n" +
                "*日语，英语和中文语言考试班教学经验\n" +
                "*大学入学申请和奖学金申请指导经验\n" +
                "*日语，英语和中文日常会话志愿经验\n" +
                "*课程以互动方式进行！每个学生都有机会在班上练习口语能力！\n" +
                "*告诉我你的学习目标，课程内容根据你的需求而定制！",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 98.0,
            subjectList = listOf(Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6))),
            unitList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = 8,
        account = "yujun",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/251/483/normal/egloms.png?1621571602",
        name = "昱君",
        email = "yujun@gmail.com",
        intro = "\u200B\u200B\uD83D\uDD05華語文學碩士｜擅長國文科升學考試\n" +
                "\u200B\u200B\uD83D\uDD05漢拼、注音、簡體、繁體｜各階段國文課程\n" +
                "\uD83D\uDD05\u200B\u200B6年補教經驗，學生橫跨歐亞\n" +
                "\uD83D\uDD05\u200B\u200B注重口說，教你最實用的中文，上課笑聲滿滿\n" +
                "\uD83D\uDD05\u200B\u200B文學專業，讓你不僅寫出漂亮的字，也擁有好的文采\n" +
                "\u200B\u200B\uD83D\uDD05客製化課程，針對你的需求，設計專屬的課程\n" +
                "\n" +
                "\u200B\u200B✔️作文寫作專業教法，從200字短文進步成800字長文\n" +
                "\u200B\u200B✔️6年資深補教國小講師，讓國文奠定未來學習的基石\n" +
                "✔️\u200B\u200B協助多位學生克服會考，國文科目穩居各科第一\n" +
                "\u200B\u200B✔️國文教育啟蒙，成功讓學生重拾對於國文的興趣\n" +
                "\n" +
                "\uD83D\uDC4C25分鐘中文體驗課；讓我了解你的中文程度！\n" +
                "\uD83D\uDC4C\u200B\u200B讓昱君老師知道你的中文學得怎麼樣，才能幫你設計最合適的課程哦\n" +
                "\u200B\u200B\uD83D\uDC4C教材怎麼處理（免費教材，不需另購課本）\n" +
                "\n" +
                "\uD83D\uDC4D兒童教學：啟蒙教學，能夠引起小朋友的興趣，並提升學習意願\n" +
                "\uD83D\uDC4D成人教學：提供最『實用』的中文，讓你學了不會無用武之地\n" +
                "\uD83D\uDC4D各年齡層國文課：國文哪有這麼難～無論是國小或是國高中大考，幫你統整再活用\n" +
                "\u200B\u200B\n" +
                "\u200B\u200B1️⃣兒童、成人教學\n" +
                "\u200B\u200B\uD83D\uDCAF零基礎發音課（拼音、注音）\n" +
                "\u200B\u200B－用最扎實的課程打下好的基礎，融合文化、節慶、故事，讓你能夠在快樂中學習\n" +
                "\u200B\u200B－自製教材，發音口說啟蒙、拼音練習、漢字認讀\n" +
                "\u200B\u200B依據學生狀況規劃 5堂、10堂、20堂 基礎課\n" +
                "\u200B\u200B\n" +
                "\u200B\u200B\uD83D\uDCAF初級、中級課程（簡體、繁體）\n" +
                "\u200B\u200B－可全中文/英文授課，讓孩子沈浸於華語的環境之中，自在地說出一口好中文\n" +
                "－\u200B\u200B老師自製講義，透過系統試、引導試教學，一步一步提升中文能力\n" +
                "\u200B\u200B\n" +
                "\u200B\u200B\uD83D\uDCAF文化、進階課程\n" +
                "－\u200B\u200B透過語法、寫作加深你對華文的認識，詩詞、文章、文學作品，最深刻有趣的文化課程\n" +
                "－\u200B\u200B根據你的程度調整課程內容，課程結束後大幅提升口說、認讀能力\n" +
                "\u200B\u200B\n" +
                "2️⃣\u200B\u200B會話特訓班\n" +
                "\u200B\u200B－輕鬆日常對話\n" +
                "\u200B\u200B－文章朗讀、發音矯正\n" +
                "\u200B\u200B－新聞、時事討論\n" +
                "\u200B\u200B－旅遊必備實用口說\n" +
                "\u200B\u200B\n" +
                "3️⃣寫作特訓班\n" +
                "\u200B\u200B－多年專業寫作指導，讓你能夠用出最道地的文字\n" +
                "－\u200B\u200B不僅只能輸入，還會需要能夠輸出\n" +
                "－\u200B\u200B從只能寫出200字，到能寫出800字\n" +
                "\u200B\u200B－指導多位學生於台灣升學考試拿到最高分\n" +
                "\n" +
                "⭕️國小語文素養培養班\n" +
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
                "\u200B\u200B❤️教學風格：\n" +
                "\u200B1、了解學生需求，切中重點教學，才能真正將弱勢能力補足\n" +
                "2、上課氣氛生動，教材會延伸其他相關資訊，隨時補充與課程有關的學識\n" +
                "\n" +
                "❤️\u200B\u200B課程規則：\n" +
                "\u200B\u200B1、上課記得準時，老師最多會等你10分鐘哦\n" +
                "2、\u200B\u200B取消課程記得提前12小時，若無故未出席會視為課程完成哦",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 98.0,
            subjectList = listOf(Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6))),
            unitList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = 9,
        account = "languageTeacher",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/599/265/normal/notdfj.png?1618969308",
        name = "語語老師",
        email = "languageTeacher@gmail.com",
        intro = "作文指導學生北區第一名/曾任北部升學私校老師\n" +
                "閱讀寫作比賽和小論文指導特優\n" +
                "曾於北部男校實習\n" +
                "對於混合題和國寫掌握度高\n" +
                "\n" +
                "曾帶過高三全班國文都均標以上\n" +
                "文言文解讀上會以字來帶，整理形式簡單易懂\n" +
                "喜歡娛樂圈時事會適時用名人來舉例\n" +
                "\n" +
                "1.會先以講解整個計分規則和計分項目，以及歷屆各標考生落點人數\n" +
                "科學化的去做規劃\n" +
                "\n" +
                "2.教學經驗豐富，可以透過測驗或是學生已完成學校考試來偵查，你需要補足的單元，如諸子、十五古文、人名典故\n" +
                "\n" +
                "3.主題式歸類國寫範圍，豐富資料來強化你對議題掌握，順利迎戰國寫\n" +
                "\n" +
                "4.喜歡星座周圍的人都覺得說得很準!如果喜歡星座與國文可以來試試!\n" +
                "\n" +
                "\n" +
                "\n" +
                "專攻臺灣高中升大學的學測和指考\n" +
                "課後會和學生溝通，配合所需出補充作業\n" +
                "每日增進一點點，積沙成塔\n" +
                "\n" +
                "課程需要基礎中文能力\n" +
                "\n" +
                "教學理念為以終為始，輕鬆幽默，接地氣的國文課!\n" +
                "希望有問題可以在課堂反應，或是預約時讓我先知道你確切的需求\n" +
                "願你們都能享受高中國文課!",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 100.0,
            subjectList = listOf(Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6))),
            unitList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = 10,
        account = "elaineSister",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/296/765/normal/cxdnzo.png?1620804540",
        name = "Elaine姐姐",
        email = "elaineSister@gmail.com",
        intro = "\uD83D\uDE06大家好,我是小朋友最愛的Elaine姐姐\uD83D\uDE09\n" +
                "\n" +
                "\uD83C\uDF1F 普通話母語,發音標準清晰\n" +
                "\uD83C\uDF1F 幼兒普通話教學經驗豐富\n" +
                "\uD83C\uDF1F 精心準備定制教材,教學資源豐富\n" +
                "\uD83C\uDF1F 課堂趣味性強,多互動\n" +
                "\uD83C\uDF1F 鼓勵啟蒙孩子的學習興趣\n" +
                "\uD83C\uDF1F 結合有趣的一對一互動遊戲,快樂學習普通話\n" +
                "\n" +
                "\uD83D\uDC4D\uD83C\uDFFB 為了提高學習效率 Elaine姐姐非常歡迎家長們多多聯繫我~\n" +
                "討論小朋友的學習需求 及時溝通 制定獨特的學習計劃唷！\n" +
                "➡  WeChat: Hy171558688\n" +
                "➡  WhatsApp: 1(670)7858079\n" +
                "\uD83C\uDF88 期待在我的課堂上見到你呀！ \uD83C\uDF89\n" +
                "\n" +
                "✳️100%客製化課程✳️\n" +
                "\n" +
                "\uD83D\uDCCD 定制備考衝刺學習計劃\n" +
                "\uD83D\uDCCD GAPSK 寫作 閱讀理解 拼音 看圖說話 面試課\n" +
                "\uD83D\uDCCD 詳細了解家長需求 課前及時溝通 課堂充分練習\uD83D\uDC4D\n" +
                "\n" +
                "\uD83D\uDC76 兒童普通話 (25分鐘) \uD83D\uDCDA >请私讯老师定制課堂計劃唷！\n" +
                "\n" +
                "\uD83D\uDCCD 認識基礎日常的單詞，包括顏色，動物，數字等\n" +
                "\uD83D\uDCCD 流行的中文兒歌唱跳，營造歡樂有趣的課堂氛圍\n" +
                "\uD83D\uDCCD 引導孩子開口說普通話，運用繪本，卡片，遊戲教學\n" +
                "\uD83D\uDCCD 課堂時長25分鍾，培養孩子的專注力和學習效率\n" +
                "\n" +
                "\uD83D\uDC68 小學生普通話 (50分鐘) \uD83D\uDCDA >请私讯老师定制課堂計劃唷！\n" +
                "\n" +
                "\uD83D\uDCCD 零基礎系統化課程，定制學習計劃，加強聽說讀寫的能力\n" +
                "\uD83D\uDCCD 日常溝通對話練習，主題包括購物，旅遊，食物，校園生活\n" +
                "\uD83D\uDCCD 解決表達困難，糾正發音語法，課後作業練習\n" +
                "\n" +
                "\uD83D\uDC96成人學中文\n" +
                "\n" +
                "\uD83C\uDF41HSK 或是 聽說專項練習課程 (初學者看過來~)\n" +
                "\uD83C\uDF41耐心幫助你一步步克服發音的困難，與語法上的疑惑之處。\n" +
                "\uD83C\uDF41階段式學習，讓您體會到漢語學習進步的樂趣，不輕易放棄！\n" +
                "\uD83C\uDF41提供豐富的備考資源，專業的課題輔助，來幫助你實現學習目標~\n" +
                "\n" +
                "\uD83D\uDCCA商務中文練習\n" +
                "\n" +
                "\uD83D\uDC8E互動式模擬練習 準備面試 \n" +
                "\uD83D\uDC8E練習簡報 幫助您糾音 聽起來更像母語者\n" +
                "\uD83D\uDC8E輔導修改工作履歷或資料 提高面試成功率\n" +
                "\uD83D\uDC8E學習更多商業溝通用語和詞彙 在職場中表達無壓力\n" +
                "\n" +
                "\uD83C\uDF1F日常對話練習\uD83C\uDF1F\n" +
                "\n" +
                "\uD83C\uDF1F超實用哦!! 涵蓋初級日常生活的話題~\n" +
                "\uD83C\uDF1F問候，美食，購物，旅遊，文化，校園生活等等。\n" +
                "\uD83C\uDF1F舉一反三，拒絕死記硬背~ 只有用到的知識才會記得更深刻~\n" +
                "\n" +
                "\uD83C\uDFB5 特色音樂興趣課程 >请私讯老师了解唷！\n" +
                "\n" +
                "\uD83C\uDFA4 適用於任何年齡段，激發學習興趣\n" +
                "\uD83C\uDFA4 運用PPT來輔助學習歌詞，擴大詞匯量，練習拼音發音\n" +
                "\uD83C\uDFA4 寫過中文原創歌曲，發表多首翻唱，網絡音樂平台認證音樂人\n" +
                "\uD83C\uDFA4 如果你喜歡唱歌，會彈樂器，想學習一首中文歌，那麼這個課程最適合不過了~\n" +
                "\n" +
                "\uD83D\uDC97 上課小規則\n" +
                "\n" +
                "\uD83D\uDC95 正式課為 50 分鐘,可根據小朋友的年紀提供 25 分鐘的課堂\n" +
                "\uD83D\uDC95 在訂課之前，歡迎私訊老師來討論你的學習目標和課程期望，這樣老師能夠更好的知道你的需要，因為每個人都是獨一無二的唷~\n" +
                "\uD83D\uDC95 如會遲到請提前通知老師，超過15分鐘後還是未到， 就不能返還課時費了哦\n" +
                "\uD83D\uDC95 如需要改期或特殊情況，請提前私訊老師哦，謝謝理解~",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 100.0,
            subjectList = listOf(Subject(id = 1, text = "國語", educationIdList = listOf(1), gradleIdList = listOf(1, 2, 3, 4, 5, 6))),
            unitList = unitListTest.filter { it.subjectId == 1L }
        )
    ),
    UserInfo(
        id = 11,
        account = "fawn",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/479/379/normal/joicff.png?1619497946",
        name = "小鹿",
        email = "fawn@gmail.com",
        intro = "▼   為什麼選擇小鹿老師呢   ▼\n" +
                "\uD83D\uDC8E   超過四年厚實的教學經驗\n" +
                "\uD83D\uDC8E   能流利地運用英語或中文講課\n" +
                "\uD83D\uDC8E   擁有面對國小  /   國中  /   高中學生的豐富教學資歷\n" +
                "\uD83D\uDC8E   在網路社群媒體上經營自己的生活數學頻道\n" +
                "\uD83D\uDC8E   若你沒跟我學數學，可能一輩子都認為數學只能拿來考試\n" +
                "\uD83D\uDC8E   別人都是教你怎麼考好試，而我是教你怎麼應用在你的生活中\n" +
                "\n" +
                "\n" +
                "\uD83D\uDCDA   課程內容：從國小數學 / 國中數學 / 高中職數學來發掘生活中的數學秘密。我的教學有別於傳統學習，強調啟發與應用\n" +
                "\n" +
                "\uD83D\uDCDA   課程特色：數學就像運球 / 傳球 / 上籃，也就是解方程式 / 求角度 / 算最大值，但是學校數學從來沒告訴我們這些能用在哪裡\n" +
                "\n" +
                "\uD83D\uDCDA   上課成效：針對需求客製學習進程，每堂課學習一個數學概念，鞏固基本概念，最後在帶入生活中。\n" +
                "\n" +
                "\n" +
                "\n" +
                "\uD83D\uDCDD    經典課程\n" +
                "\uD83D\uDCCC    高中職篇\n" +
                "\uD83D\uDC49\uD83C\uDFFB    矩陣：矩陣的神秘，籃球場上的勝負由你控制\n" +
                "\uD83D\uDC49\uD83C\uDFFB    機率：擲筊的藝術，2 / 3 的機會神明會 SAY YES\n" +
                "\uD83D\uDC49\uD83C\uDFFB    極限：到底要拍幾張呢？連知名的專業攝影師都不會的技巧\n" +
                "\uD83D\uDC49\uD83C\uDFFB    數列：愛情中的等差，我早就知道你什麼時候會跟另一半吵架\n" +
                "\n" +
                "\uD83D\uDCCC    國中篇\n" +
                "\uD83D\uDC49\uD83C\uDFFB    二次方根：去試穿衣服的時候，別相信你自己的眼睛\n" +
                "\uD83D\uDC49\uD83C\uDFFB    乘法公式：巴斯卡三角形的厲害，讓你跟賭神一樣逢賭必贏\n" +
                "\uD83D\uDC49\uD83C\uDFFB    線性函數：若你今天打工想偷懶，那是要怎麼正大光明地偷懶呢\n" +
                "\uD83D\uDC49\uD83C\uDFFB    畢氏定理：在世界的中心說愛你，說著我想跟你走好幾輩子的真心諾言\n" +
                "\n" +
                "\uD83D\uDCCC    國小篇\n" +
                "\uD83D\uDC49\uD83C\uDFFB    加法跟乘法：教你如何算出別人的信用卡秘密\n" +
                "\uD83D\uDC49\uD83C\uDFFB    面積與體積：吃比薩時，要怎麼拿上面的料才不會掉下來\n" +
                "\uD83D\uDC49\uD83C\uDFFB    分數的加減：你在跟別人比剪刀石頭布時，到底要出哪個拳才會贏\n" +
                "\uD83D\uDC49\uD83C\uDFFB    小數的乘法：去買東西的時候，滿千送百，買一送一等東西，真的比較划算嗎\n" +
                "\n" +
                "\n" +
                "▼  我會為你準備好   ▼\n" +
                "\uD83D\uDCDA   老師親手用心設計的教材\n" +
                "\uD83D\uDCDA   老師用心設計的回家作業\n" +
                "\uD83D\uDCDA   每次都有一題生活數學題讓你思考\n" +
                "\uD83D\uDCDA   隨時解答你的問題，不管是什麼樣的數學問題\n" +
                "\uD83D\uDCDA   用心規劃專屬於你的課程，積極培養你的數學思考能力\n" +
                "\n" +
                "\n" +
                "▼   我的課程值得你期待的理由   ▼\n" +
                "\uD83D\uDC69\uD83C\uDFFB\u200D\uD83C\uDFEB   學習數學不再是一件枯燥乏味的事\n" +
                "\uD83D\uDC69\uD83C\uDFFB\u200D\uD83C\uDFEB   每堂課我都會用心準備給你自己製作的教材跟作業\n" +
                "\uD83D\uDC69\uD83C\uDFFB\u200D\uD83C\uDFEB   先讓你對數學產生興趣之後，你自然而然就不會排斥數學，而是會主動去學習\n" +
                "\uD83D\uDC69\uD83C\uDFFB\u200D\uD83C\uDFEB   每堂課程除了學習數學之外，更會用自身在世界旅行跟生活的經驗來結合數學，告訴你我在世界的哪個地方，用數學解決了我遇到的問題\n" +
                "\n" +
                "\n" +
                "✨   我的課程適合誰 ?\n" +
                "\uD83D\uDCCC   聽懂學校老師的講課但實際作題卻不知道如何下手\n" +
                "\uD83D\uDC49\uD83C\uDFFB   想要學好數學但課堂上不敢舉手問老師數學問題者\n" +
                "\uD83D\uDCCC   想要知道數學能怎麼應用在我們的生活中\n" +
                "\uD83D\uDC49\uD83C\uDFFB   不想讓自己學數學只是為了應付考試者\u200B\u200B\u200B\u200B\u200B\n" +
                "\n" +
                "\n" +
                "\u200B\u200B\u200B\uD83E\uDDD1\uD83C\uDFFB\u200D\uD83E\uDDB0\u200B  不管你是幾年級\n" +
                "\u200B\u200B\u200B\uD83C\uDF1F\u200B   我建議上課時數最少一星期兩堂課\n" +
                "\u200B\u200B\u200B\uD83C\uDF1F\u200B   上課中我會教你如何解題並融會貫通\n" +
                "\u200B\u200B\u200B\uD83C\uDF1F\u200B   若你不跟我學數學，你可能一輩子都只知道數學只能拿來考試了\u200B\u200B\n" +
                "\u200B\u200B\u200B\uD83C\uDF1F\u200B   每章節學完後，都會從那章節出發，教你怎麼把它應用在 『 你 』 的生活中\u200B\u200B\u200B\n" +
                "\uD83C\uDF1F\u200B   從中打好你的數學基礎， 除了為升學準備外，也培養學習著用數學去思考生活周遭\n" +
                "\n" +
                "\n" +
                "\n" +
                "\uD83D\uDCDD   一位來自鄉下的的大男孩，青創實驗室的創辦人，致力於推廣數學跟生活的結合課程\n" +
                "\n" +
                "\uD83D\uDD39   若學生已有既定教材或進程，教師亦可配合使用其教材與進度\n" +
                "\n" +
                "\uD83D\uDD39   數學不僅是抽象運算，還跟現實生活緊密結合\n" +
                "\n" +
                "\uD83D\uDE4C   根據每個學生的情況來客製化你的課程講義\n" +
                "\n" +
                "✍\uD83C\uDFFB   課後若有不清楚的概念，歡迎加 LINE：maystar0903\n" +
                "\n" +
                "\n" +
                "\n" +
                "\uD83D\uDC49 【  來看看學生和家長怎麼說吧  】\n" +
                "☗  老師教學的方式很新穎，很有啓發性，能讓孩子多思考，讓孩子學的數學很好玩。\n" +
                "\n" +
                "☗  老師上課充滿熱忱，孩子本身有點害羞，老師能夠鼓勵他讓場面熱絡。 也可以用英文協助講解，感謝！ 自製教材有趣！\n" +
                "\n" +
                "☗  小孩子第一次會吵著要上課⋯果然超乎我對數學的想像。老師很棒、期待以後上完課孩子都可以主動寫數學並期待下一堂課！\n" +
                "\n" +
                "☗  超级赞 \uD83D\uDC4D   每次Alan下课都要说很多关于数学课上的问题来考我们  \uD83D\uDE0A   他很开心，也学到很多知识，谢谢  \uD83D\uDE4F\n" +
                "\n" +
                "☗  老師，超棒ㄉ啦，第一次遇到一個老師能把數學說的跟數學不一樣  \uD83D\uDE0D\n" +
                "\n" +
                "☗  小鹿老师活泼有趣，热情洋溢，孩子每天都在期待上课\n" +
                "\n" +
                "\uD83D\uDC49   上完課程後，你會用數學的思維去思考的不只考試，更是周遭的每一件事\n" +
                "\n" +
                "\uD83D\uDC49   學生們會開始用數學跟我討論生活中的事情，例如：如何用斜率來減肥  /  如何搭訕女生  /  紫南宮真的有神秘力量嗎等等的問題，生活到處都是數學\n" +
                "\n" +
                "\n" +
                "\n" +
                "⭐   如果有任何問題，歡迎跟我聯絡 \n" +
                "\n" +
                "⭐   我認為學校數學是無趣的基本動作練習，但你要若要上場比賽，你就必須要怎麼把這些基本動作應用在場上，你應該要學會解決問題，而不是一直在考卷拚誰考得高\n" +
                "\n" +
                "\n" +
                "『  人們總覺得數學很難，是因為他們不理解生活有多複雜  』",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 94.0,
            subjectList = listOf(Subject(id = 2, text = "數學", educationIdList = listOf(1, 2, 3), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))),
            unitList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = 12,
        account = "tony",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/609/572/normal/nlhdtn.png?1620446864",
        name = "Tony",
        email = "tony@gmail.com",
        intro = "\uD83D\uDD25  【感謝為疫情付出的所有夥伴們】\uD83D\uDD25\n" +
                "自 5/17 日開始，警消、醫護人員\n" +
                "兩人同行，一人免費！！！\n" +
                "警消醫護人員以外的各位同學，買十堂課以上⬆️\n" +
                "給予15% off 的折扣，如果想知道更多優惠，歡迎私訊我！\n" +
                "\uD83D\uDD25【讓科學與理性打敗恐懼與病毒，同島一命，共同防疫，加油】\uD83D\uDD25    \n" +
                "\n" +
                "\n" +
                "跟著Tony學數學是你最好的選擇!! Let's go～～\n" +
                "\uD83C\uDFC6平台Top數學家教\n" +
                "\uD83D\uDCD6清大數學系畢業\n" +
                "\uD83D\uDCD6就讀台大研究所\n" +
                "\uD83D\uDCD6七年資深家教及補教經驗\n" +
                "\uD83D\uDCD6已幫助多名學生進入明星中學及大學\n" +
                "\uD83D\uDCD6完全客製化課程，用學生感興趣的話題引導出背後的數學邏輯\n" +
                "\n" +
                "---------------------------------------------------\n" +
                "\n" +
                "\uD83D\uDCDA25分鐘數學診療門診\uD83D\uDCDA\n" +
                "讓Tony更了解你的弱點以及程度\n" +
                "客製出最適合你的課程安排\uD83D\uDC4D\n" +
                "- 免費教材，不需購買課本\n" +
                "- 找到學生弱點，擊敗弱點化為優勢！\n" +
                "- 規劃學習目標以及計劃，有效提升數學能力\n" +
                "\n" +
                "\uD83D\uDCDA國中會考數學課程\uD83D\uDCDA\n" +
                "小孩剛上國中不適應教材？\n" +
                "小孩第一次與來自各地的學生競爭，感到無所適從？\n" +
                "-７年國中會考經驗，了解各成績段的學生會遇到的問題，請放心把小孩的數學交給我！\n" +
                "-擁有多年課輔與心理諮商經驗，幫助小孩心態健全的成長\n" +
                "-把握小孩腦袋可塑性最強的時期，Tony與您一起努力！\n" +
                "＊由於我本身的教學方式與教育部所推廣的螺旋式教法相近，所以我可以告訴你這套教學方式的優點與缺點，讓別的學生還在霧煞煞時，你已經掌握國中數學的節奏與步調＊\n" +
                "\n" +
                "\uD83D\uDCDA高中學測數學課程\uD83D\uDCDA\n" +
                "高中數學難度直線上升，讓小孩焦頭爛額？\n" +
                "換了多間補習班一直苦無成效，家長也束手無策？\n" +
                "-7年高中學測經驗，知道學生準備超大範圍考試時會遇到的各種問題\n" +
                "-了解年輕人的語言及文化，讓學生學習數學猶如在交一位知心朋友\n" +
                "-從名校畢業的我能帶給您家小孩不一樣的價值觀與人生觀，讓他們 think big and aim high !\n" +
                "\n" +
                "\uD83D\uDCDA國小專屬數學課程\uD83D\uDCDA\n" +
                "您的孩子覺得上數學課很無聊？\n" +
                "孩子沒辦法適應高年級的教材？\n" +
                "不用擔心！Tony豐富的數學教學經驗 讓孩子兼顧學業成績與對科學的興趣，不再害怕數學！\n" +
                "-生動的例子與活潑的教學，數學課不再枯燥！\n" +
                "-擁有幼教經驗與7年數學專業家教經驗，讓您小孩學習品質有保障\n" +
                "-在小學時期投資小孩對數學的好奇與興趣，勝過浪費錢在補救高中的數學！\n" +
                "\n" +
                "【其他數學考試如GMAT、SAT 可以詳談】\n" +
                "\n" +
                "---------------------------------------------------\n" +
                "\n" +
                "教學風格：\n" +
                "啟蒙式教學為我個人的教學風格，7 年的教學現場經驗讓我深信，讓小孩數學進步的關鍵不是靠1週2小時的密集訓練，而是靠一週兩小時的時間，讓學生把對週遭事物的好奇心找回來，我再把其中的數學原理講給他聽，藉由這個方式學習，才能讓他學得又快又穩，觸類旁通。\n" +
                "\n" +
                " 【課程規則】\n" +
                "1. 上課請準時，最多只等10分鐘唷！\n" +
                "2. 取消課程請於12小時前取消，無事先告知取消原因並無出席，課程視為完成。\n" +
                "3. 有複習、常練習才能進步，Tony的唯一要求就是自律複習\n" +
                "4. 如果可以，在課堂前準備問題在課堂中問我，釐清觀念，是我認為最有效率的數學學習方法！",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 96.0,
            subjectList = listOf(Subject(id = 2, text = "數學", educationIdList = listOf(1, 2, 3), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))),
            unitList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = 13,
        account = "huangYao",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/455/173/normal/zotfqi.png?1622626688",
        name = "黃曜",
        email = "huangYao@gmail.com",
        intro = "會說中文\n" +
                "\n" +
                "✨成大碩士，資深補教老師，經驗１３年數學教學專家 \uD83D\uDC69\u200D\uD83C\uDFEB詳見下方全部介紹\n" +
                "✨公立國中科任、補救教學合格專任老師\n" +
                "✨大學數學系榜首\n" +
                "✨體驗課提供課前基本能力測驗、課後弱點分析。購正式課免費提供考古、練習題及詳解\n" +
                "\n" +
                "✨不外出群聚，在家好學習\n" +
                "✨體驗課3美元／一對二8折，抗疫優惠中\n" +
                "\n" +
                "\n" +
                "課程特色\n" +
                "☀️引用生活實例講解公式 ▶ 輕鬆建觀念\n" +
                "☀️讀懂落落長的素養題型 ▶ 解題超ＥＺ\n" +
                "☀️活用計算技巧化簡過程 ▶ 計算超實用\n" +
                "☀️互動教學和圖解式筆記 ▶ 複習強效率\n" +
                "\n" +
                "課程內容\n" +
                "\uD83D\uDD0E大學學測：強化計算能力，深入觀念、公式、題型三者的融會結合\n" +
                "\uD83D\uDD0E國中會考：長文題閱讀技巧，代數、圖形、統計機率間連結和統整\n" +
                "\uD83D\uDD0E國小數學：基礎數學概念和基本功培養\n" +
                "\uD83D\uDD0E競賽數學：研讀和探究有深度的問題，養成持久專注和思考品質\n" +
                "\n" +
                "授課方式\n" +
                "\uD83D\uDD38依個人學習進度排定教學/複習\n" +
                "\uD83D\uDD38互動式教學，討論/講解並進\n" +
                "\uD83D\uDD38電子白板，圖例筆記超清楚\n" +
                "\uD83D\uDD38提供上課筆記、課後習題\n" +
                "\n" +
                "\uD83C\uDF08教材依個人情況，使用自編講義或自行購買指定書籍\n" +
                "\uD83C\uDF08課後複習觀念不清楚、習題練習卡卡時，可透過線上討論\n" +
                "\uD83C\uDF08若有作業、考卷試題需討論，課前兩天提出，以提供最完善的教學\n" +
                "\uD83C\uDF08依個人需求，排定課前測驗、課後作業\n" +
                "\n" +
                "\n" +
                "理解 ▶ 熟練 ▶ 融會貫通\n" +
                "\uD83D\uDCCF數感能力、升學成績，需按部就班學習，由淺入深逐步積累\n" +
                "\uD83D\uDCAD課堂中能聽懂理解的內容，課後也要自我複習再思考一遍，練化成自身的硬實力\n" +
                "\uD83D\uDCDD複習時，眼看、腦想加手寫，三者合一事半功倍\n" +
                "\n" +
                "\n" +
                "\uD83C\uDFC6️近期指導學生之成果\n" +
                "Ｃ同學 指導六年：考上再興中學語資班／學測數學頂標／考上政大英文\n" +
                "Ｊ同學 指導四年：國中會考數學A++\n" +
                "Ｌ同學 指導三年：AMC8競賽成績前1%／再興中學校排第一、數學班排第一\n" +
                "Ｌ同學 指導三年：國中數學班排第一\n" +
                "Ｃ同學 指導一年：國中段考由60分進步至90分\n" +
                "Ｃ同學 指導一年：高中數學班排由倒數第五進步至前三\n" +
                "Ｊ同學 指導半年：小六數學段考由50分進步至85分\n" +
                "Ｗ同學 指導三個月：會考數學由C進步至B++\n" +
                "Ｌ同學 指導一個月：再興中學數學成績進步20分",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 4.98,
            replyRate = 100.0,
            subjectList = listOf(Subject(id = 2, text = "數學", educationIdList = listOf(1, 2, 3), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))),
            unitList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = 14,
        account = "yuRiceBall",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/451/824/normal/xnqqha.png?1622043540",
        name = "鈺飯糰",
        email = "yuRiceBall@gmail.com",
        intro = "\uD83D\uDCA1 關於我：\n" +
                "\uD83D\uDC4D 中央大學數學系畢業，6年以上教學經驗的數學老師\n" +
                "\uD83D\uDC4D 擁有合格的中等學校教師證\n" +
                "\uD83D\uDC4D 教師心算七段、珠算二級\n" +
                "\uD83D\uDC4D 高中數學競賽全校第一\n" +
                "\uD83D\uDC4D 高中第二類組全校第2畢業\n" +
                "\uD83D\uDC4D 6年以上的國高中家教經驗\n" +
                "\uD83D\uDC4D 上課風格活潑隨時可發問，讓學習數學的過程是快樂的\n" +
                "\uD83D\uDC4D 講解觀念，並搭配靈活的練習題，讓你真正的學會並應用\n" +
                "\n" +
                "\uD83D\uDCA1 教師專長：\n" +
                "\uD83D\uDCD6 國中各校段考滿分班\n" +
                "\uD83D\uDCD6 國中會考複習班 \n" +
                "(書單 : 麻辣會考講義)\n" +
                "\uD83D\uDCD6 高中各校段考滿分班 \n" +
                "(書單 : 對話式講義)\n" +
                "\uD83D\uDCD6 高中學測複習班 \n" +
                "(書單 : 複習週記、123日的淬鍊)\n" +
                "\uD83D\uDCD6 高中指考數甲、數乙班\n" +
                "(書單 : 指考複習關鍵60天)\n" +
                "\uD83D\uDCD6 高職段考滿分班\n" +
                "\uD83D\uDCD6 高職統測複習班 \n" +
                "(書單 : 高頻率題型複習講義、ABC決戰60回)\n" +
                "\n" +
                "\uD83D\uDCA1 課程特色：\n" +
                "\uD83D\uDCC8 根據學生能力及學習目標客製化教材，學習更有成效\n" +
                "\uD83D\uDCC8 快狠準題型分析，一堂課掌握考試重點\n" +
                "\uD83D\uDCC8 解題技巧及邏輯練習，熟悉答題模式，避開誤謬陷阱\n" +
                "\uD83D\uDCC8 多鼓勵學生建立學生學習的自信心\n" +
                "\n" +
                "\uD83D\uDCA1 上課教材：\n" +
                "\uD83D\uDCC8 使用簡報量身訂做你的專屬課程並配合實體書。\n" +
                "\uD83D\uDCC8 依照個人需求安排適量作業。\n" +
                "\uD83D\uDCC8 課堂後免費提供雲端教材回家複習。\n" +
                "\uD83D\uDCC8 學生可自備繪圖板或平板搭配上課。\n" +
                "\n" +
                "\uD83D\uDCA1 凡購買10堂課以上的學生即可獲得獎學金資格(每一次段考結算一次)\n" +
                "\uD83D\uDCC8 國高中職數學段考班排第一(獎學金1000元)\n" +
                "\uD83D\uDCC8 國中數學會考A++(獎學金1000元)\n" +
                "\uD83D\uDCC8 學測15級分(獎學金1000元)\n" +
                "\uD83D\uDCC8 指考90分以上(獎學金1000元)\n" +
                "\uD83D\uDCC8 統測數學滿分(獎學金1000元)\n" +
                "(例如: 第一次段考結束前購買飯糰10堂正式課並學習完，並且數學單科全班第一，即可領取1000元；第二次段考結束前再購買飯糰老師10堂正式課，即可有再次領取獎學金的資格)\n" +
                "\n" +
                "\uD83D\uDCA1 分享一下學好數學的方法：\n" +
                "\uD83D\uDCC8 把觀念弄清楚\n" +
                "上課會講解觀念，透過靈活的題目讓觀念越釐越清楚，重點是讓你真的去體會，體會這些觀念！\n" +
                "而不是背誦死板的公式，與太過單一的練習題!\n" +
                "\n" +
                "\uD83D\uDCC8 弄清楚以後,要練習,自己獨立思考的能力\n" +
                "怎樣具備解題的能力呢？理論上,在我們上完課後,從簡單到難的，我們都學會了，\n" +
                "那接下來就是練習「獨立思考的能力」。想要學好數學,一定要拿出嘗試的精神,\n" +
                "題目再複雜,都要用我們講過的架構,去嘗試。\n" +
                "\n" +
                "我的課程注重邏輯，講求精準、效率、重點式與互動性，課程開始前你可以先想想:\n" +
                "\uD83C\uDF88 學習數學的目的是什麼?\n" +
                "\uD83C\uDF88 想要達到什麼目標?\n" +
                "\uD83C\uDF88 目前遇到的困難是什麼?\n" +
                "\uD83C\uDF88 希望花多久的時間達到這個目標?\n" +
                "\n" +
                "歡迎預約我的體驗課以進一步了解我能如何幫助你，也讓我能更精準掌握你的數學程度並客製化你的學習路程！ \n" +
                "\n" +
                "課程注意事項:\n" +
                "\uD83C\uDF88 目前以國高中、高職視訊課程為主，國小部分或實體面授暫時不提供唷 。\n" +
                "\uD83C\uDF88 若想上課的時間未顯示於授課時間表上，可以私訊老師討論進行額外安排。\n" +
                "\uD83C\uDF88 上課前需事先告知學習目標與需求，讓老師能更依照你的需求準備課程內容。\n" +
                "\uD83C\uDF88 授課學生程度不限，包含初學者至進階者。只要有心，沒人攔得住你。\n" +
                "\uD83C\uDF88 臨時請假最晚可於開課前1~2小時告知，並討論補課的時間。   \n" +
                "\uD83C\uDF88 課程有問題可加老師的LINE: reduichi 詢問。\n" +
                "\n" +
                "學習要持續，最快的捷徑就是穩紮穩打，一週至少安排一~二堂課，持續練習才能進步!   \n" +
                "想探索自己的無限潛力？私訊我，來上課吧！",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,

            replyRate = 95.0,
            subjectList = listOf(Subject(id = 2, text = "數學", educationIdList = listOf(1, 2, 3), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))),
            unitList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = 15,
        account = "wendy",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/063/813/normal/ctuqky.png?1622635992",
        name = "Wendy",
        email = "wendy@gmail.com",
        intro = "\uD83C\uDF93畢業於國立交通大學資訊科學與工程研究所\n" +
                "\uD83C\uDF93畢業於國立中興大學應用數學系\n" +
                "\uD83D\uDC69\u200D\uD83C\uDFEB六年的國高中數學與國中英文教學經驗\n" +
                "\uD83D\uDC69\u200D\uD83C\uDFEB曾任新竹市東區龍山國小課輔志工老師\n" +
                "\uD83D\uDC69\u200D\uD83D\uDCBB曾任新竹科學園區知名企業資深工程師\n" +
                "\uD83D\uDC69\u200D\uD83C\uDFEB即將擔任台灣拓人スクール個別指導委任老師\n" +
                "\n" +
                "在學習上遇到無法解決的問題嗎？想領先班上的同學當個佼佼者嗎？歡迎預約我的體驗課～讓我幫助你達成學習的目標！ \n" +
                "\n" +
                "〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️\n" +
                "           教 學 特 色\n" +
                "〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️\n" +
                "\uD83C\uDFAF多元化教學         \uD83D\uDC49學習並非死背書上重點，訓練思考能力\n" +
                "\uD83C\uDFAF客製化教學         \uD83D\uDC49解決各種與眾不同的疑難雜症   \n" +
                "\uD83C\uDFAF升學經驗分享     \uD83D\uDC49分享老師自身求學經歷\n" +
                "\uD83C\uDFAF讀書進度規劃     \uD83D\uDC49會考前複習重點、超前部署\n" +
                "\uD83C\uDFAF作業批改與檢討 \uD83D\uDC49在學校遇到的難題，別擔心！找我就對了！\n" +
                "\uD83C\uDFAF客製化教材        \uD83D\uDC49老師會提供教材與課後複習題，不用額外購買\n" +
                "\n" +
                "〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️\n" +
                "           課 程 介 紹\n" +
                "〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️\n" +
                "✏️小五、小六國中先修數學課程\n" +
                "✏️國一、國二基礎數學打底課程\n" +
                "✏️國三數學會考總複習課程\n" +
                "✏️國三數學會考衝刺課程\n" +
                "✏️國三數學歷屆會考試題解題課程\n" +
                "✏️高中數學基礎課程\n" +
                "✏️國小一至六年級數學課程\n" +
                "✏️課後輔導、指導完成學校作業\n" +
                "✏️客製化國中小及高中數學課程（可與老師討論學習目標）\n" +
                "\n" +
                "〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️\n" +
                "           課 堂 時 間\n" +
                "〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️\n" +
                "\uD83D\uDC3E 課程時間建議每週兩堂，且每堂課 60～90 分鐘 \uD83D\uDC3E\n" +
                "\uD83D\uDC3E 課表上沒有列出的時間，可以私訊老師另外增設\uD83D\uDC3E\n" +
                "\n" +
                "\uD83C\uDF1F六月底前購課學生享隨時問答服務\uD83C\uDF1F\n" +
                "學校不會的作業可隨時拍照上傳提問，老師盡可能會在當天或隔天回覆。\n" +
                "\n" +
                "〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️\n" +
                "           其 他 注 意 事 項\n" +
                "〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️\n" +
                "⚠️購買課程前，請先與老師確認學習目標與內容\n" +
                "⚠️請假最晚需在課堂預定開始時間前一天提出，若無故缺課且無正當理由，則不予補課\n" +
                "舉例：若上課時間為週三晚間六點，則最晚需於週二提出請假需求。",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 94.0,
            subjectList = listOf(Subject(id = 2, text = "數學", educationIdList = listOf(1, 2, 3), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))),
            unitList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = 16,
        account = "teacherEd",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/554/816/normal/dvpvff.png?1623083477",
        name = "艾德老師",
        email = "teacherEd@gmail.com",
        intro = "\uD83D\uDD25台大機械畢業、準成大碩士，理學專精的鎮瑋老師教你如何翻轉數學\uD83D\uDD25\n" +
                "\uD83C\uDFC5台大畢業\uD83C\uDF93機械專業｜成大數學研究所｜曾任知名公司工程師\n" +
                "\uD83C\uDFC5七年教學經驗｜學生數破百｜教學經驗豐富、授課品質保證\n" +
                "\uD83C\uDFC5專精多元化；小學、國中會考、指考、學測、高職統測\n" +
                "\uD83D\uDC51 數學專精\uD83D\uDC51\n" +
                "\uD83D\uDC51趕快私訊鎮瑋安排課程體驗\n" +
                "\n" +
                "           \uD83D\uDCDA開設班別\uD83D\uDCDA\n" +
                "\uD83D\uDD25升學考試班（指考、學測、會考）\n" +
                "\uD83D\uDD25國中數學進度班\n" +
                "\uD83D\uDD25高中職數學進度班\n" +
                "\uD83D\uDD25國小數學歡樂班\n" +
                "\uD83D\uDD25客製課程 （解題、指定單元加強、概念補正）\n" +
                "\n" +
                "**********\uD83D\uDCDA[升學考試訓練班]**********\n" +
                "\n" +
                "⭐衝學測、指考求好成績\n" +
                "⭐根據你自身的能力，設計適合你的課程\n" +
                "⭐從最短的時間幫助了解技巧，應付公開試\n" +
                "⭐分享考試經驗，常犯錯誤\n" +
                "\n" +
                "**********\uD83D\uDCDA[國、高中訓練班]**********\n" +
                "\n" +
                "⭐數學基礎十分重要！\n" +
                "⭐升高中後不管是難度或是學習量都是國中的好多倍，運用簡明的觀念一招打敗天下無敵手\n" +
                "⭐任何程度都可以有效率的趕上進度\n" +
                "\n" +
                "**********\uD83D\uDCDA[小學基礎訓練班]**********\n" +
                "\n" +
                "⭐為考試、升學感到壓力？\n" +
                "⭐有趣互動的課程絕對適合你\n" +
                "⭐用簡單例子去解釋課題，降低困難的感覺，應對更得心應手\n" +
                "\n" +
                "為保障所有同學們上課的權益，請看有關取消或更改上課時間事宜!\n" +
                "✨在上課12小時前更改或取消時間：可自行更改\n" +
                "✨在上課12小時內：不能更改時間並不會退回課堂和該堂費用\n" +
                "✨同學如要遲到，請在課堂前通知老師\n" +
                "✨在沒有事前通知的情況下遲到：老師會在課室內等10分鐘並向同學發送提示信息\n" +
                "✨若同學在課堂開始後 10分鐘內沒有出現，老師會視學生無故曠課，該已預課堂也不設退款或補課\n" +
                "關於課堂\n" +
                "✨ 課堂時間基本為50分鐘，也可以依學生學習需要提供25分鐘, 80分鐘和110分鐘的課程\n" +
                "✨ 如果有興趣可以與我聯繫，並購買/預約課程\n" +
                "✨ 老師會定期檢查學生進度\n" +
                "✨ 同學應該進行課後溫習，以達到理想學習目標\n" +
                "✨ 關於數學課程或有什麼疑問，歡迎私訊我，謝謝 \uD83D\uDE0A",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 100.0,
            subjectList = listOf(Subject(id = 2, text = "數學", educationIdList = listOf(1, 2, 3), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))),
            unitList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = 17,
        account = "teacherP",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/634/982/normal/zyynym.png?1620654754",
        name = "P老師",
        email = "teacherP@gmail.com",
        intro = "【跟著P老師學數學是你最好的選擇】\n" +
                "✏️臺灣師範大學化學所，家教課輔經驗超過3年\n" +
                "✏️目前為有機化學助教、國中數學科學習扶助老師\n" +
                "✏️用最口語化的方式來講解數學，讓學習數學的過程不再恐懼，而是知識上的成就感\n" +
                "✏️找出你的學習盲點，提供適合你的課程教材與內容，透過練習審視自己的學習狀況\n" +
                "\n" +
                "\n" +
                "【P老師教學特色】\n" +
                "\uD83D\uDCA1螺旋式教學，帶領學生搭起學習的鷹架\n" +
                "\uD83D\uDCA1上課氛圍活潑有趣\n" +
                "\uD83D\uDCA1 靈活運算，概念澄清\n" +
                "\uD83D\uDCA1 交互式問答方式，讓學生熟悉數學                                                                                                   \n" +
                "\n" +
                "\n" +
                "【P老師的教學理念】\n" +
                "✔️ 因材施教 ： P老師帶著你找到適合的學習方法\n" +
                "✔️ 學習盲點 ： P老師陪著你發現努力的空間 \n" +
                "✔️ 鼓勵學習 ： P老師給予你滿滿學習的信心與動機\n" +
                "\n" +
                "\n" +
                "【P老師的課後Bonus】\n" +
                "⚜️提供學生當次上課的講義與上課筆記進行複習 \n" +
                "⚜️ 每堂課程後，能夠給予學生3次問問題的機會，即時為學生進行解答\n" +
                "\n" +
                "\n" +
                " 【課程規則提醒】\n" +
                "1. 上課請準時，最多只等10分鐘唷！\n" +
                "2. 取消課程請於12小時前取消，無事先告知取消原因並無出席，課程視為完成\n" +
                "3. 有複習、常練習才能進步\n" +
                "4. 正式課程10分鐘或體驗課程5分鐘未進教室，視為曠課。該預約課程不退款",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 98.0,
            subjectList = listOf(Subject(id = 2, text = "數學", educationIdList = listOf(1, 2, 3), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))),
            unitList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = 18,
        account = "rick",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/441/685/normal/wfmjtk.png?1606956448",
        name = "Rick",
        email = "rick@gmail.com",
        intro = "15年1對1數學家教經驗，250位以上1對1教學個案\n" +
                "帶過學測，指考，統測，國中會考，學校考試，轉學考，1對1手把手教學，幫助您達成考試目標。\n" +
                "數學不可怕，沒人問進度堆積才可怕，您只需要經驗豐富的人來幫，歡迎與我聯繫，讓我來幫您解決問題和煩惱~~\n" +
                "\n" +
                "1. 單堂課1對1線上解題：\n" +
                "有時候只想問些問題，但又要上一整學期的課特別麻煩，歡迎購買我的單堂解題課。\n" +
                "\n" +
                "2. 國高中1對1學期課程：\n" +
                "您有成績上的要求，希望可以達成成績目標，希望有個人來帶您安排進度，內容解說和解決問題，請與我聯繫，我可以幫您設計屬於您自己的學習計畫和內容，每週固定上課，考前加強複習，高分過關並不難。\n" +
                "\n" +
                "3. 國高中1對1補救課程：\n" +
                "進度落後太多，大考快到了，終於開始想努力，可是太晚起步，覺得使不上力，想先將之前的進度補上來，1對1的客製化設計內容，線上直接討論，加上事後練習發問，快速將進度追上並不是天方夜譚。\n" +
                "\n" +
                "4. 大考衝刺課程：\n" +
                "學測、指考、會考準備不知如何下手，範圍這麼大，模擬題好難，進度不會排，我來幫您量身打造，一步一步往目標成績前進。\n" +
                "\n" +
                "5. 客製化數學教學課程：\n" +
                "如果您有其他數學的客製化教學需求(例如準備SAT, 教師甄試, 轉學考等等)，外面補習班課程不好找，或上課時段不方便，歡迎與我聯繫，幫您設計屬於您自己的一對一課程，有效達成目標。\n" +
                "\n" +
                "如果跟您說只需要上課不用自己努力的話，那是百分之百的騙人，所有知識的學習都是需要努力的，但只要有合適的方法，和可實現的目標，一步一步往前推進，是可以大大提高成功率的。我的教學理念很簡單，就是幫助來買課程的您一步一步達成學習和考試目標，根據實際情況訂出合理目標，然後一起完成。\n" +
                "\n" +
                "如果您願意努力，只是缺一個能幫助您實現目標的人，歡迎來找我，很樂意為您服務。",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 100.0,
            subjectList = listOf(Subject(id = 2, text = "數學", educationIdList = listOf(1, 2, 3), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))),
            unitList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = 19,
        account = "massie",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/670/122/normal/dqwziv.png?1622181815",
        name = "\uD83C\uDF38Massie",
        email = "massie@gmail.com",
        intro = "✨留英6年名校畢業，A LEVEL 數學A*，具有3年以上教育經驗，港大電力工程硕士 \uD83D\uDC69\uD83C\uDFFB\u200D\uD83C\uDFED\n" +
                "\uD83D\uDC31 OCR A level/GCSE數學； STEM；學生提供的教材\n" +
                "\uD83E\uDD17 按照教材內容和學生水平制定私人化課程，著重講解學生想學的部分，結合實際應用講解，讓學生更好地融入課堂，以有趣的方式學習新知識 ^ ^\n" +
                "\n" +
                "\uD83C\uDF38上課跟不上老師節奏的或者對於數學無從下手的學生，想考A Level 或者GCSE的學生；\n" +
                "體驗課後專門定制課程，課下不懂的問題可隨時聯繫老師， 互動式教學，不會讓學生感到悶，強調熟能生巧，大量練習時間，不同題型攻破\n" +
                "\n" +
                "\uD83C\uDF38數學課程：若學生沒有教材，老師會為學生準備教材，並定時進行模擬考試，以及每堂課提供練習鞏固，修改會在課後第二天發送給學生。若學生要求，可在試課前進行一次模擬考。\n" +
                "教學範圍：小學，初中，A Level數學，GCSE 數學，DSE 數學，大學電子工程數學",
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 98.0,
            subjectList = listOf(Subject(id = 2, text = "數學", educationIdList = listOf(1, 2, 3), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))),
            unitList = unitListTest.filter { it.subjectId == 2L }
        )
    ),
    UserInfo(
        id = 20,
        account = "ging",
        auth = UserPref.Authority.TEACHER,
        avatarPath = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/464/931/normal/xdmhqp.png?1609914398",
        name = "謝寶Ging",
        email = "ging@gmail.com",
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
        teacherInfo = TeacherInfo(
            commentScoreAvg = 5.0,
            replyRate = 100.0,
            subjectList = listOf(Subject(id = 2, text = "數學", educationIdList = listOf(1, 2, 3), gradleIdList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))),
            unitList = unitListTest.filter { it.subjectId == 2L }
        )
    ),

    )
