package edu.yujie.mviex

import edu.yujie.mviex.bean.RepoBean

interface ISearchRepo {

    suspend fun searchRepo(text: String) : RepoBean
}