package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.model.result.AppConfig
import tw.north27.coachingapp.module.http.SimpleResults

interface IPublicRepository {

    suspend fun getAppConfig(): SimpleResults<AppConfig>
}