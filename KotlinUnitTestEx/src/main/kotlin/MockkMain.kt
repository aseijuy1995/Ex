class MockkMain {
    fun funcReturnString(): String = "A"

    fun funcReturnInt(): Int = 1

    fun funcReturnUnit() {}

    //--------------------------------------------------------------------------------

    enum class MockEnum(val number: Int) {
        FIRST(1), SECOND(2), THIRD(3), FOURTH(4)
    }

    //--------------------------------------------------------------------------------

    fun funcVararg(vararg numbers: Int): Int = 0

    //--------------------------------------------------------------------------------

    fun funcSetInt(i: Int) {}

    fun funcSetString(s: String) {}

    //--------------------------------------------------------------------------------

    fun funcReturnUnit2() {}

    fun funcReturnUnit3() {}

    fun funcReturnUnit4() {}

    //--------------------------------------------------------------------------------

}