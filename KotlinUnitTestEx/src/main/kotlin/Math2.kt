class Math2 {
    var result = 0

    fun add(number1: Int, number2: Int) {
        result = number1 + number2
    }

    fun minimum(number1: Int, number2: Int): Int {
        return if (number1 > number2) {
            number2
        } else {
            number1
        }
    }
}