package edu.yujie.okhttpex.hostnameVerifier

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

object HostnameVerifierImpl : HostnameVerifier {
    override fun verify(hostname: String?, session: SSLSession?): Boolean = true
}