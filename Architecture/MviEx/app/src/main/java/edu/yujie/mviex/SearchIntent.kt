package edu.yujie.mviex

sealed class SearchIntent(private val text: String?) {

    data class FetchEmpty(val text: String) : SearchIntent(text = text)

    data class FetchSearch(val text: String) : SearchIntent(text = text)
}