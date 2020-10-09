package com.bird.mm.vo

data class AliTest(
    val appId: String,
    val appVersion: String,
    val exposureRecordList: List<Any>,
    val os: String,
    val sdkVersion: String,
    val stickerId: String
)