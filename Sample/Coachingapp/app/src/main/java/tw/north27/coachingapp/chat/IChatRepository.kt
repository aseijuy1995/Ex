package tw.north27.coachingapp.chat

import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.Results

interface IChatRepository {

    suspend fun loadChat(): Results<List<ChatInfo>>

}