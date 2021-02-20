package tw.north27.coachingapp.repository

import tw.north27.coachingapp.const.IApiService
import tw.north27.coachingapp.model.Data
import tw.north27.coachingapp.model.OriginateResult
import tw.north27.coachingapp.repository.inter.IStartRepository

class StartRepository(val service: IApiService) : IStartRepository {

    override suspend fun getOriginateData(): OriginateResult {
        return service.getOriginateData()
    }

    override suspend fun getData(): Data {
        return service.getData()
    }
}