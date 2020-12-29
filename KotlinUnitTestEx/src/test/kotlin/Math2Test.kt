
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Math2Test {
    private lateinit var math2: Math2

    @BeforeEach
    fun before() {
        math2 = Math2()
    }

    @Test
    fun addTest() {
        val expected = 3

        math2.add(1, 2)

        assertEquals(expected, math2.result)
    }

    @Test
    fun minimumTest() {
        val expected = 1.coerceAtMost(2)
        val actual = math2.minimum(1, 2)
        assertEquals(expected, actual)
    }

    @Test
    fun minimumTest2() {
        val expected = 2.coerceAtMost(1)
        val actual = math2.minimum(2, 1)
        assertEquals(expected, actual)
    }
}