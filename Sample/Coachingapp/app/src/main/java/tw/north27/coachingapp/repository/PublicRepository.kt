package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.response.PublicDataResponse
import tw.north27.coachingapp.model.AppConfig
import tw.north27.coachingapp.model.response.Education

class PublicRepository(val service: IApiService) : IPublicRepository {

    override suspend fun fetchEducationData(): Results<Education> = safeApiResults { service.fetchEducationData() }

    override suspend fun fetchAppConfig(): Results<AppConfig> = safeApiResults { service.fetchAppConfig() }

    override suspend fun fetchPublicData(): Results<PublicDataResponse> = safeApiResults { service.fetchPublicData() }

    //
    //
    //
    //
    //
    //
    //
    //
    //
    //


}