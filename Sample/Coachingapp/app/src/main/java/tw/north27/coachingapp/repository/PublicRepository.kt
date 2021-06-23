package tw.north27.coachingapp.repository

import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.http.safeApiResults
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.AppConfig
import tw.north27.coachingapp.model.EducationData

class PublicRepository(val service: IApiService) : IPublicRepository {

    override suspend fun getEducationData(): Results<EducationData> = safeApiResults { service.getEducationData() }

    override suspend fun getAppConfig(): Results<AppConfig> = safeApiResults { service.getAppConfig() }

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