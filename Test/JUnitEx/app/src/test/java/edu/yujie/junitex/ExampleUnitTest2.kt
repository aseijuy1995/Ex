package edu.yujie.junitex

import org.junit.Before
import org.junit.Test


class ExampleUnitTest2 {
    private val TAG = javaClass.simpleName

    @Before
    fun before() {
        println("$TAG:before()")
    }

    @Test
    fun test() {
        println("$TAG:test()")
    }
}