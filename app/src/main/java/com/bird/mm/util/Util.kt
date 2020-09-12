package com.bird.mm.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

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

    }


}