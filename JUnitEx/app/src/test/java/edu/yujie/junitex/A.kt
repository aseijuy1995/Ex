package edu.yujie.junitex

import org.junit.Test
import org.junit.experimental.categories.Category

class A {
    private val TAG = javaClass.simpleName

    @Test
    fun a() {
        println("$TAG:a()")
    }

    @Test
    @Category(Categories.SlowTests::class)
    fun b() {
        println("$TAG:b()")
    }
}