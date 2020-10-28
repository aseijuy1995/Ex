package edu.yujie.junitex

class Fibonacci {
    companion object {
        fun compute(n: Int): Int {
            var result = 0
            if (n <= 1) {
                result = n
            } else {
                result = compute(n - 1) + compute(n - 2)
            }
            return result
        }
    }
}