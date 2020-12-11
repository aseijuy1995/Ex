package edu.yujie.okhttpex

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

object OkHttpHostnameVerifier : HostnameVerifier {
    override fun verify(hostname: String?, session: SSLSession?): Boolean = true
}