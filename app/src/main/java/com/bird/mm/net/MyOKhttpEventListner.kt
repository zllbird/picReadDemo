package com.bird.mm.net

import com.bird.mm.db.SchemeDao
import com.bird.mm.vo.HttpEventTime
import com.bird.mm.vo.SchemeItem
import okhttp3.*
import timber.log.Timber
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MyOKhttpEventListner(val schemeDao: SchemeDao) : EventListener() {

    var dnsStart = 0L

    var eventTime: HttpEventTime? = null

    override fun callStart(call: Call) {
        super.callStart(call)
        eventTime = HttpEventTime()
        eventTime?.callStartTime = System.nanoTime()
    }

    override fun callEnd(call: Call) {
        super.callEnd(call)
        eventTime?.callEndTime = System.nanoTime()

//        schemeItem.inetAddressList = eventTime?.inetAddressList
        eventTime?.inetAddressList?.forEach {
            Timber.i("Dns Use Time : ${it.address}")
            Timber.i("Dns Use Time : ${it.hostAddress}")
            Timber.i("Dns Use Time : ${it.hostName}")
            Timber.i("Dns Use Time : ${it.canonicalHostName}")
        }

        schemeDao.insert(SchemeItem(0,"code:", 0, 200).apply {
            schemeUrl = "call ${call.request().url().host()}"
            callTime = -TimeUnit.NANOSECONDS.toMillis(eventTime!!.callStartTime - eventTime!!.callEndTime)
            dnsTime = -TimeUnit.NANOSECONDS.toMillis(eventTime!!.dnsStartTime - eventTime!!.dnsEndTime)
            connectionTime = -TimeUnit.NANOSECONDS.toMillis(eventTime!!.connectionStartTime - eventTime!!.connectionEndTime)
            connectTime = -TimeUnit.NANOSECONDS.toMillis(eventTime!!.connectStartTime - eventTime!!.connectEndTime)
            useTime = callTime
        })
        eventTime = null

    }

    override fun dnsStart(call: Call, domainName: String) {
        super.dnsStart(call, domainName)
        dnsStart = System.nanoTime()
        eventTime?.dnsStartTime = System.nanoTime()
    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: MutableList<InetAddress>) {
        super.dnsEnd(call, domainName, inetAddressList)
        val dur = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - dnsStart)
        Timber.i("Dns Use Time : $dur")
        eventTime?.dnsEndTime = System.nanoTime()
        eventTime?.inetAddressList = inetAddressList
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        super.connectStart(call, inetSocketAddress, proxy)
        Timber.i("connectStart Time ${System.currentTimeMillis()}")
        eventTime?.connectStartTime = System.nanoTime()
    }

    override fun connectEnd(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?
    ) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
        Timber.i("connectEnd Time ${System.currentTimeMillis()}")
        eventTime?.connectEndTime = System.nanoTime()
    }

    override fun connectionAcquired(call: Call, connection: Connection) {
        super.connectionAcquired(call, connection)
        eventTime?.connectionStartTime = System.nanoTime()
    }

    override fun connectionReleased(call: Call, connection: Connection) {
        super.connectionReleased(call, connection)
        eventTime?.connectionEndTime = System.nanoTime()
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