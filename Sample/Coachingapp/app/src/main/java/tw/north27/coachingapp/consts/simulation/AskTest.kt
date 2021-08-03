package tw.north27.coachingapp.consts.simulation

import com.yujie.core_lib.UserPref
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.response.ClientInfo
import java.util.*

val askRoomList_Test = mutableListOf(
    AskRoom(
        id = 0,
        otherClientInfo = ClientInfo(
            id = "rebeccaId",
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
            senderId = "rebeccaId",
            receiverId = clientId_Test,
            //
            askType = AskType.TEXT,
            text = "國語排一排的是什麼意思呢?",
            isRead = false,
            sendTime = Date()
        )
    ),
    AskRoom(
        id = 1,
        otherClientInfo = ClientInfo(
            id = "peiYuId",
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
            id = 2L,
            senderId = "peiYuId",
            receiverId = clientId_Test,
            //
            askType = AskType.VIDEO,
            videoList = listOf(
                AskVideo(
                    id = 1,
                    url = "https://www.youtube.com/watch?v=7keFPznK9so",
                    time = 120
                )
            ),
            isRead = true,
            sendTime = Date("2021/07/05 12:00")
        )
    ),
    AskRoom(
        id = 2,
        otherClientInfo = ClientInfo(
            id = "mimiId",
            auth = UserPref.Authority.TEACHER,
            avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/142/217/normal/vovnbl.png?1621920493",
            name = "米米老師",
        ),
        educationLevelId = 1,
        gradeId = 2,
        subjectId = 1,
        unitId = 4,
        isPush = true,
        isSound = false,
        unreadNum = 0,
        askRoomInfo = AskRoomInfo(
            id = 3L,
            senderId = clientId_Test,
            receiverId = "mimiId",
            //
            askType = AskType.IMAGE,
            imgList = listOf(
                AskImage(
                    id = 2,
                    url = "https://2.bp.blogspot.com/-n62RdDWGgDo/XwyKnpsMr8I/AAAAAAABEL4/tWPpDml1G4MRDLw_WJkOOEWJO2Y1kzkVgCLcBGAsYHQ/s1600/001.jpg"
                )
            ),
            isRead = true,
            sendTime = Date("2021/07/03 18:32")
        )
    ),
    AskRoom(
        id = 3,
        otherClientInfo = ClientInfo(
            id = "yujunId",
            auth = UserPref.Authority.TEACHER,
            avatarUrl = "https://d1ebg4c3may5v9.cloudfront.net/users/images/000/251/483/normal/vrvmsw.png?1624516244",
            name = "昱君",
        ),
        educationLevelId = 2,
        gradeId = 3,
        subjectId = 4,
        unitId = 13,
        isPush = false,
        isSound = true,
        unreadNum = 2,
        askRoomInfo = AskRoomInfo(
            id = 4L,
            senderId = "yujunId",
            receiverId = clientId_Test,
            askType = AskType.VIDEO,
            videoList = listOf(
                AskVideo(
                    id = 1,
                    url = "http://demos.webmproject.org/exoplayer/glass.mp4",
                    time = 135
                ),
                AskVideo(
                    id = 2,
                    url = "http://demos.webmproject.org/exoplayer/glass.mp4",
                    time = 135
                ),
            ),
            isRead = false,
            sendTime = Date("2021/07/03 14:58")
        )
    )
)

