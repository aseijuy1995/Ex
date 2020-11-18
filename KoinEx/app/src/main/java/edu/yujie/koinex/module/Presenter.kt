package edu.yujie.koinex.module

open class Presenter {
    private val TAG = javaClass.simpleName
    fun show() {
        println("$TAG:show()")
    }
}

class MyPresenter(str: String) : Presenter() {

}