package edu.yujie.mviex

sealed class SearchViewEvent(private val text: String?) {

    data class FetchEmpty(val text: String) : SearchViewEvent(text = text)

    data class FetchSearch(val text: String) : SearchViewEvent(text = text)
}