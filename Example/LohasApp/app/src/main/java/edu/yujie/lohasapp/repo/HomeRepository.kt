package edu.yujie.lohasapp.repo

import edu.yujie.lohasapp.IApiService

class HomeRepository(private val service: IApiService) {

    suspend fun getAdvert() = service.postImg(kind = "57")
}