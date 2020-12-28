package edu.yujie.mviex

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(private val repo: ISearchRepo) : ViewModel() {
    val searchViewEvent = Channel<SearchViewEvent>(Channel.UNLIMITED)

    private val mSearchStateFlow = MutableStateFlow<SearchViewState>(SearchViewState.Idle)
    val searchViewStateFlow: StateFlow<SearchViewState> = mSearchStateFlow
    private val mEmptyViewState = MutableStateFlow<EmptyViewState>(EmptyViewState.Idle)
    val emptyViewState: StateFlow<EmptyViewState> = mEmptyViewState


    init {
        initSearchIntent()
    }

    fun initSearchIntent() = viewModelScope.launch(Dispatchers.IO) {
        searchViewEvent.consumeAsFlow().collect {
            when (it) {
                is SearchViewEvent.FetchEmpty -> fetchEmpty(it.text)

                is SearchViewEvent.FetchSearch -> fetchRepo(it.text)
            }
        }
    }

    private fun fetchEmpty(text: String) {
        mSearchStateFlow.value = SearchViewState.Loading
        if (TextUtils.isEmpty(text))
            mEmptyViewState.value = EmptyViewState.Empty
        else {
            mEmptyViewState.value = EmptyViewState.NotEmpty(text)
        }
    }

    private fun fetchRepo(text: String) = viewModelScope.launch {
        mSearchStateFlow.value = try {
            val repoBean = repo.searchRepo(text)
            SearchViewState.Complete(repoBean)
        } catch (e: IOException) {
            SearchViewState.Error(e.message)
        }
    }


}