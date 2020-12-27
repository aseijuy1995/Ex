package edu.yujie.mviex

class SearchRepo(private val service: IApiService) : ISearchRepo {

    override suspend fun searchRepo(text: String) = service.searchRepo(text)
}