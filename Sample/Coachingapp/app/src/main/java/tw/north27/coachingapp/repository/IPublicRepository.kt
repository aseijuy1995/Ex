package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.request.AppConfigRequest
import tw.north27.coachingapp.model.response.AppConfig
import tw.north27.coachingapp.model.response.Education
import tw.north27.coachingapp.model.response.PublicDataResponse

interface IPublicRepository {

    suspend fun fetchAppConfig(appConfigRequest: AppConfigRequest): Results<AppConfig>

    suspend fun fetchEducation(): Results<Education>

    suspend fun fetchPublicData(): Results<PublicDataResponse>
}