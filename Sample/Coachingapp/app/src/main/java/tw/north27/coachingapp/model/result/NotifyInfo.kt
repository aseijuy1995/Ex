package tw.north27.coachingapp.model.result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//通知
@Parcelize
data class NotifyInfo(
    val id: Long,
    val imgUrl: String,
    val title: String,
    val desc: String,
    val time: String,
    var isRead: Boolean,// 已讀 = true,未讀 = false
    val notifyType: NotifyType
) : Parcelable

enum class NotifyType {
    NORMAL,// 一般
    ACTION // 活動
}
