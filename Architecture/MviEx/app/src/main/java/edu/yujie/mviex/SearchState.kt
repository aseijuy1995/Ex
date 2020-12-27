package edu.yujie.mviex

import edu.yujie.mviex.bean.RepoBean

sealed class SearchState {

    class Empty : SearchState()

    data class NoEmpty(val text: String) : SearchState()

    object Idle : SearchState()

    class Loading : SearchState()

    data class Complete( val repo: RepoBean) : SearchState()

    data class Error( val error: String?) : SearchState()
}