package tw.north27.coachingapp.model.request

/**
 * @param account >> 帳號
 * @param latestAskId >> 最新提問條
 * */
data class AskRequest(
    val account: Long,
    val latestAskId: Long? = null,
)