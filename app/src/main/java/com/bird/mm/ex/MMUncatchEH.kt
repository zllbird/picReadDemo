package com.bird.mm.ex

class MMUncatchEH : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(t: Thread, e: Throwable) {
//        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t,e)
    }

}