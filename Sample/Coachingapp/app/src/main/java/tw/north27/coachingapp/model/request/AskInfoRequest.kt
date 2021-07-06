package tw.north27.coachingapp.model.request

/**
 * @param roomId >> 房間id
 * @param account >> 帳號
 * @param index >> 索引
 * @param num >> 筆數
 * */
data class AskInfoRequest(
    val roomId: Long,
    val account: String,
    val index: Int,
    val num: Int,
)