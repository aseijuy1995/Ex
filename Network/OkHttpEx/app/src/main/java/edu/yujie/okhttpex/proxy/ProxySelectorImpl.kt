package edu.yujie.okhttpex.proxy

import java.io.IOException
import java.net.Proxy
import java.net.ProxySelector
import java.net.SocketAddress
import java.net.URI

//https://juejin.cn/post/6844903601291149319

object ProxySelectorImpl: ProxySelector() {
    override fun select(uri: URI?): MutableList<Proxy> {
        TODO("Not yet implemented")
    }

    override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) {
        TODO("Not yet implemented")
    }
}