package com.bird.mm.util

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.webkit.*
import androidx.annotation.RequiresApi
import timber.log.Timber

class ParseWebViewClient(private var onFindUrl : ((String)->Unit)?) : WebViewClient(){

//    var onFindUrl : ((String)->Unit)? = null

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(
        view: WebView,
        request: WebResourceRequest
    ): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String
    ): Boolean {
        return super.shouldOverrideUrlLoading(view, url)
    }

    /*解决ssl证书问题*/
    override fun onReceivedSslError(
        view: WebView,
        handler: SslErrorHandler,
        error: SslError
    ) {
        handler.proceed()
    }

    override fun shouldInterceptRequest(
        view: WebView,
        url: String
    ): WebResourceResponse? {
//        ParseWebUrlHelper.onParseListener!!.onFindUrl(url)
//        findUrl(url)
        return super.shouldInterceptRequest(view, url)
    }

    fun findUrl(url: String){
        Timber.i("findUrl url $url")
        if (
            url.contains(".mp4")
            || url.contains(".hls")
            || url.contains(".m3u8")
            || url.contains(".m4a")
        ){
            onFindUrl?.let {
                it(url)
            }
        }
    }

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val url = request.url.toString()
//            ParseWebUrlHelper.onParseListener!!.onFindUrl(url)
            findUrl(url)
        }
        return super.shouldInterceptRequest(view, request)
    }

    override fun onPageStarted(
        view: WebView,
        url: String,
        favicon: Bitmap?
    ) {
        super.onPageStarted(view, url, favicon)
//        ParseWebUrlHelper.startConut() //加载超时处理
    }
}