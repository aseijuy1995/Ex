package edu.yujie.lohasapp.repo

import edu.yujie.lohasapp.IApiService

class OriginateRepository(private val service: IApiService) {


    suspend fun postVersion() = service.postVersion()


}