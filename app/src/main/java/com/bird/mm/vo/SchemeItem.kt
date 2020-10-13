package com.bird.mm.vo

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bird.mm.BR
import timber.log.Timber
import java.net.InetAddress

@Entity
data class SchemeItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int) : BaseObservable() {

    constructor(id: Int,schemeUrl:String) : this(id) {
        this.schemeUrl = schemeUrl
    }

    constructor(id: Int,schemeUrl:String,useTime:Long,status:Int): this(id, schemeUrl){
        this.useTime = useTime
        this.status = status
    }

    constructor(id: Int,schemeUrl:String,useTime:Long,status:Int,filePath:String,fileType:String):this(id, schemeUrl, useTime, status){
        this.filePath = filePath
        this.fileType = fileType
    }

    var callTime:Long = 0

    var dnsTime:Long = 0

    var connectTime:Long = 0

    var connectionTime :Long = 0

//    var schemeUrl:String = ""
    var filePath :String = ""
    var fileType :String = ""

    var useTime : Long = 0
    var status : Int = 0

    var schemeUrl:String = ""

        @Bindable
        get() {
//            Timber.i("SchemeItem schemeUrl is $field")
            return field
        }
        set(value) {
            field = value
            notifyPropertyChanged(BR.schemeUrl)
        }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        super.addOnPropertyChangedCallback(callback)
    }

}