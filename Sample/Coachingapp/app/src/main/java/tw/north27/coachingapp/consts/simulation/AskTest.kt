package tw.north27.coachingapp.consts.simulation

import tw.north27.coachingapp.model.AskInfo
import tw.north27.coachingapp.model.AskType
import java.util.*

fun askListTest(roomId: Long): List<AskInfo> {
    return when (roomId) {
        0L -> {
            mutableListOf(
                AskInfo(
                    id = 1L,
                    senderAct = "rebeccaAct",
                    receiverAct = accountTest,
                    askType = AskType.TEXT,
                    text = "老師你好，國語排一排的是什麼意思呢?",
                    isRead = true,
                    sendTime = Date("2021/07/05 12:00")
                ),
                AskInfo(
                    id = 2L,
                    senderAct = accountTest,
                    receiverAct = "rebeccaAct",
                    askType = AskType.TEXT,
                    text = "排隊的意思",
                    isRead = true,
                    sendTime = Date("2021/07/05 14:23")
                ),
                AskInfo(
                    id = 3L,
                    senderAct = "rebeccaAct",
                    receiverAct = accountTest,
                    askType = AskType.TEXT,
                    text = "謝謝老師!",
                    isRead = true,
                    sendTime = Date("2021/07/05 14:48")
                )
            )

        }
        1L -> {
            mutableListOf(
                AskInfo(
                    id = 1L,
                    senderAct = "rebeccaAct",
                    receiverAct = accountTest,
                    askType = AskType.TEXT,
                    text = "老師你好，數線解法有幾種?",
                    isRead = true,
                    sendTime = Date("2021/07/05 12:00")
                ),
                AskInfo(
                    id = 2L,
                    senderAct = accountTest,
                    receiverAct = "rebeccaAct",
                    askType = AskType.TEXT,
                    text = "很多種",
                    isRead = true,
                    sendTime = Date("2021/07/05 14:23")
                )
            )
        }
        else -> emptyList()
    }
}