package edu.yujie.coroutinesex

import kotlinx.coroutines.delay

suspend fun loadData(delay:Long = 3000, name:String = "Default"): String {
    delay(delay)
    return "Hello Coroutines, name = $name"
}

suspend fun loadDataFromViewModel(): String {
    delay(3000)
    return "Hello Coroutines From ViewModel"
}
