package edu.yujie.channelex

import kotlinx.coroutines.delay

suspend fun loadData(delay: Long = 1000, name: String): String {
    delay(delay)
    return name
}