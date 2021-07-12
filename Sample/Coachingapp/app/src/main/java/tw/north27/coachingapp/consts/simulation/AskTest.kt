package tw.north27.coachingapp.consts.simulation

import com.yujie.utilmodule.UserPref
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.AskRoomInfo
import tw.north27.coachingapp.model.AskType
import tw.north27.coachingapp.model.ClientInfo
import java.util.*

val askRoomList_Test = mutableListOf(
    AskRoom(
        id = 0,
        otherClientInfo = ClientInfo(
            id = "rebeccaAct",
            auth = UserPref.Authority.TEACHER,
            avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/732/757/normal/nlpknz.png?1623138408",
            name = "Rebecca",
        ),
        educationLevelId = 1,
        gradeId = 1,
        subjectId = 1,
        unitId = 2,
        isPush = true,
        isSound = true,
        unreadNum = 0,
        askRoomInfo = AskRoomInfo(
            id = 1L,
            senderAct = "rebeccaAct",
            receiverAct = account_Test,
            //
            askType = AskType.TEXT,
            text = "老師，我想詢問國語排一排的相關問題，請問現在是否方便嗎?",
            isRead = true,
            sendTime = Date()
        )
    ),
    AskRoom(
        id = 1,
        otherClientInfo = ClientInfo(
            id = "peiYuAct",
            auth = UserPref.Authority.TEACHER,
            avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/425/315/normal/acwdjm.png?1605207454",
            name = "裴育",
        ),
        educationLevelId = 1,
        gradeId = 2,
        subjectId = 2,
        unitId = 7,
        isPush = false,
        isSound = false,
        unreadNum = 2,
        askRoomInfo = AskRoomInfo(
            id = 1L,
            senderAct = "peiYuAct",
            receiverAct = account_Test,
            //
            askType = AskType.TEXT,
            text = "數學的數線解法這樣你能了解嗎?",
            isRead = false,
            sendTime = Date("2021/07/05 12:00")
        )
    )
)

fun askRoomInfoList_Test(roomId: Long): List<AskRoomInfo> {
    return when (roomId) {
        0L -> {
            mutableListOf(
                AskRoomInfo(
                    id = 1L,
                    senderAct = "rebeccaAct",
                    receiverAct = account_Test,
                    askType = AskType.TEXT,
                    text = "老師你好，國語排一排的是什麼意思呢?",
                    isRead = true,
                    sendTime = Date("2021/07/05 12:00")
                ),
                AskRoomInfo(
                    id = 2L,
                    senderAct = account_Test,
                    receiverAct = "rebeccaAct",
                    askType = AskType.TEXT,
                    text = "排隊的意思",
                    isRead = true,
                    sendTime = Date("2021/07/05 14:23")
                ),
                AskRoomInfo(
                    id = 3L,
                    senderAct = "rebeccaAct",
                    receiverAct = account_Test,
                    askType = AskType.TEXT,
                    text = "謝謝老師!",
                    isRead = true,
                    sendTime = Date("2021/07/05 14:48")
                )
            )

        }
        1L -> {
            mutableListOf(
                AskRoomInfo(
                    id = 1L,
                    senderAct = "rebeccaAct",
                    receiverAct = account_Test,
                    askType = AskType.TEXT,
                    text = "老師你好，數線解法有幾種?",
                    isRead = true,
                    sendTime = Date("2021/07/05 12:00")
                ),
                AskRoomInfo(
                    id = 2L,
                    senderAct = account_Test,
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