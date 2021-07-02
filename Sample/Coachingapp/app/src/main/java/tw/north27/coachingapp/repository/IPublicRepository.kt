package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.response.PublicDataResponse
import tw.north27.coachingapp.model.AppConfig
import tw.north27.coachingapp.model.response.Education

interface IPublicRepository {

    suspend fun fetchEducationData(): Results<Education>

    suspend fun fetchAppConfig(): Results<AppConfig>

    suspend fun fetchPublicData(): Results<PublicDataResponse>
}