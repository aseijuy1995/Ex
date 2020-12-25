package edu.yujie.okhttpex

import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class OkHttpTest {
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun before() {
        mockWebServer = MockWebServer()
    }

    @Test
    fun test() {
//        mockWebServer.enqueue(MockResponse().setBody("Hello World!"))
//        mockWebServer.enqueue(MockResponse().setBody("Sup,Cra?"))
//        mockWebServer.enqueue(MockResponse().setBody("Yu Dog"))
        mockWebServer.enqueue(MockResponse().setBody("{\"applestoreurl\":\"https:\\/\\/play.google.com\\/store\\/apps\\/details?id=com.ytt.lohas\",\"result\":\"1.2.0\"}"))
        mockWebServer.start()
        mockWebServer.url("https://www.letsgoshopping.com.tw/ct/api.php")
//        mockWebServer.url("/v1/chat/")

        val request = mockWebServer.takeRequest()
//        assertEquals("POST https://www.letsgoshopping.com.tw/ct/api.php",request.requestLine)
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

}