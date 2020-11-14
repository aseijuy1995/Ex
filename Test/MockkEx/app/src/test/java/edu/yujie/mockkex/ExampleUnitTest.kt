package edu.yujie.mockkex

import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testMockkWantMoney() {
        val mother = mockk<Mother>()
        val kid = Kid(mother)

        every { mother.giveMoney() } returns 30

        kid.wantMoney()
        assertEquals(30, kid.money)
    }

    //Relaxed:該物件所有的方法都不需要指定
    @MockK
//    @MockK(relaxed = true)
//    @MockK(relaxUnitFun = true)
    lateinit var mother: Mother

    lateinit var kid: Kid

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        kid = Kid(mother)
    }

    @Test
    fun testMockkAnnoWantMoney() {
        every { mother.giveMoney() } returns 30
        every { mother.inform(any()) } just Runs
        kid.wantMoney()
        assertEquals(30, kid.money)

        verify { mother.inform(any()) }
    }

    @Test
    fun testMockkRelaxedWantMoney() {
        kid.wantMoney()
        assertEquals(0, kid.money)

        verify { mother.inform(any()) }
    }

    @Test
    fun testMockkRelaxUnitFunWantMoney() {
        every { mother.giveMoney() } returns 30
        kid.wantMoney()
        assertEquals(30, kid.money)

        verify { mother.inform(any()) }
    }

    @Test
    fun testMockkExactlyWantMoney() {
        every { mother.giveMoney() } returns 30
        kid.wantMoney()
        assertEquals(30, kid.money)
        kid.wantMoney()
        assertEquals(60, kid.money)

        verify(exactly = 2) { mother.inform(any()) }
    }

    @Test
    fun testMockkVerifyWantMoney() {
        every { mother.giveMoney() } returns 30
        kid.wantMoney()
        assertEquals(30, kid.money)
        //較嚴謹,需對應下個呼叫方法否則失敗
        verifySequence {
            mother.inform(any())
            mother.giveMoney()
        }
        //較寬鬆,方法順序對即可
        verifyOrder {
            mother.inform(any())
            mother.giveMoney()
        }
    }

    @Test
    fun testMockkCapture() {
        val slot = slot<Int>()
        every { mother.giveMoney() } returns 100
        every { mother.inform(capture(slot)) } just Runs

        kid.wantMoney()
        println("slot.captured:${slot.captured}")
        assertEquals(0, slot.captured)
    }

    @Test
    fun ok() {
        val util = Util()
        mockkStatic(UtilJava::class)
//        mockkStatic(UtilKotlin::class)
        mockkObject(UtilKotlin)
        every { UtilJava.ok() } returns "UtilJava:ok()"
        every { UtilKotlin.ok() } returns "UtilKotlin:ok()"

        util.ok()

        assertEquals("UtilJava:ok()", UtilJava.ok())
        assertEquals("UtilKotlin:ok()", UtilKotlin.ok())

        verify { UtilJava.ok() }
        verify { UtilKotlin.ok() }
    }
}