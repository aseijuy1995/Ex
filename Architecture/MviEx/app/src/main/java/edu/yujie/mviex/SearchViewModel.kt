package edu.yujie.mviex

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.io.IOException
import java.lang.Thread.sleep

class SearchViewModel(private val repo: ISearchRepo) : ViewModel() {
    val searchIntent = Channel<SearchIntent>(Channel.UNLIMITED)
    private val mSearchStateFlow = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchStateFlow: StateFlow<SearchState> = mSearchStateFlow


    init {
        initSearchIntent()
    }

    fun initSearchIntent() = viewModelScope.launch(Dispatchers.IO) {
        searchIntent.consumeAsFlow().collect {
            when (it) {
                is SearchIntent.FetchEmpty -> fetchEmpty(it.text)

                is SearchIntent.FetchSearch -> fetchRepo(it.text)
            }
        }
    }

    private fun fetchEmpty(text: String) {
        mSearchStateFlow.value = SearchState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            withContext(Dispatchers.Main){
                if (TextUtils.isEmpty(text))
                    mSearchStateFlow.value = SearchState.Empty()
                else
                    mSearchStateFlow.value = SearchState.NoEmpty(text)
            }
        }
    }

    private fun fetchRepo(text: String) = viewModelScope.launch {
        mSearchStateFlow.value = try {
            val repoBean = repo.searchRepo(text)
            SearchState.Complete(repoBean)
        } catch (e: IOException) {
            SearchState.Error(e.message)
        }
    }


}