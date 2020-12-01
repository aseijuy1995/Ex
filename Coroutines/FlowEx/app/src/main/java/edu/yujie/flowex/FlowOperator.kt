package edu.yujie.flowex

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow


fun flowDemo() = flow {
    listOf(1, 2, 3, 4).forEach {
        delay(1000)
        emit(it)
    }
}

fun flowDemo2() = flow {
    listOf(5, 6, 7, 8).forEach {
        delay(1000)
        emit(it)
    }
}