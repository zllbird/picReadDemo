package com.bird.mm.util

import androidx.databinding.ObservableField
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
                val img = it.select("i img").first() ?: it.select("img").first()
                var src = img?.attr("src") ?: ""
                Timber.i("img $src")
                Girl(title, src, link)
            }.filter {
                !it.cover.isNullOrEmpty()
            }
        }

        fun xml2UKnowModel(xmlString: String): List<Girl> {
//        val result = String(xmlString.toByteArray(), charset("gb2312"))
            val doc = Jsoup.parse(xmlString)
            var box = doc.select("div.container div.row").first()
            val imgs = box.select("div.well a")
            return imgs.map {
                val link = it.attr("href")
//            val title = String(it.attr("title").toByteArray(), charset("gb2312"))
                val span = it.select("span")
                val title = span.text()
                val img = it.select("div img").first()
                var src = img?.attr("src") ?: ""
                Timber.i("img $src")
//                Girl(title, src, link)
                Girl(title, src, link)
            }.filter {
                !it.cover.isNullOrEmpty()
            }
        }

        fun xml2UKnowPlayUrl(xmlString: String): String {
//        val result = String(xmlString.toByteArray(), charset("gb2312"))
            val doc = Jsoup.parse(xmlString)
            var box = doc.select("div.video-container").first()
            val imgs = box.select("div video source")
            val link = imgs.attr("src")
            return link
        }

//        fun xml2UKnowModel(xmlString: String): List<Girl> {
////        val result = String(xmlString.toByteArray(), charset("gb2312"))
////            well well-sm videos-text-align
//            val doc = Jsoup.parse(xmlString)
//            var box = doc.select("div.well ul.w110").first()
//            val imgs = box.select("li a")
//            return imgs.map {
//                val link = it.attr("href")
////            val title = String(it.attr("title").toByteArray(), charset("gb2312"))
//                val title = it.attr("title")
//                val img = it.select("img").first()
//                var src = img?.attr("src") ?: ""
//                Timber.i("img $src")
//                Girl(title, src, link)
//            }.filter {
//                !it.cover.isNullOrEmpty()
//            }
//        }

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