package tw.north27.coachingapp.model.request

/**
 * @param account >> 帳號
 * @param topAskId >> 最新提問條
 * */
data class AskRoomRequest(
    val account: String,
    val topAskId: Long? = null,
)