fun askRoomInfoList_Test(roomId: Long): List<AskRoomInfo> {
    return when (roomId) {
        0L -> {
            mutableListOf(
                AskRoomInfo(
                    id = 1L,
                    senderId = clientId_Test,
                    receiverId = "rebeccaId",
                    askType = AskType.TEXT,
                    text = "老師你好",
                    isRead = false,
                    sendTime = Date("2021/07/05 12:00")
                ),
                AskRoomInfo(
                    id = 2L,
                    senderId = clientId_Test,
                    receiverId = "rebeccaId",
                    askType = AskType.TEXT,
                    text = "國語排一排的是什麼意思呢?",
                    isRead = false,
                    sendTime = Date("2021/07/05 14:48")
                )
            )

        }
        1L -> {
            mutableListOf(
                AskRoomInfo(
                    id = 3,
                    senderId = clientId_Test,
                    receiverId = "peiYuId",
                    askType = AskType.TEXT,
                    text = "老師你知道數線是什麼嗎?",
                    isRead = true,
                    sendTime = Date("2021/07/03 20:08")
                ),
                AskRoomInfo(
                    id = 4,
                    senderId = "peiYuId",
                    receiverId = clientId_Test,
                    askType = AskType.TEXT,
                    text = "老師怎麼可能不知道!來~你看這個",
                    isRead = false,
                    sendTime = Date("2021/07/04 07:21")
                ),
                AskRoomInfo(
                    id = 5,
                    senderId = "peiYuId",
                    receiverId = clientId_Test,
                    askType = AskType.IMAGE,
                    imgList = listOf(
                        AskImage(
                            id = 4,
                            url = "https://www.youtube.com/watch?v=7keFPznK9so"
                        )
                    ),
                    isRead = false,
                    sendTime = Date("2021/07/04 07:23")
                )
            )
        }
        2L -> {
            mutableListOf(
                AskRoomInfo(
                    id = 6,
                    senderId = clientId_Test,
                    receiverId = "mimiId",
                    askType = AskType.TEXT,
                    text = "老師早安",
                    isRead = true,
                    sendTime = Date("2021/06/27 14:23")
                ),
                AskRoomInfo(
                    id = 7,
                    senderId = clientId_Test,
                    receiverId = "mimiId",
                    askType = AskType.TEXT,
                    text = "聽說你會國語的再玩一次，明天我寄圖片給你看",
                    isRead = true,
                    sendTime = Date("2021/06/27 14:28")
                ),
                AskRoomInfo(
                    id = 8,
                    senderId = clientId_Test,
                    receiverId = "mimiId",
                    askType = AskType.TEXT,
                    text = "抱歉稍晚了點，那下方這個圖片可以幫我解釋一下嗎",
                    isRead = true,
                    sendTime = Date("2021/07/01 13:21")
                ),
                AskRoomInfo(
                    id = 9,
                    senderId = clientId_Test,
                    receiverId = "mimiId",
                    askType = AskType.IMAGE,
                    imgList = listOf(
                        AskImage(
                            id = 5,
                            url = "https://2.bp.blogspot.com/-n62RdDWGgDo/XwyKnpsMr8I/AAAAAAABEL4/tWPpDml1G4MRDLw_WJkOOEWJO2Y1kzkVgCLcBGAsYHQ/s1600/001.jpg"
                        ),
                        AskImage(
                            id = 50,
                            url = "https://2.bp.blogspot.com/-n62RdDWGgDo/XwyKnpsMr8I/AAAAAAABEL4/tWPpDml1G4MRDLw_WJkOOEWJO2Y1kzkVgCLcBGAsYHQ/s1600/001.jpg"
                        ),
                        AskImage(
                            id = 500,
                            url = "https://2.bp.blogspot.com/-n62RdDWGgDo/XwyKnpsMr8I/AAAAAAABEL4/tWPpDml1G4MRDLw_WJkOOEWJO2Y1kzkVgCLcBGAsYHQ/s1600/001.jpg"
                        ),
                        AskImage(
                            id = 51,
                            url = "https://2.bp.blogspot.com/-n62RdDWGgDo/XwyKnpsMr8I/AAAAAAABEL4/tWPpDml1G4MRDLw_WJkOOEWJO2Y1kzkVgCLcBGAsYHQ/s1600/001.jpg"
                        ),
                        AskImage(
                            id = 502,
                            url = "https://2.bp.blogspot.com/-n62RdDWGgDo/XwyKnpsMr8I/AAAAAAABEL4/tWPpDml1G4MRDLw_WJkOOEWJO2Y1kzkVgCLcBGAsYHQ/s1600/001.jpg"
                        ),
                        AskImage(
                            id = 5003,
                            url = "https://2.bp.blogspot.com/-n62RdDWGgDo/XwyKnpsMr8I/AAAAAAABEL4/tWPpDml1G4MRDLw_WJkOOEWJO2Y1kzkVgCLcBGAsYHQ/s1600/001.jpg"
                        )
                    ),
                    isRead = true,
                    sendTime = Date("2021/07/01 13:22")
                )
            )
        }
        3L -> {
            mutableListOf(
                AskRoomInfo(
                    id = 10,
                    senderId = "yujunId",
                    receiverId = clientId_Test,
                    askType = AskType.TEXT,
                    text = "老師幫你整理些東西，記得要讀喔!",
                    isRead = false,
                    sendTime = Date("2021/06/28 10:45")
                ),
                AskRoomInfo(
                    id = 11,
                    senderId = "yujunId",
                    receiverId = clientId_Test,
                    askType = AskType.VIDEO,
                    videoList = listOf(
                        AskVideo(
                            id = 1,
                            url = "http://demos.webmproject.org/exoplayer/glass.mp4",
                            time = 135
                        ),
                        AskVideo(
                            id = 2,
                            url = "http://demos.webmproject.org/exoplayer/glass.mp4",
                            time = 135
                        ),
                    ),
                    isRead = false,
                    sendTime = Date("2021/06/28 10:47")
                )
            )
        }
        else -> emptyList()
    }
}