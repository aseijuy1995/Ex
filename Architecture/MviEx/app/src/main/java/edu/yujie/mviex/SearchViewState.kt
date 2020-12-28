package edu.yujie.mviex

import edu.yujie.mviex.bean.RepoBean

sealed class SearchViewState {

    object Idle : SearchViewState()

    object Loading : SearchViewState()

    data class Complete(val repo: RepoBean) : SearchViewState()

    data class Error(val error: String?) : SearchViewState()
}