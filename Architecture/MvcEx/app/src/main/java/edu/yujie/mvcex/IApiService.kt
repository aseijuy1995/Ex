package edu.yujie.mvcex

import edu.yujie.mvcex.bean.RepoBean
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    ////https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}
    @GET("/search/repositories")
    suspend fun searchRepo(@Query("q") query: String): RepoBean

}