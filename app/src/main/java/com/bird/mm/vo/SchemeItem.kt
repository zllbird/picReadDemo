package com.bird.mm.vo

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bird.mm.BR
import timber.log.Timber

@Entity
data class SchemeItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int) : BaseObservable() {

    constructor(id: Int,schemeUrl:String) : this(id) {
        this.schemeUrl = schemeUrl
    }

//    var schemeUrl:String = ""

    var schemeUrl:String = ""

        @Bindable
        get() {
            Timber.i("SchemeItem schemeUrl is $field")
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