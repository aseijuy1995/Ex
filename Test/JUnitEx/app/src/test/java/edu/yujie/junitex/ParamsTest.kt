package edu.yujie.junitex

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
/**
 * org.junit.runners.model.InvalidTestClassError: Invalid test class 'edu.yujie.junitex.ParamsTest':
1. Test class should have exactly one public zero-argument constructor
 * */
@RunWith(Parameterized::class)
class ParamsTest(
    @Parameterized.Parameter val input: Int,
    @Parameterized.Parameter(1) val expected: Int
) {

    @Test
    fun testFibonacci() {
        println("input:$input, expected:$expected")
        assertEquals(expected, Fibonacci.compute(input))
    }

    companion object {
        //測試數據
        @Parameterized.Parameters
        @JvmStatic
        fun getParams() =
            arrayOf(
                arrayOf(0, 0),
                arrayOf(1, 1),
                arrayOf(2, 1),
                arrayOf(3, 2),
                arrayOf(4, 3),
                arrayOf(5, 5),
                arrayOf(6, 8)
            )
    }
}