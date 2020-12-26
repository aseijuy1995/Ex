package edu.yujie.mvvmex

class SearchRepo(private val service: IApiService) {

    suspend fun searchRepo(text: String) = service.searchRepo(text)
}