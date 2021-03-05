package tw.north27.coachingapp.repository

import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.result.AppConfig
import tw.north27.coachingapp.module.ext.safeApiSimpleResults
import tw.north27.coachingapp.module.http.SimpleResults
import tw.north27.coachingapp.repository.inter.IPublicRepository

class PublicRepository(val service: IApiService) : IPublicRepository {

    override suspend fun getAppConfig(fcmToken: String): SimpleResults<AppConfig> {
        return safeApiSimpleResults { service.getAppConfig(fcmToken) }
    }
}