package tw.north27.coachingapp.repository

import com.yujie.core_lib.http.Results
import com.yujie.core_lib.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.response.AppConfig
import tw.north27.coachingapp.model.response.AppConfigRequest
import tw.north27.coachingapp.model.response.Education
import tw.north27.coachingapp.model.response.PublicDataResponse

class PublicRepository(val service: IApiService) : IPublicRepository {

    override suspend fun fetchAppConfig(appConfigRequest: AppConfigRequest): Results<AppConfig> =
        safeApiResults { service.fetchAppConfig(appConfigRequest = appConfigRequest) }

    override suspend fun fetchEducation(): Results<Education> =
        safeApiResults { service.fetchEducation() }

    override suspend fun fetchPublicData(): Results<PublicDataResponse> =
        safeApiResults { service.fetchPublicData() }
}