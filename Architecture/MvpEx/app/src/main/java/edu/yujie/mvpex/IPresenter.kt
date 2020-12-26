package edu.yujie.mvpex

interface IPresenter {

    fun checkEmpty(text: String?): Boolean

    fun search(text: String)
}