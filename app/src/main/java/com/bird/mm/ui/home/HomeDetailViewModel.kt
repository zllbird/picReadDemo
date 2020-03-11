package com.bird.mm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.bird.mm.repository.MMNetResource
import com.bird.mm.repository.UserRepository
import com.bird.mm.util.AbsentLiveData
import javax.inject.Inject

class HomeDetailViewModel @Inject constructor(var userRepository: UserRepository): ViewModel(){

    private val _link = MutableLiveData<String?>()

    private val _loadMoreIndex =  MutableLiveData<Int>()

    val loadMoreIndex : LiveData<Int> = _loadMoreIndex

    private val _currentTDIndex = MutableLiveData<Int>()
    val currentTDIndex : LiveData<Int> = _currentTDIndex

    val _cacheTD = MutableLiveData<List<String>>()

    var type:String? = null

    fun setLink(link:String){
        if (_link.value != link){
            _link.value = link
        }
    }

    fun setType2(type:String){
        this.type = type
    }

    fun addPicInCacheTd(string:String){
        if (_cacheTD.value == null){
            _cacheTD.value = arrayListOf()
        }
        val cache = arrayListOf<String>()
        cache.addAll(_cacheTD.value!!)
        cache.add(string)
//        (_cacheTD.value!! as ArrayList).add(string)
        _cacheTD.value = cache
    }

    val photos: LiveData<List<String>> = _link.switchMap {
        if (_link.value == null){
            AbsentLiveData.create()
        }else{
            userRepository.loadHomeDetail(it)
        }
    }

//    val photos2 : LiveData<List<String>> = _loadMoreIndex.switchMap {
//        if (it == null){
//            AbsentLiveData.create()
//        }else{
//            userRepository.loadHomeDetail(it)
//        }
//    }

    private val _currentTDPhoto : LiveData<String> = _currentTDIndex.switchMap {
        if (it == null){
            AbsentLiveData.create()
        }else{
            userRepository.loadHomeTDDetail(_link.value,it)
        }
    }

    val photosTd : LiveData<List<String>> = _currentTDPhoto.switchMap {
        if (it == null){
            AbsentLiveData.create<List<String>>()
        }else{
            addPicInCacheTd(it)
            _cacheTD
        }
    }

    val cacheListTd : List<String>?
        get() = photosTd.value

    fun loadNextPage(position:Int){
        if(position + 1 == photos.value?.size){
            if (loadMoreIndex.value != position){
                _loadMoreIndex.value = position
                userRepository.loadMoreDetail()
            }
        }
    }

    fun loadTDDetailByPosition(position: Int){
        if (_currentTDIndex.value == null){
            _currentTDIndex.value = position
        }
        _currentTDIndex.value?.let {
            if (it < position){
                _currentTDIndex.value = position
            }
        }

    }

}