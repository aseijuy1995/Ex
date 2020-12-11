package edu.yujie.coroutinesex

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

suspend fun loadData(delay: Long = 3000, name: String = "Default"): String = withContext(Dispatchers.IO) {
    delay(delay)
    "Hello Coroutines, name = $name"
}

suspend fun loadDataFromViewModel(delay: Long = 3000): String = withContext(Dispatchers.IO) {
    delay(delay)
    "Hello Coroutines From ViewModel"
}
