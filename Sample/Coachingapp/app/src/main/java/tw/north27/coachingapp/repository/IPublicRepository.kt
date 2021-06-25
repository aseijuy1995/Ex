package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import tw.north27.coachingapp.model.response.PublicDataResponse
import tw.north27.coachingapp.model.AppConfig
import tw.north27.coachingapp.model.EducationData

interface IPublicRepository {

    suspend fun fetchEducationData(): Results<EducationData>

    suspend fun fetchAppConfig(): Results<AppConfig>

    suspend fun fetchPublicData(): Results<PublicDataResponse>
}