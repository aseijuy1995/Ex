package edu.yujie.mockkex

class Util {
    fun ok() {
        UtilJava.ok()
        UtilKotlin.ok()
    }
}


//object UtilKotlin {
class UtilKotlin {

//    @JvmStatic
//    fun ok(): String = "$TAG:ok()"

    companion object {
        private val TAG = "UtilKotlin"

//        @JvmStatic
        fun ok(): String = "$TAG:ok()"
    }
}