package edu.yujie.junitex

import org.junit.Test
import org.junit.experimental.categories.Category

@Category(Categories.FastTests::class, Categories.SlowTests::class)
class B {
    private val TAG = javaClass.simpleName

    @Test
    fun c() {
        println("$TAG:b()")
    }
}