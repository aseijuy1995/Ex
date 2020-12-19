package edu.yujie.okhttpex

import okhttp3.*
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.atomic.AtomicLong


class PrintingEventListener : EventListener() {
    private val TAG = javaClass.simpleName
    private var callStartNanos: Long? = null

    private val sb: StringBuilder? = null

    companion object {
        val FACTORY = object : Factory {
            private val atomLong = AtomicLong(1L)
            override fun create(call: Call): EventListener {
                return PrintingEventListener()
            }
        }
    }

    private fun printEvent(name: String) {
        val nowNanos: Long = System.nanoTime()
        if (name == "callStart") callStartNanos = nowNanos
        val elapsedNanos = nowNanos - (callStartNanos ?: 0)
        String.format("$TAG %.3f %s%n", elapsedNanos / 1000000000.0, name)
    }

    override fun callStart(call: Call) {
        super.callStart(call)
        printEvent("callStart()")
    }

    override fun dnsStart(call: Call, domainName: String) {
        super.dnsStart(call, domainName)
        printEvent("dnsStart()")
    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
        super.dnsEnd(call, domainName, inetAddressList)
        printEvent("dnsEnd()")
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        super.connectStart(call, inetSocketAddress, proxy)
        printEvent("connectStart()")
    }

    override fun secureConnectStart(call: Call) {
        super.secureConnectStart(call)
        printEvent("secureConnectStart()")
    }

    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        super.secureConnectEnd(call, handshake)
        printEvent("secureConnectEnd()")
    }

    override fun connectEnd(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
        printEvent("connectEnd()")
    }

    override fun connectionAcquired(call: Call, connection: Connection) {
        super.connectionAcquired(call, connection)
        printEvent("connectionAcquired()")
    }

    override fun connectFailed(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?, ioe: IOException) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe)
        printEvent("connectFailed()")
    }

    override fun requestHeadersStart(call: Call) {
        super.requestHeadersStart(call)
        printEvent("requestHeadersStart()")
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        super.requestHeadersEnd(call, request)
        printEvent("requestHeadersEnd()")
    }

    override fun requestBodyStart(call: Call) {
        super.requestBodyStart(call)
        printEvent("requestBodyStart()")
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        super.requestBodyEnd(call, byteCount)
        printEvent("requestBodyEnd()")
    }

    override fun responseHeadersStart(call: Call) {
        super.responseHeadersStart(call)
        printEvent("responseHeadersStart()")
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        super.responseHeadersEnd(call, response)
        printEvent("responseHeadersEnd()")
    }

    override fun responseBodyStart(call: Call) {
        super.responseBodyStart(call)
        printEvent("responseBodyStart()")
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        super.responseBodyEnd(call, byteCount)
        printEvent("responseBodyEnd()")
    }

    override fun connectionReleased(call: Call, connection: Connection) {
        super.connectionReleased(call, connection)
        printEvent("connectionReleased()")
    }

    override fun callEnd(call: Call) {
        super.callEnd(call)
        printEvent("callEnd()")
    }

    override fun callFailed(call: Call, ioe: IOException) {
        super.callFailed(call, ioe)
        printEvent("callFailed()")
    }


}