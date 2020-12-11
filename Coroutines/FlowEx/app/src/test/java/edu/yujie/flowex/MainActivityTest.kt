package edu.yujie.flowex

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class MainActivityTest {

    @Test
    fun getFlowFirstTest() = runBlocking {
        val value = flowDemo().first()
        assertEquals(value, 1)
    }

    @Test
    fun getFlowTest() = runBlocking {
        val list = flowDemo().toList()
        assertEquals(list, listOf(1, 3, 5, 7, 9, 2, 4, 6, 8, null))
    }
}