package edu.yujie.koinex.module

import android.view.View

interface Service {
    fun doSomething()
}

class Controller(val view: View)

interface Service2 {
    fun doSomething2()
}

class ServiceImpl : Service, Service2 {
    private val TAG = javaClass.simpleName
    override fun doSomething() {
        println("$TAG:doSomething")
    }

    override fun doSomething2() {
        println("$TAG:doSomething2")
    }
}

class TestServiceImp : Service {
    private val TAG = javaClass.simpleName
    override fun doSomething() {
        println("$TAG:doSomething")
    }
}

class MyService