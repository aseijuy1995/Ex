package edu.yujie.mockkex

class Kid(private val mother: Mother) {
    var money = 0
        private set

    fun wantMoney() {
        mother.inform(money)
        money += mother.giveMoney()
    }
}