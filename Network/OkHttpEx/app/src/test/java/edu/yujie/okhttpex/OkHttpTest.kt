package edu.yujie.okhttpex

import edu.yujie.okhttpex.util.OkHttpUtil
import edu.yujie.okhttpex.util.asyncPostFromData
import junit.framework.TestCase.assertEquals
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

//https://jsonplaceholder.typicode.com/

class OkHttpTest {
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun before() {
        mockWebServer = MockWebServer()
    }

    @Test
    fun test() {
        val response = "{\"applestoreurl\":\"https:\\/\\/play.google.com\\/store\\/apps\\/details?id=com.ytt.lohas\",\"result\":\"1.2.0\"}"
        mockWebServer.enqueue(MockResponse().setBody(response))

        mockWebServer.start()

        val url = "https://www.letsgoshopping.com.tw/ct/api.php"

        val httpUrl = mockWebServer.url(url)
        val map = mapOf("cmd" to "get_version_android")


        OkHttpUtil.get().asyncPostFromData(httpUrl.toUri().toString(), map, object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                println("test1")
                val request: RecordedRequest = mockWebServer.takeRequest()
                println("test12")

                assertEquals(url, request.path)
            }
        })

    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

}