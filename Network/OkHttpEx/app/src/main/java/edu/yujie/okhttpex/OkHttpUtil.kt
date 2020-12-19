package edu.yujie.okhttpex

import android.content.Context
import android.util.Log
import edu.yujie.okhttpex.interceptor.CacheInterceptor
import edu.yujie.okhttpex.interceptor.TokenHeaderAuthenticator
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

//https://square.github.io/okhttp/

//https://developer.mozilla.org/zh-TW/docs/Web/HTTP/Methods

//Cache
//https://mushuichuan.com/2016/03/01/okhttpcache/
//https://notfalse.net/56/http-stale-response#Cache-Control

//cookieJar
//https://juejin.cn/post/6844904097385021453
//https://juejin.cn/post/6844903646711267336

//eventListener & eventListenerFactory
//https://blog.csdn.net/joye123/article/details/82115562

//certificatePinner - 憑證綁定
//https://tabacowang.me/2018/10/12/okhttp-pinning/

//connectionPool - 連接池
//https://www.jianshu.com/p/d8f4f37b3b3d

//dispatcher - 任務調度器
//https://segmentfault.com/a/1190000020460789

//connectionSpecs - TLS連線規格
//https://www.jianshu.com/p/5ebcd282ea56
//https://juejin.cn/post/6844903983228665870

//dns
//http://www.whdreamblog.cn/2019/04/23/OkHttp%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-DNS%E7%AE%80%E6%9E%90/

//followRedirects & followSslRedirects
//https://www.jianshu.com/p/f2b287a176f5
//https://blog.csdn.net/u012422440/article/details/49914021

//hostnameVerifier
//https://www.itread01.com/p/1329198.html
//https://www.jianshu.com/p/1373889e74b2

//proxy
//https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/738422/#outline__1_2

//webSocket
//https://www.mdeditor.tw/pl/p1DT/zh-tw

class OkHttpUtil private constructor(private val context: Context) {
    private val TAG = javaClass.simpleName
    val client: OkHttpClient

    companion object : SingletonProperty<OkHttpUtil, Context>(::OkHttpUtil)

    val loggerInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            if (BuildConfig.DEBUG) Log.d(TAG, message)
        }
    }).apply { level = HttpLoggingInterceptor.Level.NONE }

    init {
        client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)//連接超時
            .readTimeout(30, TimeUnit.SECONDS)//IO讀取超時
            .writeTimeout(10, TimeUnit.SECONDS)//IO寫入超時
            .callTimeout(60, TimeUnit.SECONDS)//完整請求超時
            .retryOnConnectionFailure(true)//失敗重連
            //Logger
            .addInterceptor(loggerInterceptor)
            /**
             * Cache:透過response.cacheResponse/networkResponse驗證
             * 強制透過網路or緩存可針對request.setCacheControl()設置CacheControl.FORCE_NETWORK || CacheControl.FORCE_CACHE
             * */
            .cache(Cache(context.cacheDir, 10 * 1024L * 1024L))//Header:Cache-Control, max-age=xxx,
            .addNetworkInterceptor(CacheInterceptor)
            //Auth
            .authenticator(TokenHeaderAuthenticator)
            //Interceptors
//            .interceptors()
//            .networkInterceptors()
            //
//            .cookieJar(OkHttpCookieJar)//與web||webView交互時使用

//            .eventListener(PrintingEventListener())
//            .eventListenerFactory(PrintingEventListener.FACTORY)
//            .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
//            .dispatcher(Dispatcher(Executors.newScheduledThreadPool(64)))
//            .certificatePinner(CertificatePinner.Builder().add("", "").build())
            //
//            .connectionSpecs(Collections.singletonList(okHttpConnectionSpec))
//            .dns(OkHttpDns)
//            .followRedirects(false)
//            .followSslRedirects(false)
//            .hostnameVerifier(OkHttpHostnameVerifier)
            //Proxy
//            .proxySelector()
//            .proxy()
//            .proxyAuthenticator()
            //WebSocket
//            .pingInterval()
//            .protocols()
//            .socketFactory()
//            .sslSocketFactory()
//            .minWebSocketMessageToCompress()
            .build()

    }

    fun get(url: String, params: Map<String, String>): Request {
        val builder = url.toHttpUrl().newBuilder()
        params.forEach {
            if (it.key.trim().isNotEmpty() && it.value.trim().isNotEmpty())
                builder.addEncodedQueryParameter(it.key, it.value)
        }
        return Request.Builder().url(builder.build()).get().build()
    }

    fun head(url: String, params: Map<String, String>): Request {
        val builder = url.toHttpUrl().newBuilder()
        params.forEach {
            if (it.key.trim().isNotEmpty() && it.value.trim().isNotEmpty())
                builder.addEncodedQueryParameter(it.key, it.value)
        }
        return Request.Builder().url(builder.build()).head().build()
    }

    fun post(url: String, body: RequestBody) = Request.Builder().url(url).post(body).build()

    fun delete(url: String, body: RequestBody) = Request.Builder().url(url).delete(body).build()

    fun put(url: String, body: RequestBody) = Request.Builder().url(url).put(body).build()

    fun patch(url: String, body: RequestBody) = Request.Builder().url(url).patch(body).build()

    //--------------------------------------------------------------------------------

    //json
    fun jsonToBody(json: String): RequestBody {
        val mediaType = "application/json;charset=utf-8".toMediaType()
        return json.toRequestBody(mediaType)
    }

    //from-data
    fun fromDataToBody(map: Map<String, String>?): RequestBody {
        val builder = FormBody.Builder()
        map?.forEach {
            if (it.key.trim().isNotEmpty() && it.value.trim().isNotEmpty())
                builder.add(it.key, it.value)
        }
        return builder.build()
    }

    //--------------------------------------------------------------------------------

    open fun sync(request: Request): Response = client.newCall(request).execute().run {
        if (!isSuccessful) throw  IOException("$TAG Unexpected code $this")
        this
    }

    fun async(request: Request, callback: Callback) = client.newCall(request).enqueue(callback)

}