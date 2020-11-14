package edu.yujie.mockitoex

class Foo {
    private val TAG = javaClass.simpleName

    fun doFoo() {
        println("$TAG:doFoo()")
    }

    fun getCount() = 1
}