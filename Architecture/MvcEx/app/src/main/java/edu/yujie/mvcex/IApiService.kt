package edu.yujie.mvcex

import edu.yujie.mvcex.model.bean.*
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    //sort - stars/forks/help-wanted-issues/updated
    //order - desc/asc

    @GET("/search/code")
    suspend fun searchCode(
        @Query("accept") accept: String? = "application/vnd.github.v3+json",
        @Query("q") q: String? = null,
        @Query("sort") sort: String? = "stars",
        @Query("order") order: String? = "desc",
        @Query("per_page") per_page: String? = "20",
        @Query("page") page: String? = "1"
    ): CodeBean

    @GET("/search/commits")
    suspend fun searchCommits(
        @Query("accept") accept: String? = null,
        @Query("q") q: String? = null,
        @Query("sort") sort: String? = "stars",
        @Query("order") order: String? = "desc",
        @Query("per_page") per_page: String? = "20",
        @Query("page") page: String? = "1"
    ): CommitsBean

    @GET("/search/issues")
    suspend fun searchIssue(
        @Query("accept") accept: String? = "application/vnd.github.v3+json",
        @Query("q") q: String? = null,
        @Query("sort") sort: String? = "stars",
        @Query("order") order: String? = "desc",
        @Query("per_page") per_page: String? = "20",
        @Query("page") page: String? = "1"
    ): IssuesBean

    @GET("/search/labels")
    suspend fun searchLabels(
        @Query("accept") accept: String? = "application/vnd.github.v3+json",
        @Query("repository_id") repository_id: String? = null,
        @Query("q") q: String? = null,
        @Query("sort") sort: String? = "stars",
        @Query("order") order: String? = "desc"
    ): LabelsBean

    @GET("/search/repositories")
    suspend fun searchRepositories(
        @Query("accept") accept: String? = "application/vnd.github.v3+json",
        @Query("q") q: String? = null,
        @Query("sort") sort: String? = "stars",
        @Query("order") order: String? = "desc",
        @Query("per_page") per_page: String? = "20",
        @Query("page") page: String? = "1"
    ): RepositoriesBean

    @GET("/search/topics")
    suspend fun searchTopics(
        @Query("accept") accept: String? = null,
        @Query("q") q: String? = null
    ): TopicsBean

    @GET("/search/users")
    suspend fun searchUsers(
        @Query("accept") accept: String? = "application/vnd.github.v3+json",
        @Query("q") q: String? = null,
        @Query("sort") sort: String? = "stars",
        @Query("order") order: String? = "desc",
        @Query("per_page") per_page: String? = "20",
        @Query("page") page: String? = "1"
    ): UsersBean

}