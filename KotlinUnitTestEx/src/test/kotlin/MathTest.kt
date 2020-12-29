import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MathTest {

    @Test
    fun addTest() {
        val expected = 3
        val actual = Math().add(1, 2)
        assertEquals(expected, actual)
    }
}