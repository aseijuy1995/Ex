package edu.yujie.okhttpex.dns

import okhttp3.Dns
import java.net.InetAddress

//https://segmentfault.com/a/1190000020460789
//https://juejin.cn/post/6844903806216617992

object DnsImpl : Dns {
    override fun lookup(hostname: String): List<InetAddress> {
        println("OkHttpUtil hostname = $hostname")
        if (hostname.contains("localhost")) {
            val inetAddresses = mutableListOf<InetAddress>()
            inetAddresses.add(InetAddress.getByAddress(byteArrayOf(10, 0, 2, 2)))
            return inetAddresses
        }
        return Dns.SYSTEM.lookup(hostname)
    }
}