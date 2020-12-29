import io.mockk.every
import io.mockk.mockk
import jdk.nashorn.internal.objects.NativeArray.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class UmbrellaTest {
    private lateinit var umbrella: Umbrella
    private lateinit var stubWeather: StubWeather

    @BeforeEach
    fun before() {
        umbrella = Umbrella()
        stubWeather = StubWeather()
    }

    @Test
    fun totalPriceTest() {
        val expected = 90
        val actual = umbrella.totalPrice(Weather(), 1, 100)
        assertEquals(expected, actual)
    }

    @Test
    fun totalPrice2Test() {
        val expected = 90
        stubWeather.fakeIsSunny = true
        val actual = umbrella.totalPrice(stubWeather, 1, 100)
        assertEquals(expected, actual)
    }

    @Test
    fun totalPrice3Test() {
        val expected = 100
        stubWeather.fakeIsSunny = false
        val actual = umbrella.totalPrice(stubWeather, 1, 100)
        assertEquals(expected, actual)
    }

    @Test
    fun totalPrice4Test() {
        val weather = Mockito.mock(IWeather::class.java)
        `when`(weather.isSunny()).thenReturn(true)
        val actual = umbrella.totalPrice(weather, 100, 1)
        val expected = 90
        assertEquals(expected, actual)
    }

    @Test
    fun totalPriceSunnyDay() {
        val umbrella = Umbrella()
        val weather = mockk<IWeather>()

        every{ weather.isSunny() }.returns(true)

        val actual = umbrella.totalPrice(weather, 1, 100)
        val expected = 90
        assertEquals(expected, actual)
    }

}