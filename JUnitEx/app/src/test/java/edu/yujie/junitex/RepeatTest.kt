package edu.yujie.junitex

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName

class RepeatTest {
    private val TAG = javaClass.simpleName

    @Rule
    @JvmField
    val testName = TestName()

    @Rule
    @JvmField
    val repeatRule = RepeatRule()

    @RepeatRule.Repeat(5)
    @Test
    fun testMethod() {
        println("$TAG:${testName.methodName}")
    }
}