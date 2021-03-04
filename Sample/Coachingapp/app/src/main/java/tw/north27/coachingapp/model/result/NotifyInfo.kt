package tw.north27.coachingapp.model.result

//通知
data class NotifyInfo(
    val id: Long,
    val imgUrl: String,
    val title: String,
    val desc: String,
    val time: String,
    var isRead: Boolean,// 已讀 = true,未讀 = false
    val notifyType: NotifyType
)

enum class NotifyType {
    NORMAL,// 一般
    ACTION // 活動
}
