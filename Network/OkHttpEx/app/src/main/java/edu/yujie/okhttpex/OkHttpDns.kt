package edu.yujie.okhttpex

import okhttp3.Dns
import java.net.InetAddress

object OkHttpDns : Dns {
    override fun lookup(hostname: String): List<InetAddress> {
        return Dns.SYSTEM.lookup(hostname)
    }
}