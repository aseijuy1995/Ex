package edu.yujie.mockkex

class Mother {
    private val TAG = javaClass.simpleName

    fun giveMoney(): Int = 100

    fun inform(money:Int) {
        println("$TAG:inform():money = $money")
    }
}