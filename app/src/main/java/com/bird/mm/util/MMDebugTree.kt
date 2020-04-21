package com.bird.mm.util

import timber.log.Timber

class MMDebugTree : Timber.DebugTree(){

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        var newTag = tag
        if (tag != null) {
            val threadName = Thread.currentThread().name
            newTag = "MMLog:<$threadName> $tag"
        }
        super.log(priority, newTag, message, t)
    }

    override fun createStackElementTag(element: StackTraceElement): String? {
        return super.createStackElementTag(element)+"(Line " + element.lineNumber + ")";
    }

}