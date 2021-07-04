package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.AppConfig
import tw.north27.coachingapp.model.response.Education
import tw.north27.coachingapp.model.response.PublicDataResponse

interface IPublicRepository {

    suspend fun fetchAppConfig(deviceType: String): Results<AppConfig>

    suspend fun fetchEducationData(): Results<Education>

    suspend fun fetchPublicData(): Results<PublicDataResponse>
}