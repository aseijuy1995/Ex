package edu.yujie.mviex

import edu.yujie.mviex.bean.RepoBean

class ApiService(private val service: IApiService) : IApiService {

    override suspend fun searchRepo(query: String): RepoBean = service.searchRepo(query)
}