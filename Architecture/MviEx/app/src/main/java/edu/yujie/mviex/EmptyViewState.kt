package edu.yujie.mviex

sealed class EmptyViewState() {

    object Idle : EmptyViewState()

    object Empty : EmptyViewState()

    data class NotEmpty(var text: String) : EmptyViewState()
}