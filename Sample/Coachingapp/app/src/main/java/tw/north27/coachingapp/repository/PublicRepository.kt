package tw.north27.coachingapp.repository

import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.ext2.safeApiResults
import tw.north27.coachingapp.model.result.AppConfig
import tw.north27.coachingapp.model.result.UserInfo
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.repository.inter.IPublicRepository

class PublicRepository(val service: IApiService) : IPublicRepository {

    override suspend fun getAppConfig(uuid: String, fcmToken: String): Results<AppConfig> = safeApiResults { service.getAppConfig(uuid, fcmToken) }

    override suspend fun getLoadTeacher(): Results<List<UserInfo>> = safeApiResults { service.getLoadTeacher() }
}