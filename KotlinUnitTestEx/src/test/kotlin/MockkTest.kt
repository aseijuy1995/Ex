import io.mockk.MockKAnnotations
import io.mockk.excludeRecords
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class MockkTest {

    @MockK
    lateinit var mockkMain: MockkMain

    @MockK(relaxed = true)
    lateinit var mockkMain2: MockkMain

    @MockK(relaxUnitFun = true)
    lateinit var mockkMain3: MockkMain

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
    }

    @Test
    fun mockCategoryTest() {
//        val mockMain = mockk<MockkMain>()
//        every { mockMain.funcReturnString() }.returns("A")
//        every { mockMain.funcReturnInt() }.returns(1)
//        every { mockMain.funcReturnUnit() }.returns(Unit)
//
//        val s = mockMain.funcReturnString()
//        val i = mockMain.funcReturnInt()
//        val u = mockMain.funcReturnUnit()

        //--------------------------------------------------------------------------------

//        //relaxed - return default behavior
//        val mockMain2 = mockk<MockkMain>(relaxed = true)
//
//        val s2 = mockMain2.funcReturnString()
//        val i2 = mockMain2.funcReturnInt()
//        val u2 = mockMain2.funcReturnUnit()

        //--------------------------------------------------------------------------------

//        //relaxUnitFun - return Unit with function
//        val mockMain3 = mockk<MockkMain>(relaxUnitFun = true)
//        mockMain3.funcReturnUnit()

    }

    @Test
    fun mockAnnotationTest() {
//        every { mockkMain.funcReturnString() }.returns("A")
//        every { mockkMain.funcReturnInt() }.returns(1)
//        every { mockkMain.funcReturnUnit() }.returns(Unit)
//
//        val s = mockkMain.funcReturnString()
//        val i = mockkMain.funcReturnInt()
//        val u = mockkMain.funcReturnUnit()

        //--------------------------------------------------------------------------------

//        every { mockkMain2.funcReturnString() }.returns("A")
//        every { mockkMain2.funcReturnInt() }.returns(1)
//        every { mockkMain2.funcReturnUnit() }.returns(Unit)
//
//        val s2 = mockkMain2.funcReturnString()
//        val i2 = mockkMain2.funcReturnInt()
//        val u2 = mockkMain2.funcReturnUnit()

        //--------------------------------------------------------------------------------

//        every { mockkMain3.funcReturnUnit() }.returns(Unit)
//        val u3 = mockkMain3.funcReturnUnit()
    }

    @Test
    fun mockEnumTest() {
//        mockkObject((MockkMain.MockEnum.FIRST))
//        mockkObject((MockkMain.MockEnum.SECOND))
//        mockkObject((MockkMain.MockEnum.THIRD))
//        mockkObject((MockkMain.MockEnum.FOURTH))
//
//        every { MockkMain.MockEnum.FIRST.number }.returns(1)
//        every { MockkMain.MockEnum.SECOND.number }.returns(2)
//        every { MockkMain.MockEnum.THIRD.number }.returns(3)
//        every { MockkMain.MockEnum.FOURTH.number }.returns(4)
//
//        assertEquals(1, MockkMain.MockEnum.FIRST.number)
//        assertEquals(2, MockkMain.MockEnum.SECOND.number)
//        assertEquals(3, MockkMain.MockEnum.THIRD.number)
//        assertEquals(4, MockkMain.MockEnum.FOURTH.number)
    }

    @Test
    fun mockVararyTest() {
//        //varargAllInt - 可以傳入多個固定的值
//        every {
//            mockkMain.funcVararg(1, 2, *varargAllInt { it == 5 })
//        }.returns(1)
//
//        //anyIntVararg - 可以傳入任何值
//        every {
//            mockkMain.funcVararg(1, 2, *anyIntVararg(), 5)
//        }.returns(2)
//
//        //anyIntVararg - 可以傳入條件內的植
//        every {
//            mockkMain.funcVararg(1, 2, *varargAnyInt { nArgs in 2..6 }, 6)
//        }.returns(3)
    }

    @Test
    fun mockCaptureTest() {
//        val slot = slot<String>()
//        every {
//            mockkMain.funcSetString(capture(slot))
//        } just runs
//        mockkMain.funcSetString("A")
//        assertEquals("A", slot.captured)
    }

    @Test
    fun mockValidatorsTest() {
//        every { mockkMain.funcReturnUnit2() } just runs
//        mockkMain.funcReturnUnit2()
//
//        //exactly - 指定呼叫幾次
//        verify(exactly = 1) { mockkMain.funcReturnUnit2() }
//
//        //atLeast - 最少呼叫幾次
//        verify(atLeast = 1) { mockkMain.funcReturnUnit2() }
//
//        //atMost - 最多呼叫幾次
//        verify(atMost = 1) { mockkMain.funcReturnUnit2() }
    }

    @Test
    fun mockVerifySequenceTest() {
//        //mockkMain2 - relaxed = true
//        mockkMain2.funcReturnUnit2()
//        mockkMain2.funcReturnUnit3()
//        mockkMain2.funcReturnUnit4()
//
//        //verifySequence - 呼叫順序是否完全一致
//        verifySequence {
//            mockkMain2.funcReturnUnit2()
//            mockkMain2.funcReturnUnit3()
//            mockkMain2.funcReturnUnit4()
//        }
    }

    @Test
    fun mockVerifyOrderTest() {
//        //mockkMain2 - relaxed = true
//        mockkMain2.funcReturnUnit2()
//        mockkMain2.funcReturnUnit3()
//        mockkMain2.funcReturnUnit4()
//        //verifyOrder - 順序是否正確,即可
//        verifyOrder {
//            mockkMain2.funcReturnUnit2()
//            mockkMain2.funcReturnUnit4()
//        }
    }

    @Test
    fun mockTimeOutTest() {
        //mockkMain2 - relaxed = true
        thread {
            sleep(2000)
            mockkMain2.funcReturnUnit2()
        }
        //timeout - 不可超過指定執行時間
        verify(timeout = 3000) {
            mockkMain2.funcReturnUnit2()
        }
    }

    @Test
    fun mockMatchersTest() {
//        mockkMain2.funcSetString("A")
//        //eq - 驗證參數
//        verify { mockkMain2.funcSetString(eq("A")) }
//        verify { mockkMain2.funcSetString(any()) }

        //--------------------------------------------------------------------------------

//        mockkMain2.funcSetInt(1)
//        mockkMain2.funcSetInt(101)
//        //range - 範圍間
//        //less - 小於
//        //more - 大於
//        verifyAll {
//            mockkMain2.funcSetInt(range(1, 100))
//            mockkMain2.funcSetInt(less(10))
//            mockkMain2.funcSetInt(more(100))
//        }
    }

    @Test
    fun mockExcludeRecordsTest() {
        //excludeRecords - 指定於verify中不可呼叫到excludeRecords中指定方法
        excludeRecords { mockkMain2.funcReturnUnit() }
        mockkMain2.funcReturnUnit()
        mockkMain2.funcReturnUnit2()
        mockkMain2.funcReturnUnit3()

        verify {
            mockkMain2.funcReturnUnit2()
            mockkMain2.funcReturnUnit3()
        }


    }

    //--------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------


}