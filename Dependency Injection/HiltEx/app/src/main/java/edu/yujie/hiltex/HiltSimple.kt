package edu.yujie.hiltex

import javax.inject.Inject

class HiltSimple @Inject constructor() {
    private val TAG = javaClass.simpleName

    fun doSomething(): String = "$TAG:doSomething()"
}