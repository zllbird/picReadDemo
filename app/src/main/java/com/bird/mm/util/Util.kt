package com.bird.mm.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import java.io.File

class Util {

    companion object{
        fun saveToClipboard(txt: String,context: Context) {
            val clipboard =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("Copied Text", txt))
            Toast.makeText(context, "已复制: $txt", Toast.LENGTH_SHORT).show()
        }

        fun checkIsVideo(url:String?) : Boolean{
            url?.let {
                return url.contains(".mp4") || url.contains(".flv") || url.contains(".avi")
            }
            return false
        }

        fun initWebSettings(mWebView : WebView,onFindUrl : ((String)->Unit)?) {
//            val mWebView = ParseWebUrlHelper.webView
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
            mWebSettings.setAppCachePath(mWebView.context.cacheDir.absolutePath)
            mWebSettings.databaseEnabled = true
//            mWebSettings.setGeolocationDatabasePath(ParseWebUrlHelper.mAct!!.getDir("database", 0).path)
            mWebSettings.setGeolocationEnabled(true)
            val instance: CookieManager = CookieManager.getInstance()
            instance.setAcceptCookie(true)
            if (Build.VERSION.SDK_INT >= 21) {
                instance.setAcceptThirdPartyCookies(mWebView, true)
            }
            mWebView.webViewClient = ParseWebViewClient(onFindUrl)
        }

        /*启用cookie*/
        private fun enabledCookie(web: WebView?) {
            val instance: CookieManager = CookieManager.getInstance()
//            if (Build.VERSION.SDK_INT < 21) {
//                CookieSyncManager.createInstance(ParseWebUrlHelper.mAct)
//            }
            instance.setAcceptCookie(true)
            if (Build.VERSION.SDK_INT >= 21) {
                instance.setAcceptThirdPartyCookies(web, true)
            }
        }


        fun sendFlushFileChanged(file: File, context: Context){
            val contentUri = Uri.fromFile(file)
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri)
            context.sendBroadcast(mediaScanIntent)
        }
    }


}