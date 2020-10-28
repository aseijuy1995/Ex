package edu.yujie.junitex

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RepeatRule : TestRule {

    annotation class Repeat(val count: Int)

    override fun apply(base: Statement?, description: Description?): Statement =
        object : Statement() {
            override fun evaluate() {
                val repeat = description?.getAnnotation(Repeat::class.java)
                if (repeat != null) {
                    for (i in 0..repeat.count) {
                        base?.evaluate()
                    }
                } else {
                    base?.evaluate()
                }
            }
        }

}