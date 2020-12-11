package edu.yujie.okhttpex

import okhttp3.*
import okhttp3.EventListener
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.*
import java.util.concurrent.atomic.AtomicLong


class HttpEventListener(private val callId: Long, private val url: HttpUrl, private val callStartNanos: Long) : EventListener() {
    private val TAG = javaClass.simpleName
    private val sbLog: StringBuilder? = null

    companion object {
        val FACTORY = object : EventListener.Factory {
            private val atomLong = AtomicLong(1)
            override fun create(call: Call): EventListener {
                val callId = atomLong.getAndIncrement()
                return HttpEventListener(callId, call.request().url, System.nanoTime())
            }
        }
    }

    private fun recordEventLog(name: String) {
        val elapseNanos: Long = System.nanoTime() - callStartNanos
        sbLog?.append(java.lang.String.format(Locale.TAIWAN, "%.3f-%s", elapseNanos / 1000000000.0, name))?.append(";")
        if (name.equals("callEnd", ignoreCase = true) || name.equals("callFailed", ignoreCase = true)) {
            //打印出每个步骤的时间点
            println(sbLog.toString())
        }
    }

    override fun callStart(call: Call) {
        super.callStart(call)
        recordEventLog("$TAG callStart()")
    }

    override fun dnsStart(call: Call, domainName: String) {
        super.dnsStart(call, domainName)
        recordEventLog("$TAG dnsStart()")
    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
        super.dnsEnd(call, domainName, inetAddressList)
        recordEventLog("$TAG dnsEnd()")
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        super.connectStart(call, inetSocketAddress, proxy)
        recordEventLog("$TAG connectStart()")
    }

    override fun secureConnectStart(call: Call) {
        super.secureConnectStart(call)
        recordEventLog("$TAG secureConnectStart()")
    }

    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        super.secureConnectEnd(call, handshake)
        recordEventLog("$TAG secureConnectEnd()")
    }

    override fun connectEnd(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
        recordEventLog("$TAG connectEnd()")
    }

    override fun connectFailed(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?, ioe: IOException) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe)
        recordEventLog("$TAG connectFailed()")
    }

    override fun connectionAcquired(call: Call, connection: Connection) {
        super.connectionAcquired(call, connection)
        recordEventLog("$TAG connectionAcquired()")
    }

    override fun connectionReleased(call: Call, connection: Connection) {
        super.connectionReleased(call, connection)
        recordEventLog("$TAG connectionReleased()")
    }

    override fun requestHeadersStart(call: Call) {
        super.requestHeadersStart(call)
        recordEventLog("$TAG requestHeadersStart()")
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        super.requestHeadersEnd(call, request)
        recordEventLog("$TAG requestHeadersEnd()")
    }

    override fun requestBodyStart(call: Call) {
        super.requestBodyStart(call)
        recordEventLog("$TAG requestBodyStart()")
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        super.requestBodyEnd(call, byteCount)
        recordEventLog("$TAG requestBodyEnd()")
    }

    override fun responseHeadersStart(call: Call) {
        super.responseHeadersStart(call)
        recordEventLog("$TAG responseHeadersStart()")
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        super.responseHeadersEnd(call, response)
        recordEventLog("$TAG responseHeadersEnd()")
    }

    override fun responseBodyStart(call: Call) {
        super.responseBodyStart(call)
        recordEventLog("$TAG responseBodyStart()")
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        super.responseBodyEnd(call, byteCount)
        recordEventLog("$TAG responseBodyEnd()")
    }

    override fun callEnd(call: Call) {
        super.callEnd(call)
        recordEventLog("$TAG callEnd()")
    }

    override fun callFailed(call: Call, ioe: IOException) {
        super.callFailed(call, ioe)
        recordEventLog("$TAG callFailed()")
    }


}