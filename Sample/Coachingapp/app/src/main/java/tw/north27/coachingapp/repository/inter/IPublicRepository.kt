package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.model.result.AppConfig
import tw.north27.coachingapp.module.http.Results

interface IPublicRepository {

    suspend fun getAppConfig(
        uuid: String,
        fcmToken: String
    ): Results<AppConfig>
}