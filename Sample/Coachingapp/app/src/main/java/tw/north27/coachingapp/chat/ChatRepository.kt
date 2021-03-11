package tw.north27.coachingapp.chat

import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.ext.safeApiResults
import tw.north27.coachingapp.module.http.Results

class ChatRepository(val service: IApiService) : IChatRepository {

    override suspend fun loadChat(): Results<List<ChatInfo>> {
        return safeApiResults { service.getLoadChat() }
    }
}