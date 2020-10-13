package com.bird.mm.vo

import java.net.InetAddress

data class AliTest(
    val appId: String,
    val appVersion: String,
    val exposureRecordList: List<Any>,
    val os: String,
    val sdkVersion: String,
    val stickerId: String
)

class HttpEventTime(){
    var callStartTime : Long = 0
    var callEndTime : Long = 0

    var dnsStartTime : Long = 0
    var dnsEndTime : Long = 0

    var connectStartTime : Long = 0
    var connectEndTime : Long = 0

    var connectionStartTime : Long = 0
    var connectionEndTime : Long = 0

    var inetAddressList: MutableList<InetAddress>? = null
}