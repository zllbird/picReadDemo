package com.bird.mm.util

import androidx.lifecycle.LiveData
import com.bird.mm.vo.Girl
import org.jsoup.Jsoup
import timber.log.Timber

class XML2List {

    companion object {
        fun xml2Model(xmlString: String): List<Girl> {
//        val result = String(xmlString.toByteArray(), charset("gb2312"))
            val doc = Jsoup.parse(xmlString)
            var box = doc.select("div.MeinvTuPianBox").first()
            val imgs = box.select("li a")
            return imgs.map {
                val link = it.attr("href")
//            val title = String(it.attr("title").toByteArray(), charset("gb2312"))
                val title = it.attr("title")
                val img = it.select("i img").first()
                var src = img?.attr("src") ?: ""
                Timber.i("img $src")
                Girl(title, src, link)
            }.filter {
                !it.cover.isNullOrEmpty()
            }
        }

        fun xml2BGModel(xmlString: String): List<Girl> {
//        val result = String(xmlString.toByteArray(), charset("gb2312"))
            val doc = Jsoup.parse(xmlString)
            var box = doc.select("div.w1200 ul.w110").first()
            val imgs = box.select("li a")
            return imgs.map {
                val link = it.attr("href")
//            val title = String(it.attr("title").toByteArray(), charset("gb2312"))
                val title = it.attr("title")
                val img = it.select("img").first()
                var src = img?.attr("src") ?: ""
                Timber.i("img $src")
                Girl(title, src, link)
            }.filter {
                !it.cover.isNullOrEmpty()
            }
        }

         fun xml2DetailModel(xmlString:String): List<String> {
            val doc = Jsoup.parse(xmlString)
            var img = doc.select("div#picBody a img").first()
            var src= img?.attr("src")  ?: ""
            val list = arrayListOf<String>()
//        val indexpre = src.indexOfLast { it.equals("/") }
//        val index = src.indexOfLast { it.equals(".") }
            repeat(10){
                val cache = src.replace("1.jpg", "${it+1}.jpg")
                list.add(cache)
            }
            return list
        }

        fun xml2PageDetailModel(xmlString:String) :String {
            val doc = Jsoup.parse(xmlString)
            var img = doc.select("div#picBody a img").first()
            var src= img?.attr("src")  ?: ""
            return src
        }

        fun xml2TDDetailModel(body: String): String {
            val doc = Jsoup.parse(body)
            var img = doc.select("div p a img").first()
            var src = img?.attr("src") ?: ""
//        val list = arrayListOf<String>()
//        list.add
            return src
        }

        fun xml2MTWModel(xmlString: String): List<Girl> {
//        val result = String(xmlString.toByteArray(), charset("gb2312"))
            val doc = Jsoup.parse(xmlString)
//        var box = doc.select("div.MeinvTuPianBox").first()
            val imgs = doc.select("li div a")
            return imgs.map {
                val link = it.attr("href")
//            val title = String(it.attr("title").toByteArray(), charset("gb2312"))
                val title = it.attr("title")
                val img = it.select("img").first()
                var src = img?.attr("src") ?: ""
                Timber.i("img $src")
                Girl(title, src, link)
            }.filter {
                !it.cover.isNullOrEmpty()
            }
        }

        fun xml2TDModel(xmlString: String): List<Girl> {
//        val result = String(xmlString.toByteArray(), charset("gb2312"))
            val doc = Jsoup.parse(xmlString)
//        var box = doc.select("div.MeinvTuPianBox").first()
            val imgs = doc.select("li a")
            return imgs.map {
                val link = it.attr("href")
//            val title = String(it.attr("title").toByteArray(), charset("gb2312"))
                val title = it.attr("title")
                val img = it.select("img").first()
                var src = img?.attr("src") ?: ""
                Timber.i("img $src")
                Girl(title, src, link)
            }.filter {
                !it.cover.isNullOrEmpty()
            }
        }

    }


}