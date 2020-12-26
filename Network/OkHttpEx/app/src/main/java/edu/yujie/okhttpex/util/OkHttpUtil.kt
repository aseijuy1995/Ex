package edu.yujie.okhttpex.util

import android.content.Context
import android.util.Log
import edu.yujie.okhttpex.BuildConfig
import edu.yujie.okhttpex.SingletonProperty
import edu.yujie.okhttpex.dns.DnsImpl
import edu.yujie.okhttpex.eventListener.PrintingEventListener
import edu.yujie.okhttpex.interceptor.CacheInterceptor
import edu.yujie.okhttpex.interceptor.TokenHeaderAuthenticator
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

//https://github.com/square/okhttp
//https://square.github.io/okhttp/
//https://www.jianshu.com/p/82f74db14a18

//connectionPool - 連接池
//https://www.jianshu.com/p/d8f4f37b3b3d
//https://juejin.cn/post/6844903557632622605#heading-6

//hostnameVerifier
//https://www.itread01.com/p/1329198.html
//https://www.jianshu.com/p/1373889e74b2

//dispatcher - 任務調度器
//https://segmentfault.com/a/1190000020460789
//https://www.mdeditor.tw/pl/pUTC/zh-hk

//certificatePinner - 憑證綁定
//https://tabacowang.me/2018/10/12/okhttp-pinning/

//connectionSpecs - TLS連線規格
//https://www.jianshu.com/p/5ebcd282ea56
//https://juejin.cn/post/6844903983228665870

//followRedirects & followSslRedirects
//https://www.jianshu.com/p/f2b287a176f5
//https://blog.csdn.net/u012422440/article/details/49914021

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

    val connectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
        .tlsVersions(TlsVersion.SSL_3_0)
        .cipherSuites(
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
        ).build()

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
            //pool & dispatchers
//            .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
//            .dispatcher(Dispatcher(Executors.newScheduledThreadPool(64)))
            //
            //憑證 & 證書 & 連線規則 & 協議
//            .certificatePinner(CertificatePinner.Builder().add("localhost", "sha256").build())
//            .hostnameVerifier(HostnameVerifierImpl)
//            .connectionSpecs(Collections.singletonList(connectionSpec))
//            .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
            //Cookie
//            .cookieJar(CookieJarImpl)//app開啟web || webView時使用
            //EventListener
            .eventListenerFactory(PrintingEventListener.FACTORY)
//            .eventListener(PrintingEventListener())
            //Proxy & Dns
//            .proxySelector(ProxySelectorImpl)
//            .proxy(Proxy(Proxy.Type.SOCKS, InetSocketAddress("proxyHost", 8080)))
//            .proxyAuthenticator(ProxyAuthenticator)
            .dns(DnsImpl)
            //重定向處理
//            .followRedirects(false)
//            .followSslRedirects(false)
            //WebSocket
//            .pingInterval(15, TimeUnit.SECONDS)
//            .socketFactory(SocketFactory.getDefault())
//            .sslSocketFactory()
//            .minWebSocketMessageToCompress(2048)
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

    fun request(url: String) = Request.Builder().url(url).build()

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

    fun sync(request: Request): Response = client.newCall(request).execute().run {
        if (!isSuccessful) throw  IOException("$TAG Unexpected code $this")
        this
    }

    fun async(request: Request, callback: Callback) = client.newCall(request).enqueue(callback)

    fun webSocket(request: Request, listener: WebSocketListener) = client.newWebSocket(request, listener)

}