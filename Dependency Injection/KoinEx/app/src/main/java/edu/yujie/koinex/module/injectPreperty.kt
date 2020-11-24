package edu.yujie.koinex.module

class A {
    lateinit var b: B
    lateinit var c: C

    fun show() {
        b.show()
        c.show()
    }
}

class B : D {
    private val TAG = javaClass.simpleName
    override fun show() {
        println("$TAG:show()")
    }
}

class C : D {
    private val TAG = javaClass.simpleName
    override fun show() {
        println("$TAG:show()")
    }
}

interface D {
    fun show()
}