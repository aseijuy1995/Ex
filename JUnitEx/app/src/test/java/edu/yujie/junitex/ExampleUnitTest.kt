package edu.yujie.junitex

import org.hamcrest.Matchers.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.rules.ExternalResource
import org.junit.runners.MethodSorters

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@FixMethodOrder(MethodSorters.DEFAULT)//預設
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)//依據名稱升序排列
//@FixMethodOrder(MethodSorters.JVM)//JVM自行決定

//@RunWith//指定特定Runner類運行
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Before//測試方法前所需執行
    fun before() {
        println("$TAG:before()")
    }

    @Test//測試方法
    fun test() {
        println("$TAG:test()")
    }


    @After///測試方法後所需執行
    fun after() {
        println("$TAG:after()")
    }

    companion object {
        private val TAG = javaClass.simpleName

        @BeforeClass//所有測試方法前所需執行(所有測試方法前單執行一次)
        @JvmStatic
        fun beforeClass() {
            println("$TAG:beforeClass()")
        }

        @AfterClass//所有測試方法後所需執行(所有測試方法後單執行一次)
        @JvmStatic
        fun afterClass() {
            println("$TAG:afterClass()")
        }
    }

    @Ignore//忽略該測試方法
    fun ignore() {
        println("$TAG:ignore()")
    }

//    @Test
//    fun assertion() {
//        assertEquals()
//        assertNotEquals()
//        assertArrayEquals()
//        assertSame()
//        assertNotSame()
//        assertNull()
//        assertNotNull()
//        assertTrue()
//        assertFalse()
//    }


    @Test
    fun hamcrestText() {
        assertThat("JUnit", startsWith("J"))
        assertThat("JUnit", endsWith("t"))
        assertThat("JUnit", containsString("U"))
        assertThat("JUnit", equalTo("JUnit"))
        assertThat("JUnit", equalToIgnoringCase("junit"))//忽略大小寫
        assertThat("JUnit", equalToIgnoringWhiteSpace(" junit "))//忽略空白
    }

    @Test
    fun hamcrestNumber() {
        assertThat(10.6, closeTo(10.5, 0.2))
        assertThat(10.6, greaterThan(10.5))//預設大於
        assertThat(10.6, lessThan(11.0))//預設小於
        assertThat(10.6, lessThanOrEqualTo(10.6))
        assertThat(10.6, greaterThanOrEqualTo(10.6))
    }

    @Test
    fun hamcrestCollection() {
        val map = mapOf<String, String>("a" to "hello", "b" to "world", "c" to "haha")
        assertThat(map, hasEntry("a", "hello"))
        assertThat(map, hasKey("a"))
        assertThat(map, hasValue("hello"))
        val list = listOf<String>("a", "b", "c")
        assertThat(list, hasItem("a"))
        assertThat(list, hasItems("a", "b"))
        val array = arrayOf("a", "b", "c", "d")
        assertThat(array, hasItemInArray("a"))
    }

    @Test
    fun hamcrestObject() {
        assertThat(Object(), notNullValue())
        assertThat(null, nullValue())
        val obj = Object()
        val obj2 = obj
        assertThat(obj, sameInstance(obj2))
        assertThat("abc", instanceOf(String::class.java))
        assertThat(UserInfo(), hasProperty("name"))
    }

    @Test
    fun hamcrestOperator() {
        assertThat(10.4, allOf(greaterThan(10.0), lessThan(10.5), lessThan(10.6)))//全滿足
        assertThat(10.4, anyOf(greaterThan(10.0), lessThan(10.0), lessThan(9.0)))//其一滿足
        assertThat(10.4, both(greaterThan(10.0)).and(lessThan(10.5)))//兩者都滿足
        assertThat(10.4, either(greaterThan(10.0)).or(lessThan(10.0)))//其一滿足
        assertThat(10.4, `is`(10.4))
        assertThat(10.4, isA(Double::class.java))//型別判斷
        assertThat(10.4, not(10.5))
        assertThat(10.4, any(Double::class.java))
        assertThat(10.4, anything())
    }

//    //設置執行時間
//    @Test(timeout = 1000L)
//    fun testTimeOut() {
//        try {
//            sleep(1200)
//        }catch (e:InterruptedException){
//            e.printStackTrace()
//        }
//    }

//    //指定拋出異常
//    @Test(expected = IndexOutOfBoundsException::class)
//    fun expected() = arrayListOf<Objects>().get(0)

//    @Rule // 取得當前方法名
//    val testName  = TestName()
//    @Test
//    fun testName(){
//        testName.methodName
//    }

//    @Rule // 用於測試異常,比expected方便
//    val e = ExpectedException.none()
//    @Test
//    fun exception(){
//        e.expect(NullPointerException::class.java)
//    }

//    @Rule // 測試可方便創建文件
//    val folder = TemporaryFolder()
//    @Test
//    fun createFolder(){
//        folder.newFile()
//    }

//    @Rule // 類似@Before/@After功能
//    val externalResource = object: ExternalResource() {
//        override fun after() {
//            super.after()
//        }
//
//        override fun before() {
//            super.before()
//        }
//    }

}