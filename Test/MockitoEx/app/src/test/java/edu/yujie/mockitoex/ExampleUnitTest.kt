package edu.yujie.mockitoex

import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.quality.Strictness
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testMock() {
        val mutableList: MutableList<String> = mock(MutableList::class.java) as MutableList<String>
        mutableList.add("one")
        mutableList.clear()

        verify(mutableList).add("one")
        verify(mutableList).clear()
    }

    @Test
    fun testMockThrow() {
        val linkedList = mock(LinkedList::class.java)

        `when`(linkedList[0]).thenReturn("first")
        `when`(linkedList[1]).thenThrow(RuntimeException())

        println(linkedList[0])
        println(linkedList[1])
        println(linkedList[99])

        verify(linkedList)[0]
    }

    @Test
    fun testMockArgument() {
        val list = mock(List::class.java)

        `when`(list[anyInt()]).thenReturn("item")

        println(list[100])

        verify(list)[anyInt()]
    }

    @Test
    fun testMockVerifyCount() {
        val mutableList = mock(MutableList::class.java) as MutableList<String>
        mutableList.add("one")
        mutableList.add("two")
        mutableList.add("two")
        mutableList.add("three")
        mutableList.add("three")
        mutableList.add("three")

        verify(mutableList, times(1)).add("one")
        verify(mutableList, times(2)).add("two")
        verify(mutableList, times(3)).add("three")

        verify(mutableList, never()).add("never")

        verify(mutableList, atLeastOnce()).add("one")
        verify(mutableList, atLeast(2)).add("two")
        verify(mutableList, atLeast(3)).add("three")
    }

    @Test
    fun testMockVerifyOrder() {
        val mutableList = mock(MutableList::class.java) as MutableList<String>
        mutableList.add("one")
        mutableList.add("two")

        val inOrder = inOrder(mutableList)
        inOrder.verify(mutableList).add("one")
        inOrder.verify(mutableList).add("two")
    }

    @Test
    fun testMockVerify() {
        val list = mock(List::class.java)
        verifyNoInteractions(list)//驗證mock物件無使用

        val mutableList = mock(MutableList::class.java) as MutableList<String>
        mutableList.add("one")
        mutableList.add("two")
        verify(mutableList).add("one")
        verify(mutableList).add("two")
        verifyNoMoreInteractions(mutableList)//驗證mock物件有無被使用以及是否有未驗證的方法
    }

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @Test
    fun testMockDoThrowWhen() {
        val mutableList = mock(MutableList::class.java) as MutableList<String>
        mutableList.add("123")
        doThrow(RuntimeException()).`when`(mutableList).clear()
        mutableList.clear()
    }

    @Test
    fun testMockDoReturn() {
        val list = mock(List::class.java)
        doReturn("123").`when`(list)[anyInt()]
        println(list[0])
    }

//    @Test
//    fun testMockDoNothingAndDoCallReadMethod(){
//        val foo = mock(Foo::class.java)
//
//        foo.doFoo()
//
//        doNothing().`when`(foo).doFoo()
//        foo.doFoo()
//
//        doCallRealMethod().`when`(foo).doFoo()
//        foo.doFoo()
//
//        println(foo.getCount())
//        doCallRealMethod().`when`(foo).doFoo()
//        println(foo.getCount())
//    }

    @Test
    fun testMockSpy() {
        val mutableList = mock(MutableList::class.java) as MutableList<String>
        val listSpy = spy(mutableList)

        `when`(listSpy.size).thenReturn(0)

        listSpy.add("one")
        listSpy.add("two")

        println(listSpy[0])
        println(listSpy.size)

        verify(listSpy).add("one")
        verify(listSpy).add("two")
    }

//    @Test
//    fun testMockArgumentCaptor(){
//        val mutableList = mock(MutableList::class.java) as MutableList<String>
//        val args = ArgumentCaptor.forClass(String::class.java)
//        mutableList.add("one")
//
//        verify(mutableList).add(args.capture())
//        assertEquals("one", args.value)
//    }

    @Test
    fun testMockReset() {
        val list = mock(List::class.java)
        `when`(list.size).thenReturn(100)
        println(list.size)

        reset(list)
        println(list.size)

    }

}