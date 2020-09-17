package com.bird.mm.util

import android.R
import android.app.Activity
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import java.util.*

/**
 *
 * val url = "http://www.yhdm.tv/v/4992-10.html"
    val url = "https://y.qq.com/n/yqq/song/000B4ijs4Ufwql.html"
    ParseWebUrlHelper.init(activity,url)
    ParseWebUrlHelper.setOnParseListener(object : ParseWebUrlHelper.OnParseWebUrlListener{
    override fun onFindUrl(url: String?) {
        Timber.i("url : $url")
    }

    override fun onError(errorMsg: String?) {
        Timber.i("errorMsg : $errorMsg")
    }
    })

    ParseWebUrlHelper.startParse()
 */
object ParseWebUrlHelper {
    private var webUrl: String? = null
    private var mAct: Activity? = null
    private var webView: WebView? = null
    private val timeOut = 20 * 1000L
    private var onParseListener: OnParseWebUrlListener? = null
    fun init(act: Activity?, url: String?): ParseWebUrlHelper {
        mAct = act
        webUrl = url
        val mainView =
            mAct!!.findViewById<View>(R.id.content) as ViewGroup
        webView = WebView(mAct)
        webView!!.layoutParams = LinearLayout.LayoutParams(1, 1)
        mainView.addView(webView)
        initWebSettings()
        return this
    }

    fun initWebSettings() {
        val mWebView = webView
        mWebView!!.clearFocus()
        val mWebSettings = mWebView.settings
        mWebSettings.javaScriptEnabled = true
        mWebSettings.defaultTextEncodingName = "utf-8"
        mWebSettings.cacheMode = WebSettings.LOAD_DEFAULT
        mWebSettings.pluginState = WebSettings.PluginState.ON
        mWebSettings.displayZoomControls = false
        mWebSettings.useWideViewPort = true
        mWebSettings.allowFileAccess = true
        mWebSettings.allowContentAccess = true
        mWebSettings.setSupportZoom(true)
        mWebSettings.allowContentAccess = true
        mWebSettings.loadWithOverviewMode = true
        mWebSettings.builtInZoomControls = true // 隐藏缩放按钮
        mWebSettings.useWideViewPort = true // 可任意比例缩放
        // setUseWideViewPort方法设置webview推荐使用的窗口。
        // setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        mWebSettings.loadWithOverviewMode = true
        mWebSettings.savePassword = true
        mWebSettings.saveFormData = true // 保存表单数据
        mWebSettings.javaScriptEnabled = true
        mWebSettings.textZoom = 100
        mWebSettings.domStorageEnabled = true
        mWebSettings.setSupportMultipleWindows(true) // 新加//我就是没有这一行，死活不出来。MD，硬是没有人写这一句！
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mWebSettings.mediaPlaybackRequiresUserGesture = true
        }
        if (Build.VERSION.SDK_INT >= 16) {
            mWebSettings.allowFileAccessFromFileURLs = true
            mWebSettings.allowUniversalAccessFromFileURLs = true
        }
        mWebSettings.javaScriptCanOpenWindowsAutomatically = true
        mWebSettings.loadsImagesAutomatically = true
        mWebSettings.setAppCacheEnabled(true)
        mWebSettings.setAppCachePath(mAct!!.cacheDir.absolutePath)
        mWebSettings.databaseEnabled = true
        mWebSettings.setGeolocationDatabasePath(mAct!!.getDir("database", 0).path)
        mWebSettings.setGeolocationEnabled(true)
        val instance: CookieManager = CookieManager.getInstance()
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(mAct!!.applicationContext)
        }
        instance.setAcceptCookie(true)
        if (Build.VERSION.SDK_INT >= 21) {
            instance.setAcceptThirdPartyCookies(mWebView, true)
        }
        mWebView.webViewClient = MyWebViewClient()
        enabledCookie(webView) //启用cookie
    }

    fun setLoadUrl(url: String?): ParseWebUrlHelper {
        webUrl = url
        return this
    }

    fun startParse(): ParseWebUrlHelper {
        webView!!.loadUrl(webUrl)
        return this
    }

    /*启用cookie*/
    private fun enabledCookie(web: WebView?) {
        val instance: CookieManager = CookieManager.getInstance()
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(mAct)
        }
        instance.setAcceptCookie(true)
        if (Build.VERSION.SDK_INT >= 21) {
            instance.setAcceptThirdPartyCookies(web, true)
        }
    }

    fun setOnParseListener(onParseListener: OnParseWebUrlListener?): ParseWebUrlHelper {
        this.onParseListener = onParseListener
        return this
    }

    class MyWebViewClient : WebViewClient() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest
        ): Boolean {
            return if (request.url.toString().startsWith("intent") || request.url.toString()
                    .startsWith("youku")
            ) {
                true
            } else {
                super.shouldOverrideUrlLoading(view, request)
            }
        }

        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            return if (url.startsWith("intent") || url.startsWith("youku")) {
                true
            } else {
                super.shouldOverrideUrlLoading(view, url)
            }
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
            onParseListener!!.onFindUrl(url)
            return super.shouldInterceptRequest(view, url)
        }

        override fun shouldInterceptRequest(
            view: WebView,
            request: WebResourceRequest
        ): WebResourceResponse? {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val url = request.url.toString()
                onParseListener!!.onFindUrl(url)
            }
            return super.shouldInterceptRequest(view, request)
        }

        override fun onPageStarted(
            view: WebView,
            url: String,
            favicon: Bitmap?
        ) {
            super.onPageStarted(view, url, favicon)
            startConut() //加载超时处理
        }
    }

    /*解决webview加载超时问题*/
    private fun startConut() {
        val timer = Timer()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                onParseListener!!.onError("解析视频超时，请检查网速或网络是否出现问题...")
                timer.cancel()
                timer.purge()
            }
        }
        timer.schedule(timerTask, timeOut, 1)
    }

    interface OnParseWebUrlListener {
        fun onFindUrl(url: String?)
        fun onError(errorMsg: String?)
    }

//    companion object {
//        private var parseWebUrlHelper: ParseWebUrlHelper? = null
//        val instance: ParseWebUrlHelper?
//            get() {
//                if (parseWebUrlHelper == null) parseWebUrlHelper =
//                    ParseWebUrlHelper()
//                return parseWebUrlHelper
//            }
//    }
}