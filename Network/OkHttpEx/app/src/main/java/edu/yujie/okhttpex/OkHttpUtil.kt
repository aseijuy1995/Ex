package edu.yujie.okhttpex

import android.content.Context
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

//https://square.github.io/okhttp/

//Cache
//https://notfalse.net/56/http-stale-response#Cache-Control
//https://mushuichuan.com/2016/03/01/okhttpcache/

//retryOnConnectionFailure
//https://blog.csdn.net/zhangteng22/article/details/52237839

//authenticator
//https://juejin.cn/post/6844903455794921486

//certificatePinner - 憑證綁定
//https://tabacowang.me/2018/10/12/okhttp-pinning/

//connectionPool
//https://www.jianshu.com/p/d8f4f37b3b3d

//connectionSpecs
//https://www.jianshu.com/p/5ebcd282ea56
//https://juejin.cn/post/6844903983228665870

//cookieJar
//https://juejin.cn/post/6844904097385021453
//https://juejin.cn/post/6844903646711267336

//dispatcher
//https://segmentfault.com/a/1190000020460789

//dns
//http://www.whdreamblog.cn/2019/04/23/OkHttp%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-DNS%E7%AE%80%E6%9E%90/

//eventListener
//https://blog.csdn.net/joye123/article/details/82115562

//followRedirects || followSslRedirects
//https://www.jianshu.com/p/f2b287a176f5
//https://blog.csdn.net/u012422440/article/details/49914021

//hostnameVerifier
//https://www.itread01.com/p/1329198.html
//https://www.jianshu.com/p/1373889e74b2

//addNetworkInterceptor
//https://github.com/AndroidPreView/AndroidNote/wiki/Okhttp-%E7%9A%84addInterceptor-%E5%92%8C-addNetworkInterceptor-%E7%9A%84%E5%8C%BA%E5%88%AB%EF%BC%9F
//https://blog.csdn.net/OneDeveloper/article/details/88381817

//proxy
//https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/738422/#outline__1_2

//webSocket
//https://www.mdeditor.tw/pl/p1DT/zh-tw

class OkHttpUtil private constructor(private val context: Context) {

    companion object : SingletonProperty<OkHttpUtil, Context>(::OkHttpUtil)

    init {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
//            .addInterceptor(CacheInterceptor)
//            .cache(Cache(context.cacheDir, 1024 * 1024 * 10))//Header:Cache-Control, max-age=xxx,
//            .authenticator(TokenHeaderAuthenticator)
//            .authenticator(TokenUrlAuthenticator)

//            .interceptors()
//            .addNetworkInterceptor()
//            .networkInterceptors()
//            .certificatePinner(CertificatePinner.Builder().add("", "").build())
//            .connectionPool()
//            .connectionSpecs(Collections.singletonList(okHttpConnectionSpec))
//            .cookieJar(MyCookieJar)
//            .dispatcher(Dispatcher(Executors.newScheduledThreadPool(64)))
//            .dns(OkHttpDns)
//            .eventListener(HttpEventListener())
//            .eventListenerFactory(HttpEventListener.FACTORY)
//            .followRedirects(false)
//            .followSslRedirects(false)
//            .hostnameVerifier(OkHttpHostnameVerifier)
//            .proxy()
//            .proxyAuthenticator()
//            .proxySelector()
//            .protocols()

            .build()
//            .minWebSocketMessageToCompress()
//            .pingInterval()
//            .socketFactory()
//            .sslSocketFactory()

    }

}