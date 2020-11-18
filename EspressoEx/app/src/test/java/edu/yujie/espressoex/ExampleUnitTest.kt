package edu.yujie.espressoex

import junit.framework.Assert.assertEquals
import org.hamcrest.Matchers.closeTo
//import org.hamcrest.number.IsCloseTo.closeTo
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() =
        try {
            assertEquals(4, 2 + 2)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    @Test
    fun hamcrestNumber() {
        Assert.assertThat(10.6, closeTo(10.5, 0.2))
    }

}