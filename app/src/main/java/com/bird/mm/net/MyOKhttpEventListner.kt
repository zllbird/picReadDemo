package com.bird.mm.net

import okhttp3.*
import timber.log.Timber
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit


class MyOKhttpEventListner : EventListener() {

    var dnsStart = 0L

    override fun callStart(call: Call) {
        super.callStart(call)
    }

    override fun callEnd(call: Call) {
        super.callEnd(call)
    }

    override fun dnsStart(call: Call, domainName: String) {
        super.dnsStart(call, domainName)
        dnsStart = System.nanoTime()
    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: MutableList<InetAddress>) {
        super.dnsEnd(call, domainName, inetAddressList)
        val dur = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - dnsStart)
        Timber.i("Dns Use Time : $dur")
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        super.connectStart(call, inetSocketAddress, proxy)
        Timber.i("connectStart Time ${System.currentTimeMillis()}")
    }

    override fun connectEnd(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?
    ) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
        Timber.i("connectEnd Time ${System.currentTimeMillis()}")
    }

    override fun requestHeadersStart(call: Call) {
        super.requestHeadersStart(call)
        Timber.i("requestHeadersStart Time ${System.currentTimeMillis()}")
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        super.requestBodyEnd(call, byteCount)
        Timber.i("requestBodyEnd Time ${System.currentTimeMillis()}")
    }

    override fun requestBodyStart(call: Call) {
        super.requestBodyStart(call)
        Timber.i("requestBodyStart Time ${System.currentTimeMillis()}")
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        super.requestHeadersEnd(call, request)
        Timber.i("requestHeadersEnd Time ${System.currentTimeMillis()}")
    }

    override fun responseBodyStart(call: Call) {
        super.responseBodyStart(call)
        Timber.i("responseBodyStart Time ${System.currentTimeMillis()}")
    }

    override fun responseHeadersStart(call: Call) {
        super.responseHeadersStart(call)
        Timber.i("responseHeadersStart Time ${System.currentTimeMillis()}")
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        super.responseBodyEnd(call, byteCount)
        Timber.i("responseBodyEnd Time ${System.currentTimeMillis()}")
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        super.responseHeadersEnd(call, response)
        Timber.i("responseHeadersEnd Time ${System.currentTimeMillis()}")
    }

    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        super.secureConnectEnd(call, handshake)
        Timber.i("secureConnectEnd Time ${System.currentTimeMillis()}")
    }



}